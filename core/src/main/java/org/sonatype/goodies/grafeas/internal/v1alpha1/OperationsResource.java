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
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.goodies.dropwizard.jaxrs.ResourceSupport;
import org.sonatype.goodies.grafeas.api.v1alpha1.Note;
import org.sonatype.goodies.grafeas.api.v1alpha1.NotesEndpoint;
import org.sonatype.goodies.grafeas.api.v1alpha1.Occurrence;
import org.sonatype.goodies.grafeas.api.v1alpha1.Operation;
import org.sonatype.goodies.grafeas.api.v1alpha1.OperationsEndpoint;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link OperationsEndpoint} resource.
 *
 * @since ???
 */
@Named
@Singleton
@Path("/api/v1alpha1/projects")
public class OperationsResource
    extends ResourceSupport
    implements OperationsEndpoint
{
  //private final Provider<OperationsDao> operationsDao;
  //
  //@Inject
  //public OperationsResource(final Provider<OperationsDao> operationsDao) {
  //  this.operationsDao = checkNotNull(operationsDao);
  //}
  //
  //private OperationsDao dao() {
  //  return operationsDao.get();
  //}

  @Override
  public List<Operation> browse(final String project,
                                @Nullable final String filter,
                                @Nullable final Integer pageSize,
                                @Nullable final String pageToken)
  {
    return null;
  }

  @Override
  public Operation read(final String project, final String name) {
    return null;
  }

  @Override
  public Operation edit(final String project, final String name, final Note note) {
    return null;
  }

  @Override
  public Operation add(final String project, final Note note) {
    return null;
  }

  @Override
  public void delete(final String project, final String name) {

  }
}
