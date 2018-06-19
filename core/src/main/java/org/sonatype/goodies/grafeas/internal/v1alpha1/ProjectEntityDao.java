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
 * {@link ProjectEntity} data-access object.
 *
 * @since ???
 */
public class ProjectEntityDao
    extends AbstractDAO<ProjectEntity>
{
  public ProjectEntityDao(final SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<ProjectEntity> browse(@Nullable final String filter,
                                    @Nullable final Integer pageSize,
                                    @Nullable final String pageToken)
  {
    // FIXME: add filter and browse support; it is not yet clearly defined what this is

    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<ProjectEntity> query = builder.createQuery(ProjectEntity.class);
    query.from(ProjectEntity.class);
    return currentSession().createQuery(query).list();
  }

  @Nullable
  public ProjectEntity read(final Long id) {
    checkNotNull(id);

    return get(id);
  }

  @Nullable
  public ProjectEntity read(final String name) {
    checkNotNull(name);

    CriteriaBuilder builder = currentSession().getCriteriaBuilder();
    CriteriaQuery<ProjectEntity> query = builder.createQuery(ProjectEntity.class);
    Root<ProjectEntity> root = query.from(ProjectEntity.class);
    query.where(builder.equal(root.get("name"), name));

    return uniqueResult(query);
  }

  public long add(final ProjectEntity entity) {
    checkNotNull(entity);

    ProjectEntity created = persist(entity);
    return created.getId();
  }

  public void delete(final ProjectEntity entity) {
    checkNotNull(entity);

    currentSession().delete(entity);
  }
}
