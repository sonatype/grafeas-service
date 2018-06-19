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
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.goodies.grafeas.api.v1alpha1.OperationsEndpoint;
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiCreateOperationRequest;
import org.sonatype.goodies.grafeas.api.v1alpha1.model.LongrunningOperation;

import io.dropwizard.hibernate.UnitOfWork;

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
    extends V1alpha1ResourceSupport
    implements OperationsEndpoint
{
  // FIXME: resolve what the actual specification api is for operations and implement

  @UnitOfWork
  @Override
  public List<LongrunningOperation> browse(final String project,
                                           @Nullable final String filter,
                                           @Nullable final Integer pageSize,
                                           @Nullable final String pageToken)
  {
    checkNotNull(project);

    // TODO:

    return null;
  }

  @UnitOfWork
  @Override
  public LongrunningOperation read(final String project, final String name) {
    checkNotNull(project);
    checkNotNull(name);

    // TODO:

    return null;
  }

  @UnitOfWork
  @Override
  public LongrunningOperation edit(final String project, final String name, final LongrunningOperation operation) {
    checkNotNull(project);
    checkNotNull(name);
    checkNotNull(operation);

    // TODO:

    return null;
  }

  @UnitOfWork
  @Override
  public LongrunningOperation add(final String project, final ApiCreateOperationRequest request) {
    checkNotNull(project);
    checkNotNull(request);

    // TODO:

    return null;
  }

  @UnitOfWork
  @Override
  public void delete(final String project, final String name) {
    checkNotNull(project);
    checkNotNull(name);

    // TODO:
  }
}
