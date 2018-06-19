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
package org.sonatype.goodies.grafeas.api.v1alpha1;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiCreateOperationRequest;
import org.sonatype.goodies.grafeas.api.v1alpha1.model.LongrunningOperation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

// SEE: https://github.com/grafeas/grafeas/blob/master/v1alpha1/docs/GrafeasApi.md
// SEE: https://github.com/grafeas/grafeas/blob/master/v1alpha1/proto/grafeas.proto

/**
 * Operations endpoint.
 *
 * @since ???
 */
@Path("/v1alpha1/projects")
@Api(value = "Manage project operations")
public interface OperationsEndpoint
{
  // TODO: operations haves have a slightly different api, this needs to be adjusted once we understand wtf they are

  @POST
  @Path("{project}/operations")
  @Consumes(APPLICATION_JSON)
  @Produces(APPLICATION_JSON)
  @ApiOperation(value = "Add operation to project")
  @ApiResponses({
      @ApiResponse(code = 201, message = "Operation added")
  })
  LongrunningOperation add(@PathParam("project") @ApiParam("Project name") String project,
                           @ApiParam("Create operation request") ApiCreateOperationRequest request);
}
