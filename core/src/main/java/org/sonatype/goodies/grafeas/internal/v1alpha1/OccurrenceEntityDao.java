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

import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiOccurrence;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link OccurrenceEntity} data-access object.
 *
 * @since ???
 */
@Named
@Singleton
public class OccurrenceEntityDao
    extends AbstractDAO<OccurrenceEntity>
{
  private static final Logger log = LoggerFactory.getLogger(OccurrenceEntityDao.class);

  @Inject
  public OccurrenceEntityDao(final SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Browse occurrences for project-id.
   */
  public List<OccurrenceEntity> browse(final String projectId,
                                       @Nullable final String filter,
                                       @Nullable final Integer pageSize,
                                       @Nullable final String pageToken)
  {
    checkNotNull(projectId);

    log.trace("Browse: project-id={}, filter={}, page-size={}, page-token={}", projectId, filter, pageSize, pageToken);

    // FIXME: add filter and browse support; it is not yet clearly defined what this is

    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<OccurrenceEntity> query = builder.createQuery(OccurrenceEntity.class);
    Root<OccurrenceEntity> root = query.from(OccurrenceEntity.class);
    query.where(
        builder.equal(root.get("projectId"), projectId)
    );

    return currentSession().createQuery(query).list();
  }

  /**
   * Read occurrence for given entity-key.
   */
  @Nullable
  public OccurrenceEntity read(final long key) {
    log.trace("Read: {}", key);

    return get(key);
  }

  /**
   * Read occurrence for given project-id and occurrence-id.
   */
  @Nullable
  public OccurrenceEntity read(final String projectId, final String occurrenceId) {
    checkNotNull(projectId);
    checkNotNull(occurrenceId);

    log.trace("Read: project-id={}, occurrence-id={}", projectId, occurrenceId);

    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<OccurrenceEntity> query = builder.createQuery(OccurrenceEntity.class);
    Root<OccurrenceEntity> root = query.from(OccurrenceEntity.class);
    query.where(
        builder.and(
            builder.equal(root.get("projectId"), projectId),
            builder.equal(root.get("occurrenceId"), occurrenceId)
        )
    );

    return uniqueResult(query);
  }

  /**
   * Edit occurrence.
   */
  public OccurrenceEntity edit(final OccurrenceEntity entity) {
    checkNotNull(entity);

    log.trace("Edit: {}", entity);

    // adjust update-time
    ApiOccurrence model = entity.getData();
    checkNotNull(model);
    model.setUpdateTime(OffsetDateTime.now());

    return persist(entity);
  }

  /**
   * Add a new occurrence.
   */
  public OccurrenceEntity add(final OccurrenceEntity entity) {
    checkNotNull(entity);

    log.trace("Add: {}", entity);

    // adjust create-time
    ApiOccurrence model = entity.getData();
    checkNotNull(model);
    model.setCreateTime(OffsetDateTime.now());

    return persist(entity);
  }

  /**
   * Delete occurrence.
   */
  public void delete(final OccurrenceEntity entity) {
    checkNotNull(entity);

    log.trace("Delete: {}", entity);

    currentSession().delete(entity);
  }
}
