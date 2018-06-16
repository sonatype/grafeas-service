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
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.sonatype.goodies.dropwizard.jaxrs.ResourceSupport;
import org.sonatype.goodies.grafeas.api.v1alpha1.ProjectsEndpoint;

import io.grafeas.model.ApiListProjectsResponse;
import io.grafeas.model.ApiProject;

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
  private final Provider<ProjectsDao> projectsDao;

  @Inject
  public ProjectsResource(final Provider<ProjectsDao> projectsDao) {
    this.projectsDao = checkNotNull(projectsDao);
  }

  private ProjectsDao dao() {
    return projectsDao.get();
  }

  @Override
  public ApiListProjectsResponse browse(@Nullable final String filter,
                                        @Nullable final Integer pageSize,
                                        @Nullable final String pageToken)
  {
    // TODO: bridge filter/page poop to dao
    List<ApiProject> projects = dao().browse().stream().map(ProjectEntity::asApi).collect(Collectors.toList());
    ApiListProjectsResponse result = new ApiListProjectsResponse();
    result.setProjects(projects);
    return result;
  }

  @Override
  @Nullable
  public ApiProject read(final String name) {
    checkNotNull(name);

    log.debug("Find: {}", name);
    ProjectEntity project = dao().read(name);
    log.debug("Found: {}", project);
    if (project == null) {
      throw new WebApplicationException(Status.NOT_FOUND);
    }
    return project.asApi();
  }

  @Override
  public void add(final ApiProject project) {
    checkNotNull(project);

    log.debug("Create: {}", project);
    long id = dao().add(project.getName());
    log.debug("Created: {}", id);
  }

  @Override
  public void delete(final String name) {
    checkNotNull(name);

    log.debug("Delete: {}", name);
    dao().delete(name);
  }
}
