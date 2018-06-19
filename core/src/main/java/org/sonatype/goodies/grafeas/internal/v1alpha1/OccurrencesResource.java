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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.sonatype.goodies.grafeas.api.v1alpha1.OccurrencesEndpoint;
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiListOccurrencesResponse;
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiNote;
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiOccurrence;

import io.dropwizard.hibernate.UnitOfWork;

import static com.google.common.base.Preconditions.checkNotNull;

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
  public ApiListOccurrencesResponse browse(final String project,
                                           @Nullable final String filter,
                                           @Nullable final Integer pageSize,
                                           @Nullable final String pageToken)
  {
    List<ApiOccurrence> models = occurrenceDao().browse(project, filter, pageSize, pageToken)
        .stream().map(this::convert).collect(Collectors.toList());
    ApiListOccurrencesResponse result = new ApiListOccurrencesResponse();
    result.setOccurrences(models);
    return result;
  }

  @UnitOfWork
  @Override
  public ApiOccurrence read(final String project, final String name) {
    checkNotNull(project);
    checkNotNull(name);

    log.debug("Find: {}/{}", project, name);
    OccurrenceEntity entity = occurrenceDao().read(project, name);
    log.debug("Found: {}", entity);
    if (entity == null) {
      throw new WebApplicationException(Status.NOT_FOUND);
    }
    return convert(entity);
  }

  @UnitOfWork
  @Override
  public ApiOccurrence edit(final String project, final String name, final ApiOccurrence occurrence) {
    checkNotNull(project);
    checkNotNull(name);
    checkNotNull(occurrence);
    log.debug("Edit: {}/{} -> {}", project, name, occurrence);

    // TODO: ban updates for immutable elements

    OccurrenceEntity entity = occurrenceDao().read(project, name);
    checkNotNull(entity);

    entity.setData(merge(entity.getData(), occurrence));
    entity = occurrenceDao().edit(entity);
    log.debug("Edited: {}", entity);

    return convert(entity);
  }

  @UnitOfWork
  @Override
  public ApiOccurrence add(final String project, final ApiOccurrence occurrence) {
    checkNotNull(project);
    checkNotNull(occurrence);
    log.debug("Create: {} -> {}", project, occurrence);

    String name = occurrence.getName();
    if (name == null) {
      throw new WebApplicationException("Name required", Status.BAD_REQUEST);
    }

    OccurrenceEntity entity = new OccurrenceEntity();
    entity.setProjectName(project);
    entity.setOccurrenceName(name);
    entity.setData(occurrence);

    // look up and attach note
    NoteEntity note = noteDao().read(project, occurrence.getNoteName());
    if (note == null) {
      throw new WebApplicationException("Invalid note", Status.BAD_REQUEST);
    }
    entity.setNote(note);

    // TODO: verify project exists
    // TODO: verify if operation-name is given that operation exists

    entity = occurrenceDao().add(entity);
    log.debug("Created: {}", entity);

    return convert(entity);
  }

  @UnitOfWork
  @Override
  public void delete(final String project, final String name) {
    checkNotNull(project);
    checkNotNull(name);

    log.debug("Delete: {}/{}", project, name);
    OccurrenceEntity entity = occurrenceDao().read(project, name);
    if (entity == null) {
      throw new WebApplicationException(Status.NOT_FOUND);
    }
    occurrenceDao().delete(entity);
  }

  @UnitOfWork
  @Override
  public ApiNote readNote(final String project, final String name) {
    checkNotNull(project);
    checkNotNull(name);

    log.debug("Read note: {}/{}", project, name);

    OccurrenceEntity entity = occurrenceDao().read(project, name);
    if (entity == null) {
      throw new WebApplicationException(Status.NOT_FOUND);
    }

    return convert(entity.getNote());
  }
}
