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

import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiOccurrence;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link OccurrenceEntity} data-access object.
 *
 * @since ???
 */
public class OccurrenceEntityDao
    extends AbstractDAO<OccurrenceEntity>
{
  public OccurrenceEntityDao(final SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Browse occurrences for project.
   */
  public List<OccurrenceEntity> browse(final String project,
                                       @Nullable final String filter,
                                       @Nullable final Integer pageSize,
                                       @Nullable final String pageToken)
  {
    checkNotNull(project);

    // FIXME: add filter and browse support; it is not yet clearly defined what this is

    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<OccurrenceEntity> query = builder.createQuery(OccurrenceEntity.class);
    query.from(OccurrenceEntity.class);

    return currentSession().createQuery(query).list();
  }

  /**
   * Read occurrence for given entity identifier.
   */
  @Nullable
  public OccurrenceEntity read(final long id) {
    return get(id);
  }

  /**
   * Read occurrence for given project and name.
   */
  @Nullable
  public OccurrenceEntity read(final String project, final String name) {
    checkNotNull(project);
    checkNotNull(name);

    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<OccurrenceEntity> query = builder.createQuery(OccurrenceEntity.class);
    Root<OccurrenceEntity> root = query.from(OccurrenceEntity.class);
    query.where(
        builder.and(
            builder.equal(root.get("projectName"), project),
            builder.equal(root.get("occurrenceName"), name)
        )
    );

    return uniqueResult(query);
  }

  /**
   * Edit occurrence.
   */
  public OccurrenceEntity edit(final OccurrenceEntity entity) {
    checkNotNull(entity);

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

    currentSession().delete(entity);
  }
}
