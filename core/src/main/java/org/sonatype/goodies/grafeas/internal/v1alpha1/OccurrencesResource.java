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
import org.sonatype.goodies.grafeas.api.v1alpha1.OccurrencesEndpoint;
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiListOccurrencesResponse;
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiNote;
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiOccurrence;

import org.hibernate.SessionFactory;

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
    extends ResourceSupport
    implements OccurrencesEndpoint
{
  private final SessionFactory sessionFactory;

  @Inject
  public OccurrencesResource(final SessionFactory sessionFactory) {
    this.sessionFactory = checkNotNull(sessionFactory);
  }

  private OccurrenceEntityDao dao() {
    return new OccurrenceEntityDao(sessionFactory);
  }

  private ApiOccurrence convert(final OccurrenceEntity entity) {
    ApiOccurrence model = entity.getData();
    checkNotNull(model);
    return model;
  }

  @Override
  public ApiListOccurrencesResponse browse(final String project,
                                           @Nullable final String filter,
                                           @Nullable final Integer pageSize,
                                           @Nullable final String pageToken)
  {
    List<ApiOccurrence> models = dao().browse(project, filter, pageSize, pageToken)
        .stream().map(this::convert).collect(Collectors.toList());
    ApiListOccurrencesResponse result = new ApiListOccurrencesResponse();
    result.setOccurrences(models);
    return result;
  }

  @Override
  public ApiOccurrence read(final String project, final String name) {
    checkNotNull(project);
    checkNotNull(name);

    log.debug("Find: {}/{}", project, name);
    OccurrenceEntity entity = dao().read(project, name);
    log.debug("Found: {}", entity);
    if (entity == null) {
      throw new WebApplicationException(Status.NOT_FOUND);
    }
    return convert(entity);
  }

  @Override
  public ApiOccurrence edit(final String project, final String name, final ApiOccurrence occurrence) {
    checkNotNull(project);
    checkNotNull(name);
    checkNotNull(occurrence);
    log.debug("Edit: {}/{} -> {}", project, name, occurrence);

    // ensure note.name matches
    if (!name.equals(occurrence.getName())) {
      throw new WebApplicationException(Status.BAD_REQUEST);
    }

    OccurrenceEntity entity = dao().read(project, name);
    checkNotNull(entity);

    // FIXME: probably need to merge mutable fields here only
    entity.setData(occurrence);
    entity = dao().edit(entity);
    log.debug("Edited: {}", entity);

    return convert(entity);
  }

  @Override
  public ApiOccurrence add(final String project, final ApiOccurrence occurrence) {
    checkNotNull(project);
    checkNotNull(occurrence);
    log.debug("Create: {} -> {}", project, occurrence);

    OccurrenceEntity entity = new OccurrenceEntity();
    entity.setProjectName(project);
    entity.setOccurrenceName(occurrence.getName());
    entity.setData(occurrence);

    // TODO: verify project exists
    // TODO: verify note exists
    // TODO: verify if operation-name is given that operation exists

    entity = dao().add(entity);
    log.debug("Created: {}", entity);

    return convert(entity);
  }

  @Override
  public void delete(final String project, final String name) {
    checkNotNull(project);
    checkNotNull(name);

    log.debug("Delete: {}/{}", project, name);
    OccurrenceEntity entity = dao().read(project, name);
    dao().delete(entity);
  }

  @Override
  public ApiNote readNote(final String project, final String name) {
    checkNotNull(project);
    checkNotNull(name);

    // TODO:

    return null;
  }
}
