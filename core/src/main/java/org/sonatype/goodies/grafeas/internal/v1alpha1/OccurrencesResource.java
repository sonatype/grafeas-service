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
  public ApiListOccurrencesResponse browse(final String projectName,
                                           @Nullable final String filter,
                                           @Nullable final Integer pageSize,
                                           @Nullable final String pageToken)
  {
    checkNotNull(projectName);
    log.debug("Browse; filter: {}, page-size: {}, page-token: {}", filter, pageSize, pageToken);

    ensureProjectExists(projectName);

    List<ApiOccurrence> models = occurrenceDao().browse(projectName, filter, pageSize, pageToken)
        .stream().map(this::convert).collect(Collectors.toList());
    log.debug("Found: {}", models.size());

    ApiListOccurrencesResponse result = new ApiListOccurrencesResponse();
    result.setOccurrences(models);
    return result;
  }

  @UnitOfWork
  @Override
  public ApiOccurrence read(final String projectName, final String name) {
    checkNotNull(projectName);
    checkNotNull(name);
    log.debug("Find: {}/{}", projectName, name);

    ensureProjectExists(projectName);

    OccurrenceEntity entity = occurrenceDao().read(projectName, name);

    log.debug("Found: {}", entity);
    checkFound(entity != null);

    return convert(entity);
  }

  @UnitOfWork
  @Override
  public ApiOccurrence edit(final String projectName, final String name, final ApiOccurrence occurrence) {
    checkNotNull(projectName);
    checkNotNull(name);
    checkNotNull(occurrence);
    log.debug("Edit: {}/{} -> {}", projectName, name, occurrence);

    // ban updates for immutable properties
    checkRequest(occurrence.getName() == null, "Name is immutable");
    checkRequest(occurrence.getKind() == null, "Kind is immutable");
    checkRequest(occurrence.getCreateTime() == null, "Create-time is immutable");
    checkRequest(occurrence.getUpdateTime() == null, "Update-time is immutable");

    ensureProjectExists(projectName);

    OccurrenceEntity entity = occurrenceDao().read(projectName, name);
    checkNotNull(entity);

    entity.setData(merge(entity.getData(), occurrence));
    entity = occurrenceDao().edit(entity);
    log.debug("Edited: {}", entity);

    return convert(entity);
  }

  @UnitOfWork
  @Override
  public ApiOccurrence add(final String projectName, final ApiOccurrence occurrence) {
    checkNotNull(projectName);
    checkNotNull(occurrence);
    log.debug("Create: {} -> {}", projectName, occurrence);

    String name = occurrence.getName();
    checkRequest(name != null, "Name required");

    ensureProjectExists(projectName);

    OccurrenceEntity entity = new OccurrenceEntity();
    entity.setProjectName(projectName);
    entity.setOccurrenceName(name);
    entity.setData(occurrence);

    // look up and attach note
    NoteEntity note = noteDao().read(projectName, occurrence.getNoteName());
    checkRequest(note != null, "Invalid note");
    entity.setNote(note);

    // TODO: verify if operation-name is given that operation exists

    entity = occurrenceDao().add(entity);
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

    OccurrenceEntity entity = occurrenceDao().read(projectName, name);
    checkFound(entity != null);

    occurrenceDao().delete(entity);
  }

  @UnitOfWork
  @Override
  public ApiNote readNote(final String projectName, final String name) {
    checkNotNull(projectName);
    checkNotNull(name);
    log.debug("Read note: {}/{}", projectName, name);

    ensureProjectExists(projectName);

    OccurrenceEntity entity = occurrenceDao().read(projectName, name);
    checkFound(entity != null);

    return convert(entity.getNote());
  }
}
