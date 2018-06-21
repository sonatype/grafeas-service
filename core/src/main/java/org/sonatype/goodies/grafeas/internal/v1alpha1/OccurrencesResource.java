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

import org.sonatype.goodies.grafeas.api.v1alpha1.OccurrencesEndpoint;
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiListOccurrencesResponse;
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiNote;
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiOccurrence;

import io.dropwizard.hibernate.UnitOfWork;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.goodies.dropwizard.jaxrs.WebPreconditions.checkFound;
import static org.sonatype.goodies.dropwizard.jaxrs.WebPreconditions.checkRequest;

/**
 * {@link OccurrencesEndpoint} resource.
 *
 * @since ???
 */
@Named
@Singleton
@Path("/api/v1alpha1/projects")
public class OccurrencesResource
    extends V1alpha1ResourceSupport
    implements OccurrencesEndpoint
{
  @UnitOfWork
  @Override
  public ApiListOccurrencesResponse browse(final String projectId,
                                           @Nullable final String filter,
                                           @Nullable final Integer pageSize,
                                           @Nullable final String pageToken)
  {
    checkNotNull(projectId);
    log.debug("Browse; project-id: {}, filter: {}, page-size: {}, page-token: {}",
        projectId, filter, pageSize, pageToken);

    ensureProjectExists(projectId);

    List<ApiOccurrence> models = getOccurrenceDao().browse(projectId, filter, pageSize, pageToken)
        .stream().map(this::convert).collect(Collectors.toList());
    log.debug("Found: {}", models.size());

    return new ApiListOccurrencesResponse().occurrences(models);
  }

  @UnitOfWork
  @Override
  public ApiOccurrence read(final String projectId, final String occurrenceId) {
    checkNotNull(projectId);
    checkNotNull(occurrenceId);
    log.debug("Find: {}/{}", projectId, occurrenceId);

    ensureProjectExists(projectId);

    OccurrenceEntity entity = getOccurrenceDao().read(projectId, occurrenceId);

    log.debug("Found: {}", entity);
    checkFound(entity != null);

    return convert(entity);
  }

  @UnitOfWork
  @Override
  public ApiOccurrence edit(final String projectId, final String occurrenceId, final ApiOccurrence occurrence) {
    checkNotNull(projectId);
    checkNotNull(occurrenceId);
    checkNotNull(occurrence);
    log.debug("Edit: {}/{} -> {}", projectId, occurrenceId, occurrence);

    // ban updates for immutable properties
    checkRequest(occurrence.getName() == null, "Name is immutable");
    checkRequest(occurrence.getKind() == null, "Kind is immutable");
    checkRequest(occurrence.getCreateTime() == null, "Create-time is immutable");
    checkRequest(occurrence.getUpdateTime() == null, "Update-time is immutable");

    ensureProjectExists(projectId);

    OccurrenceEntity entity = getOccurrenceDao().read(projectId, occurrenceId);
    checkFound(entity != null);

    entity.setData(merge(entity.getData(), occurrence));
    entity = getOccurrenceDao().edit(entity);
    log.debug("Edited: {}", entity);

    return convert(entity);
  }

  @UnitOfWork
  @Override
  public ApiOccurrence add(final String projectId, final ApiOccurrence occurrence) {
    checkNotNull(projectId);
    checkNotNull(occurrence);
    log.debug("Create: {} -> {}", projectId, occurrence);

    String occurrenceName = occurrence.getName();
    checkRequest(occurrenceName != null, "Name required");
    String occurrenceId = OccurrenceEntity.extractId(projectId, occurrenceName);

    ensureProjectExists(projectId);

    // look up parent note
    // FIXME: this may not be correct, if occurrences can reference notes in other projects?
    String noteId = NoteEntity.extractId(projectId, occurrence.getNoteName());
    NoteEntity note = getNoteDao().read(projectId, noteId);
    checkRequest(note != null, "Invalid note");

    OccurrenceEntity entity = new OccurrenceEntity(projectId, occurrenceId, note, occurrence);

    // TODO: verify if operation-name is given that operation exists

    entity = getOccurrenceDao().add(entity);
    log.debug("Created: {}", entity);

    return convert(entity);
  }

  @UnitOfWork
  @Override
  public void delete(final String projectId, final String occurrenceId) {
    checkNotNull(projectId);
    checkNotNull(occurrenceId);
    log.debug("Delete: {}/{}", projectId, occurrenceId);

    ensureProjectExists(projectId);

    OccurrenceEntity entity = getOccurrenceDao().read(projectId, occurrenceId);
    checkFound(entity != null);

    getOccurrenceDao().delete(entity);
  }

  @UnitOfWork
  @Override
  public ApiNote readNote(final String projectId, final String occurrenceId) {
    checkNotNull(projectId);
    checkNotNull(occurrenceId);
    log.debug("Read note: {}/{}", projectId, occurrenceId);

    ensureProjectExists(projectId);

    OccurrenceEntity entity = getOccurrenceDao().read(projectId, occurrenceId);
    checkFound(entity != null);

    return convert(entity.getNote());
  }
}
