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

import java.time.OffsetDateTime;
import java.util.List;

import javax.annotation.Nullable;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiNote;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link NoteEntity} data-access object.
 *
 * @since ???
 */
public class NoteEntityDao
    extends AbstractDAO<NoteEntity>
{
  public NoteEntityDao(final SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<NoteEntity> browse(final String project,
                                 @Nullable final String filter,
                                 @Nullable final Integer pageSize,
                                 @Nullable final String pageToken)
  {
    checkNotNull(project);

    // FIXME: add filter and browse support; it is not yet clearly defined what this is

    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<NoteEntity> query = builder.createQuery(NoteEntity.class);
    query.from(NoteEntity.class);
    return currentSession().createQuery(query).list();
  }

  @Nullable
  public NoteEntity read(final long id) {
    return get(id);
  }

  @Nullable
  public NoteEntity read(final String project, final String name) {
    checkNotNull(project);
    checkNotNull(name);

    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<NoteEntity> query = builder.createQuery(NoteEntity.class);
    Root<NoteEntity> root = query.from(NoteEntity.class);
    query.where(
        builder.and(
            builder.equal(root.get("projectName"), project),
            builder.equal(root.get("noteName"), name)
        )
    );

    return uniqueResult(query);
  }

  public NoteEntity edit(final NoteEntity entity) {
    checkNotNull(entity);

    // adjust update-time
    ApiNote model = entity.getData();
    checkNotNull(model);
    model.setUpdateTime(OffsetDateTime.now());

    return persist(entity);
  }

  public NoteEntity add(final NoteEntity entity) {
    checkNotNull(entity);

    // adjust create-time
    ApiNote model = entity.getData();
    checkNotNull(model);
    model.setCreateTime(OffsetDateTime.now());

    return persist(entity);
  }

  public void delete(final NoteEntity entity) {
    checkNotNull(entity);

    currentSession().delete(entity);
  }
}
