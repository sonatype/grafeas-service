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

import javax.inject.Inject;

import org.sonatype.goodies.dropwizard.jaxrs.ResourceSupport;
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiNote;
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiOccurrence;
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiProject;

import org.hibernate.SessionFactory;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Support for {@code v1alpha1} resources.
 *
 * @since ???
 */
public abstract class V1alpha1ResourceSupport
    extends ResourceSupport
{
  private SessionFactory sessionFactory;

  @Inject
  public void setSessionFactory(final SessionFactory sessionFactory) {
    this.sessionFactory = checkNotNull(sessionFactory);
  }

  private SessionFactory getSessionFactory() {
    checkState(sessionFactory != null);
    return sessionFactory;
  }

  //
  // Data-access objects
  //

  // TODO: cache dao instances, they should be thread-safe

  protected ProjectEntityDao projectDao() {
    return new ProjectEntityDao(getSessionFactory());
  }

  protected NoteEntityDao noteDao() {
    return new NoteEntityDao(getSessionFactory());
  }

  protected OccurrenceEntityDao occurrenceDao() {
    return new OccurrenceEntityDao(getSessionFactory());
  }

  //
  // Converters
  //

  protected ApiProject convert(final ProjectEntity entity) {
    String name = entity.getName();
    checkNotNull(name);
    return new ApiProject().name(name);
  }

  protected ApiNote convert(final NoteEntity entity) {
    ApiNote model = entity.getData();
    checkNotNull(model);
    return model;
  }

  protected ApiOccurrence convert(final OccurrenceEntity entity) {
    ApiOccurrence model = entity.getData();
    checkNotNull(model);
    return model;
  }
}
