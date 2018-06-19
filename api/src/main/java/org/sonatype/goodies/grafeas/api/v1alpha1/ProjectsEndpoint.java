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

import javax.annotation.Nullable;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiListProjectsResponse;
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiProject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

// SEE: https://github.com/grafeas/grafeas/blob/master/v1alpha1/docs/GrafeasProjectsApi.md
// SEE: https://github.com/grafeas/grafeas/blob/master/v1alpha1/proto/grafeas.proto

/**
 * Projects endpoint.
 *
 * @since ???
 */
@Path("/v1alpha1/projects")
@Api(value = "Manage projects")
public interface ProjectsEndpoint
{
  @GET
  @Produces(APPLICATION_JSON)
  @ApiOperation(value = "Browse projects")
  @ApiResponses({
      @ApiResponse(code = 200, message = "Projects")
  })
  ApiListProjectsResponse browse(@QueryParam("filter") @Nullable String filter,
                                 @QueryParam("page_size") @Nullable Integer pageSize,
                                 @QueryParam("page_token") @Nullable String pageToken);

  @GET
  @Path("{name}")
  @Produces(APPLICATION_JSON)
  @ApiOperation(value = "Read project")
  @ApiResponses({
      @ApiResponse(code = 200, message = "Project"),
      @ApiResponse(code = 404, message = "Project not found")
  })
  ApiProject read(@PathParam("name") @ApiParam("Project name") String name);

  @POST
  @Consumes(APPLICATION_JSON)
  @ApiOperation(value = "Add project")
  @ApiResponses({
      @ApiResponse(code = 201, message = "Project added")
  })
  void add(@ApiParam("Project") ApiProject project);

  @DELETE
  @Path("{name}")
  @ApiOperation(value = "Delete project")
  @ApiResponses({
      @ApiResponse(code = 204, message = "Project deleted"),
      @ApiResponse(code = 404, message = "Project not found")
  })
  void delete(@PathParam("name") @ApiParam("Project name") String name);
}
