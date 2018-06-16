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

import javax.annotation.Nullable;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link NoteEntity} data-access object.
 *
 * @since ???
 */
public class NotesEntityDao
    extends AbstractDAO<NoteEntity>
{
  public NotesEntityDao(final SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<NoteEntity> browse(final String project,
                                 @Nullable final String filter,
                                 @Nullable final Integer pageSize,
                                 @Nullable final String pageToken)
  {
    checkNotNull(project);

    // FIXME: add filter and browse support

    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<NoteEntity> query = builder.createQuery(NoteEntity.class);
    query.from(NoteEntity.class);
    return currentSession().createQuery(query).list();
  }

  @Nullable
  public NoteEntity read(final Long id) {
    checkNotNull(id);

    return get(id);
  }

  @Nullable
  public NoteEntity read(final String project, final String name) {
    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<NoteEntity> query = builder.createQuery(NoteEntity.class);
    Root<ProjectEntity> root = query.from(ProjectEntity.class);
    query.where(
        builder.and(
            builder.equal(root.get("project_name"), project),
            builder.equal(root.get("note_name"), name)
        )
    );

    return uniqueResult(query);
  }

  // FIXME: need to figure out how edit works

  //@SqlUpdate("INSERT INTO notes (project_name, note_name, data) VALUES (:projectName, :noteName, :data)")
  //void edit(@BindBean NoteEntity note);

  public long add(final NoteEntity note) {
    checkNotNull(note);

    NoteEntity created = persist(note);
    return created.getId();
  }

  public void delete(final NoteEntity note) {
    checkNotNull(note);

    currentSession().delete(note);;
  }
}
