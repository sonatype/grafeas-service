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
import org.sonatype.goodies.grafeas.internal.v1alpha1.NoteEntityDao;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.views.View;

import static com.google.common.base.Preconditions.checkNotNull;

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
  private final NoteEntityDao noteEntityDao;

  @Inject
  public NoteResource(final NoteEntityDao noteEntityDao) {
    this.noteEntityDao = checkNotNull(noteEntityDao);
  }

  @GET
  @Path("project/{project}/notes")
  @UnitOfWork
  public View list(@PathParam("project") final String projectName) {
    return new NoteListView(noteEntityDao.browse(projectName, null, null, null));
  }

  @GET
  @Path("project/{project}/note/{name}")
  @UnitOfWork
  public View get(@PathParam("project") final String projectName, @PathParam("name") final String name) {
    return new NoteView(noteEntityDao.read(projectName, name));
  }
}
