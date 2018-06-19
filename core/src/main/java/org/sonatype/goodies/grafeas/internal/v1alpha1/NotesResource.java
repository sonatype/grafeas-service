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
  public ApiListNotesResponse browse(final String projectName,
                                     @Nullable final String filter,
                                     @Nullable final Integer pageSize,
                                     @Nullable final String pageToken)
  {
    checkNotNull(projectName);
    log.debug("Browse; filter: {}, page-size: {}, page-token: {}", filter, pageSize, pageToken);

    ensureProjectExists(projectName);

    List<ApiNote> models = noteDao().browse(projectName, filter, pageSize, pageToken)
        .stream().map(this::convert).collect(Collectors.toList());
    log.debug("Found: {}", models.size());

    ApiListNotesResponse result = new ApiListNotesResponse();
    result.setNotes(models);
    return result;
  }

  @UnitOfWork
  @Override
  public ApiNote read(final String projectName, final String name) {
    checkNotNull(projectName);
    checkNotNull(name);
    log.debug("Find: {}/{}", projectName, name);

    ensureProjectExists(projectName);

    NoteEntity entity = noteDao().read(projectName, name);

    log.debug("Found: {}", entity);
    checkFound(entity != null);

    return convert(entity);
  }

  @UnitOfWork
  @Override
  public ApiNote edit(final String projectName, final String name, final ApiNote note) {
    checkNotNull(projectName);
    checkNotNull(name);
    checkNotNull(note);
    log.debug("Edit: {}/{} -> {}", projectName, name, note);

    // ban updates for immutable properties
    checkRequest(note.getName() == null, "Name is immutable");
    checkRequest(note.getKind() == null, "Kind is immutable");
    checkRequest(note.getCreateTime() == null, "Create-time is immutable");
    checkRequest(note.getUpdateTime() == null, "Update-time is immutable");

    ensureProjectExists(projectName);

    NoteEntity entity = noteDao().read(projectName, name);
    checkNotNull(entity);

    entity.setData(merge(entity.getData(), note));
    entity = noteDao().edit(entity);
    log.debug("Edited: {}", entity);

    return convert(entity);
  }

  @UnitOfWork
  @Override
  public ApiNote add(final String projectName, final ApiNote note) {
    checkNotNull(projectName);
    checkNotNull(note);
    log.debug("Create: {} -> {}", projectName, note);

    String name = note.getName();
    checkRequest(name != null, "Name required");

    ensureProjectExists(projectName);

    NoteEntity entity = new NoteEntity();
    entity.setProjectName(projectName);
    entity.setNoteName(name);
    entity.setData(note);

    // TODO: verify if operation-name is given that operation exists

    entity = noteDao().add(entity);
    log.debug("Created: {}", entity);

    return convert(entity);
  }

  @UnitOfWork
  @Override
  public void delete(final String projectName, final String name) {
    checkNotNull(projectName);
    checkNotNull(name);
    log.debug("Delete: {}/{}", projectName, name);

    ensureProjectExists(projectName);

    NoteEntity entity = noteDao().read(projectName, name);
    checkFound(entity != null);

    noteDao().delete(entity);
  }

  @UnitOfWork
  @Override
  public ApiListNoteOccurrencesResponse readOccurrences(final String projectName, final String name) {
    checkNotNull(projectName);
    checkNotNull(name);
    log.debug("Read occurrences: {}/{}", projectName, name);

    ensureProjectExists(projectName);

    NoteEntity entity = noteDao().read(projectName, name);
    checkFound(entity != null);

    List<ApiOccurrence> occurrences = entity.getOccurrences()
        .stream().map(this::convert).collect(Collectors.toList());
    ApiListNoteOccurrencesResponse result = new ApiListNoteOccurrencesResponse();
    result.setOccurrences(occurrences);

    return result;
  }
}
