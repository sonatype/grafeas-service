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
import org.sonatype.goodies.grafeas.api.v1alpha1.NotesEndpoint;
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiListNoteOccurrencesResponse;
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiListNotesResponse;
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiNote;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * {@link NotesEndpoint} resource.
 *
 * @since ???
 */
@Named
@Singleton
@Path("/api/v1alpha1/projects")
public class NotesResource
    extends ResourceSupport
    implements NotesEndpoint
{
  private final DaoAccess daoAccess;

  @Inject
  public NotesResource(final DaoAccess daoAccess) {
    this.daoAccess = checkNotNull(daoAccess);
  }

  private NotesDao dao() {
    return daoAccess.notes();
  }

  @Override
  public ApiListNotesResponse browse(final String project,
                                     @Nullable final String filter,
                                     @Nullable final Integer pageSize,
                                     @Nullable final String pageToken)
  {
    List<ApiNote> notes = dao().browse(project).stream().map(NoteEntity::asApi).collect(Collectors.toList());
    ApiListNotesResponse result = new ApiListNotesResponse();
    result.setNotes(notes);
    return result;
  }

  @Override
  public ApiNote read(final String project, final String name) {
    checkNotNull(project);
    checkNotNull(name);

    log.debug("Find: {}/{}", project, name);
    NoteEntity entity = dao().read(project, name);
    log.debug("Found: {}", entity);
    if (entity == null) {
      throw new WebApplicationException(Status.NOT_FOUND);
    }
    return entity.asApi();
  }

  @Override
  public ApiNote edit(final String project, final String name, final ApiNote note) {
    checkNotNull(project);
    checkNotNull(name);
    checkNotNull(note);

    // TODO:

    return null;
  }

  @Override
  public ApiNote add(final String project, final ApiNote note) {
    checkNotNull(project);
    checkNotNull(note);
    log.debug("Create: {} -> {}", project, note);

    long id = dao().add(new NoteEntity());
    log.debug("Created: {}", id);

    NoteEntity entity = dao().read(id);
    checkState(entity != null);
    return entity.asApi();
  }

  @Override
  public void delete(final String project, final String name) {
    checkNotNull(project);
    checkNotNull(name);

    log.debug("Delete: {}/{}", project, name);
    dao().delete(project, name);
  }

  @Override
  public ApiListNoteOccurrencesResponse readOccurrences(final String project, final String name) {
    checkNotNull(project);
    checkNotNull(name);

    // TODO:

    return null;
  }
}
