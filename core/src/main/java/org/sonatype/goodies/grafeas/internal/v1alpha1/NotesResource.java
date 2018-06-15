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
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.sonatype.goodies.dropwizard.jaxrs.ResourceSupport;
import org.sonatype.goodies.grafeas.api.v1alpha1.Note;
import org.sonatype.goodies.grafeas.api.v1alpha1.NotesEndpoint;
import org.sonatype.goodies.grafeas.api.v1alpha1.Occurrence;

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
  private final Provider<NotesDao> notesDao;

  @Inject
  public NotesResource(final Provider<NotesDao> notesDao) {
    this.notesDao = checkNotNull(notesDao);
  }

  private NotesDao dao() {
    return notesDao.get();
  }

  @Override
  public List<Note> browse(final String project,
                           @Nullable final String filter,
                           @Nullable final Integer pageSize,
                           @Nullable final String pageToken)
  {
    // TODO: bridge filter/page poop to dao
    return dao().browse(project).stream().map(NoteEntity::asApi).collect(Collectors.toList());
  }

  @Override
  public Note read(final String project, final String name) {
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
  public Note edit(final String project, final String name, final Note note) {
    checkNotNull(project);
    checkNotNull(name);
    checkNotNull(note);

    // TODO:

    return null;
  }

  @Override
  public Note add(final String project, final Note note) {
    checkNotNull(project);
    checkNotNull(note);
    log.debug("Create: {} -> {}", project, note);

    // FIXME: convert to entity, return api model from created entity

    long id = dao().add(new NoteEntity());
    log.debug("Created: {}", id);

    return note;
  }

  @Override
  public void delete(final String project, final String name) {
    checkNotNull(project);
    checkNotNull(name);

    log.debug("Delete: {}/{}", project, name);
    dao().delete(project, name);
  }

  @Override
  public List<Occurrence> readOccurrences(final String project, final String name) {
    checkNotNull(project);
    checkNotNull(name);

    // TODO:

    return null;
  }
}
