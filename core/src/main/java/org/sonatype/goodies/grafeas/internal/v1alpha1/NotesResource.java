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

import io.dropwizard.hibernate.UnitOfWork;
import org.hibernate.SessionFactory;

import static com.google.common.base.Preconditions.checkNotNull;

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
  private final SessionFactory sessionFactory;

  @Inject
  public NotesResource(final SessionFactory sessionFactory) {
    this.sessionFactory = checkNotNull(sessionFactory);
  }

  private NoteEntityDao dao() {
    return new NoteEntityDao(sessionFactory);
  }

  private ApiNote convert(final NoteEntity entity) {
    ApiNote model = entity.getData();
    checkNotNull(model);
    return model;
  }

  @UnitOfWork
  @Override
  public ApiListNotesResponse browse(final String project,
                                     @Nullable final String filter,
                                     @Nullable final Integer pageSize,
                                     @Nullable final String pageToken)
  {
    List<ApiNote> notes = dao().browse(project, filter, pageSize, pageToken)
        .stream().map(this::convert).collect(Collectors.toList());
    ApiListNotesResponse result = new ApiListNotesResponse();
    result.setNotes(notes);
    return result;
  }

  @UnitOfWork
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
    return convert(entity);
  }

  @UnitOfWork
  @Override
  public ApiNote edit(final String project, final String name, final ApiNote note) {
    checkNotNull(project);
    checkNotNull(name);
    checkNotNull(note);
    log.debug("Edit: {}/{} -> {}", project, name, note);

    // ensure note.name matches
    if (!name.equals(note.getName())) {
      throw new WebApplicationException(Status.BAD_REQUEST);
    }

    NoteEntity entity = dao().read(project, name);
    checkNotNull(entity);

    entity.setData(note);
    entity = dao().edit(entity);
    log.debug("Edited: {}", entity);

    return convert(entity);
  }

  @UnitOfWork
  @Override
  public ApiNote add(final String project, final ApiNote note) {
    checkNotNull(project);
    checkNotNull(note);
    log.debug("Create: {} -> {}", project, note);

    NoteEntity entity = new NoteEntity();
    entity.setProjectName(project);
    entity.setNoteName(note.getName());
    entity.setData(note);

    entity = dao().add(entity);
    log.debug("Created: {}", entity);

    return convert(entity);
  }

  @UnitOfWork
  @Override
  public void delete(final String project, final String name) {
    checkNotNull(project);
    checkNotNull(name);

    log.debug("Delete: {}/{}", project, name);
    NoteEntity entity = dao().read(project, name);
    dao().delete(entity);
  }

  @UnitOfWork
  @Override
  public ApiListNoteOccurrencesResponse readOccurrences(final String project, final String name) {
    checkNotNull(project);
    checkNotNull(name);

    // TODO:

    return null;
  }
}
