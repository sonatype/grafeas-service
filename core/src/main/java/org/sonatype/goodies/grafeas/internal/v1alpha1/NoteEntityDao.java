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
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiNote;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link NoteEntity} data-access object.
 *
 * @since ???
 */
@Named
@Singleton
public class NoteEntityDao
    extends AbstractDAO<NoteEntity>
{
  private static final Logger log = LoggerFactory.getLogger(NoteEntityDao.class);

  @Inject
  public NoteEntityDao(final SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Browse notes for project.
   */
  public List<NoteEntity> browse(final String projectName,
                                 @Nullable final String filter,
                                 @Nullable final Integer pageSize,
                                 @Nullable final String pageToken)
  {
    checkNotNull(projectName);

    log.trace("Browse: project-name={}, filter={}, page-size={}, page-token={}",
        projectName, filter, pageSize, pageToken);

    // FIXME: add filter and browse support; it is not yet clearly defined what this is

    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<NoteEntity> query = builder.createQuery(NoteEntity.class);
    Root<NoteEntity> root = query.from(NoteEntity.class);
    query.where(
        builder.equal(root.get("projectName"), projectName)
    );
    return currentSession().createQuery(query).list();
  }

  /**
   * Read note for given entity identifier.
   */
  @Nullable
  public NoteEntity read(final long id) {
    log.trace("Read: {}", id);

    return get(id);
  }

  /**
   * Read note for given project and name.
   */
  @Nullable
  public NoteEntity read(final String projectName, final String noteName) {
    checkNotNull(projectName);
    checkNotNull(noteName);

    log.trace("Read: project-name={}, note-name={}", projectName, noteName);

    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<NoteEntity> query = builder.createQuery(NoteEntity.class);
    Root<NoteEntity> root = query.from(NoteEntity.class);
    query.where(
        builder.and(
            builder.equal(root.get("projectName"), projectName),
            builder.equal(root.get("noteName"), noteName)
        )
    );

    return uniqueResult(query);
  }

  /**
   * Edit note.
   */
  public NoteEntity edit(final NoteEntity entity) {
    checkNotNull(entity);

    log.trace("Edit: {}", entity);

    // adjust update-time
    ApiNote model = entity.getData();
    checkNotNull(model);
    model.setUpdateTime(OffsetDateTime.now());

    return persist(entity);
  }

  /**
   * Add a new note.
   */
  public NoteEntity add(final NoteEntity entity) {
    checkNotNull(entity);

    log.trace("Add: {}", entity);

    // adjust create-time
    ApiNote model = entity.getData();
    checkNotNull(model);
    model.setCreateTime(OffsetDateTime.now());

    return persist(entity);
  }

  /**
   * Delete note.
   */
  public void delete(final NoteEntity entity) {
    checkNotNull(entity);

    log.trace("Delete: {}", entity);

    currentSession().delete(entity);
  }
}
