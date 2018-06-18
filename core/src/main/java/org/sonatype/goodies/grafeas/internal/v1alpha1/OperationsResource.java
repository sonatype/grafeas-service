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

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.goodies.dropwizard.jaxrs.ResourceSupport;
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
    extends ResourceSupport
    implements OperationsEndpoint
{
  @UnitOfWork
  @Override
  public LongrunningOperation add(final String project, final ApiCreateOperationRequest request) {
    checkNotNull(project);
    checkNotNull(request);

    // TODO:
    return null;
  }
}
