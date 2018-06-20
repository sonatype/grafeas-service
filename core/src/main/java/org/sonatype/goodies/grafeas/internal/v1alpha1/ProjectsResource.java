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
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.goodies.grafeas.api.v1alpha1.ProjectsEndpoint;
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiListProjectsResponse;
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiProject;

import io.dropwizard.hibernate.UnitOfWork;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.goodies.dropwizard.jaxrs.WebPreconditions.checkFound;

/**
 * {@link ProjectsEndpoint} resource.
 *
 * @since ???
 */
@Named
@Singleton
@Path("/api/v1alpha1/projects")
public class ProjectsResource
    extends V1alpha1ResourceSupport
    implements ProjectsEndpoint
{
  @Override
  @UnitOfWork
  public ApiListProjectsResponse browse(@Nullable final String filter,
                                        @Nullable final Integer pageSize,
                                        @Nullable final String pageToken)
  {
    log.debug("Browse; filter: {}, page-size: {}, page-token: {}", filter, pageSize, pageToken);

    List<ApiProject> models = getProjectDao().browse(filter, pageSize, pageToken)
        .stream().map(this::convert).collect(Collectors.toList());
    log.debug("Found: {}", models.size());

    return new ApiListProjectsResponse().projects(models);
  }

  @Override
  @UnitOfWork
  public ApiProject read(final String projectName) {
    checkNotNull(projectName);
    log.debug("Find: {}", projectName);

    ProjectEntity entity = getProjectDao().read(projectName);

    log.debug("Found: {}", entity);
    checkFound(entity != null);

    return convert(entity);
  }

  @Override
  @UnitOfWork
  public void add(final ApiProject project) {
    checkNotNull(project);
    log.debug("Create: {}", project);

    ProjectEntity entity = new ProjectEntity(project.getName());
    ProjectEntity created = getProjectDao().add(entity);
    log.debug("Created: {}", created);
  }

  @Override
  @UnitOfWork
  public void delete(final String projectName) {
    checkNotNull(projectName);
    log.debug("Delete: {}", projectName);

    ProjectEntity entity = getProjectDao().read(projectName);
    checkFound(entity != null);

    getProjectDao().delete(entity);
    log.debug("Deleted");
  }
}
