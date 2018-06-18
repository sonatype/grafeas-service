/*
 * Copyright (c) 2018-present Sonatype, Inc. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package org.sonatype.goodies.grafeas.internal.v1alpha1;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.sonatype.goodies.dropwizard.jaxrs.ResourceSupport;
import org.sonatype.goodies.grafeas.api.v1alpha1.ProjectsEndpoint;
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiListProjectsResponse;
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiProject;

import io.dropwizard.hibernate.UnitOfWork;
import org.hibernate.SessionFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link ProjectsEndpoint} resource.
 *
 * @since ???
 */
@Named
@Singleton
@Path("/api/v1alpha1/projects")
public class ProjectsResource
    extends ResourceSupport
    implements ProjectsEndpoint
{
  private final SessionFactory sessionFactory;

  @Inject
  public ProjectsResource(final SessionFactory sessionFactory) {
    this.sessionFactory = checkNotNull(sessionFactory);
  }

  private ProjectEntityDao dao() {
    return new ProjectEntityDao(sessionFactory);
  }

  private ApiProject convert(final ProjectEntity entity) {
    String name = entity.getName();
    checkNotNull(name);
    return new ApiProject().name(name);
  }

  @Override
  @UnitOfWork
  public ApiListProjectsResponse browse(@Nullable final String filter,
                                        @Nullable final Integer pageSize,
                                        @Nullable final String pageToken)
  {
    log.debug("Browse; filter: {}, page-size: {}, page-token: {}", filter, pageSize, pageToken);

    List<ApiProject> models = dao().browse(filter, pageSize, pageToken)
        .stream().map(this::convert).collect(Collectors.toList());
    log.debug("Found: {} entities", models.size());

    ApiListProjectsResponse result = new ApiListProjectsResponse();
    result.setProjects(models);
    return result;
  }

  @Override
  @UnitOfWork
  public ApiProject read(final String name) {
    checkNotNull(name);

    log.debug("Find: {}", name);
    ProjectEntity entity = dao().read(name);

    log.debug("Found: {}", entity);
    if (entity == null) {
      throw new WebApplicationException(Status.NOT_FOUND);
    }

    return convert(entity);
  }

  @Override
  @UnitOfWork
  public void add(final ApiProject project) {
    checkNotNull(project);

    log.debug("Create: {}", project);
    ProjectEntity entity = new ProjectEntity();
    entity.setName(project.getName());
    long id = dao().add(entity);
    log.debug("Created: {}", id);
  }

  @Override
  @UnitOfWork
  public void delete(final String name) {
    checkNotNull(name);

    log.debug("Delete: {}", name);
    ProjectEntity entity = dao().read(name);
    if (entity == null) {
      throw new WebApplicationException(Status.NOT_FOUND);
    }
    dao().delete(entity);
    log.debug("Deleted");
  }
}
