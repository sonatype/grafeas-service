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

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.goodies.dropwizard.jaxrs.ResourceSupport;
import org.sonatype.goodies.grafeas.api.v1alpha1.OccurrencesEndpoint;

import io.grafeas.model.ApiListOccurrencesResponse;
import io.grafeas.model.ApiNote;
import io.grafeas.model.ApiOccurrence;

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
  //private final Provider<OccurrencesDao> occurrencesDao;
  //
  //@Inject
  //public OccurrencesResource(final Provider<OccurrencesDao> occurrencesDao) {
  //  this.occurrencesDao = checkNotNull(occurrencesDao);
  //}
  //
  //private OccurrencesDao dao() {
  //  return occurrencesDao.get();
  //}

  @Override
  public ApiListOccurrencesResponse browse(final String project,
                                           @Nullable final String filter,
                                           @Nullable final Integer pageSize,
                                           @Nullable final String pageToken)
  {
    return null;
  }

  @Override
  public ApiOccurrence read(final String project, final String name) {
    return null;
  }

  @Override
  public ApiOccurrence edit(final String project, final String name, final ApiOccurrence occurrence) {
    return null;
  }

  @Override
  public ApiOccurrence add(final String project, final ApiOccurrence occurrence) {
    return null;
  }

  @Override
  public void delete(final String project, final String name) {

  }

  @Override
  public ApiNote readNote(final String project, final String name) {
    return null;
  }
}
