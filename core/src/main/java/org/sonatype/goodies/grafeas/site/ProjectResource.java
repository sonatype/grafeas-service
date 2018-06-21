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
package org.sonatype.goodies.grafeas.site;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.sonatype.goodies.dropwizard.jaxrs.ResourceSupport;
import org.sonatype.goodies.grafeas.internal.v1alpha1.ProjectEntityDao;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.views.View;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Project resource.
 *
 * @since ???
 */
@Named
@Singleton
@Path("/")
@Produces(MediaType.TEXT_HTML)
public class ProjectResource
    extends ResourceSupport
{
  private final ProjectEntityDao projectDao;

  @Inject
  public ProjectResource(final ProjectEntityDao projectDao) {
    this.projectDao = checkNotNull(projectDao);
  }

  @GET
  @Path("projects")
  @UnitOfWork
  public View list() {
    return new ProjectListView(projectDao.browse(null, null, null));
  }

  @GET
  @Path("project/{project_id}")
  @UnitOfWork
  public View get(@PathParam("project_id") final String projectId) {
    return new ProjectView(projectDao.read(projectId));
  }
}
