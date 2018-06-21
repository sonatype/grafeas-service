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

import org.sonatype.goodies.grafeas.api.v1alpha1.NotesEndpoint;
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiListNoteOccurrencesResponse;
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiListNotesResponse;
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiNote;
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiOccurrence;

import io.dropwizard.hibernate.UnitOfWork;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.goodies.dropwizard.jaxrs.WebPreconditions.checkFound;
import static org.sonatype.goodies.dropwizard.jaxrs.WebPreconditions.checkRequest;

/**
 * {@link NotesEndpoint} resource.
 *
 * @since ???
 */
@Named
@Singleton
@Path("/api/v1alpha1/projects")
public class NotesResource
    extends V1alpha1ResourceSupport
    implements NotesEndpoint
{
  @UnitOfWork
  @Override
  public ApiListNotesResponse browse(final String projectId,
                                     @Nullable final String filter,
                                     @Nullable final Integer pageSize,
                                     @Nullable final String pageToken)
  {
    checkNotNull(projectId);
    log.debug("Browse; filter: {}, page-size: {}, page-token: {}", filter, pageSize, pageToken);

    ensureProjectExists(projectId);

    List<ApiNote> models = getNoteDao().browse(projectId, filter, pageSize, pageToken)
        .stream().map(this::convert).collect(Collectors.toList());
    log.debug("Found: {}", models.size());

    return new ApiListNotesResponse().notes(models);
  }

  @UnitOfWork
  @Override
  public ApiNote read(final String projectId, final String noteId) {
    checkNotNull(projectId);
    checkNotNull(noteId);
    log.debug("Find: {}/{}", projectId, noteId);

    ensureProjectExists(projectId);

    NoteEntity entity = getNoteDao().read(projectId, noteId);

    log.debug("Found: {}", entity);
    checkFound(entity != null);

    return convert(entity);
  }

  @UnitOfWork
  @Override
  public ApiNote edit(final String projectId, final String noteId, final ApiNote note) {
    checkNotNull(projectId);
    checkNotNull(noteId);
    checkNotNull(note);
    log.debug("Edit: {}/{} -> {}", projectId, noteId, note);

    // ban updates for immutable properties
    checkRequest(note.getName() == null, "Name is immutable");
    checkRequest(note.getKind() == null, "Kind is immutable");
    checkRequest(note.getCreateTime() == null, "Create-time is immutable");
    checkRequest(note.getUpdateTime() == null, "Update-time is immutable");

    ensureProjectExists(projectId);

    NoteEntity entity = getNoteDao().read(projectId, noteId);
    checkFound(entity != null);

    entity.setData(merge(entity.getData(), note));
    entity = getNoteDao().edit(entity);
    log.debug("Edited: {}", entity);

    return convert(entity);
  }

  @UnitOfWork
  @Override
  public ApiNote add(final String projectId, final ApiNote note) {
    checkNotNull(projectId);
    checkNotNull(note);
    log.debug("Create: {} -> {}", projectId, note);

    String noteName = note.getName();
    checkRequest(noteName != null, "Name required");
    // TODO: validate note name
    // TODO: extract note-id
    String noteId = "FIXME";

    ensureProjectExists(projectId);

    NoteEntity entity = new NoteEntity(projectId, noteId, note);

    // TODO: verify if operation-name is given that operation exists

    entity = getNoteDao().add(entity);
    log.debug("Created: {}", entity);

    return convert(entity);
  }

  @UnitOfWork
  @Override
  public void delete(final String projectId, final String noteId) {
    checkNotNull(projectId);
    checkNotNull(noteId);
    log.debug("Delete: {}/{}", projectId, noteId);

    ensureProjectExists(projectId);

    NoteEntity entity = getNoteDao().read(projectId, noteId);
    checkFound(entity != null);

    getNoteDao().delete(entity);
  }

  @UnitOfWork
  @Override
  public ApiListNoteOccurrencesResponse readOccurrences(final String projectId, final String noteId) {
    checkNotNull(projectId);
    checkNotNull(noteId);
    log.debug("Read occurrences: {}/{}", projectId, noteId);

    ensureProjectExists(projectId);

    NoteEntity entity = getNoteDao().read(projectId, noteId);
    checkFound(entity != null);

    List<ApiOccurrence> occurrences = entity.getOccurrences()
        .stream().map(this::convert).collect(Collectors.toList());
    ApiListNoteOccurrencesResponse result = new ApiListNoteOccurrencesResponse();
    result.setOccurrences(occurrences);

    return result;
  }
}
