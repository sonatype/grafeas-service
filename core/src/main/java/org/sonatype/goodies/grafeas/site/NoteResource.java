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
import org.sonatype.goodies.dropwizard.jaxrs.WebPreconditions;
import org.sonatype.goodies.grafeas.internal.v1alpha1.NoteEntityDao;
import org.sonatype.goodies.grafeas.internal.v1alpha1.ProjectEntity;
import org.sonatype.goodies.grafeas.internal.v1alpha1.ProjectEntityDao;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.views.View;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.goodies.dropwizard.jaxrs.WebPreconditions.*;

/**
 * Note resource.
 *
 * @since ???
 */
@Named
@Singleton
@Path("/")
@Produces(MediaType.TEXT_HTML)
public class NoteResource
    extends ResourceSupport
{
  private final ProjectEntityDao projectEntityDao;

  private final NoteEntityDao noteEntityDao;

  @Inject
  public NoteResource(final ProjectEntityDao projectEntityDao, final NoteEntityDao noteEntityDao) {
    this.projectEntityDao = checkNotNull(projectEntityDao);
    this.noteEntityDao = checkNotNull(noteEntityDao);
  }

  @GET
  @Path("project/{project_id}/notes")
  @UnitOfWork
  public View projectNotes(@PathParam("project_id") final String projectId) {
    ProjectEntity project = projectEntityDao.read(projectId);
    checkFound(project != null);
    return new NoteListView(project, project.getNotes());
  }

  @GET
  @Path("project/{project_id}/note/{note_id}")
  @UnitOfWork
  public View projectNote(@PathParam("project_id") final String projectId, @PathParam("note_id") final String noteId) {
    return new NoteView(noteEntityDao.read(projectId, noteId));
  }
}
