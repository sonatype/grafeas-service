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

import java.util.List;

import javax.annotation.Nullable;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

// SEE: https://github.com/grafeas/grafeas/blob/master/v1alpha1/docs/GrafeasApi.md
// SEE: https://github.com/grafeas/grafeas/blob/master/v1alpha1/proto/grafeas.proto

/**
 * Occurrences endpoint.
 *
 * @since ???
 */
@Path("/v1alpha1/projects")
@Api(value = "Manage project occurrences")
public interface OccurrencesEndpoint
{
  // TODO: browse response; response is a list + a next_page_token?

  @GET
  @Path("{project}/occurrences")
  @Produces(APPLICATION_JSON)
  @ApiOperation(value = "Browse project occurrences")
  @ApiResponses({
      @ApiResponse(code = 200, message = "Occurrences")
  })
  List<Occurrence> browse(@PathParam("project") String project,
                          @QueryParam("filter") @Nullable String filter,
                          @QueryParam("page_size") @Nullable Integer pageSize,
                          @QueryParam("page_token") @Nullable String pageToken);

  @GET
  @Path("{project}/occurrences/{name}")
  @Produces(APPLICATION_JSON)
  @ApiOperation(value = "Read project occurrence")
  @ApiResponses({
      @ApiResponse(code = 200, message = "Occurrence"),
      @ApiResponse(code = 404, message = "Occurrences not found")
  })
  Occurrence read(@PathParam("project") String project, @PathParam("name") String name);

  // FIXME: jax-rs 2.0 does not support HTTP PATCH :-\

  //@PATCH
  @PUT
  @Path("{project}/occurrences/{name}")
  @Consumes(APPLICATION_JSON)
  @Produces(APPLICATION_JSON)
  @ApiOperation(value = "Edit project occurrence")
  @ApiResponses({
      @ApiResponse(code = 200, message = "Occurrence edited"),
      @ApiResponse(code = 404, message = "Occurrence not found")
  })
  Occurrence edit(@PathParam("project") String project, @PathParam("name") String name, Occurrence occurrence);

  @POST
  @Path("{project}/occurrences")
  @Consumes(APPLICATION_JSON)
  @Produces(APPLICATION_JSON)
  @ApiOperation(value = "Add occurrence to project")
  @ApiResponses({
      @ApiResponse(code = 201, message = "Occurrence added")
  })
  Occurrence add(@PathParam("project") String project, Occurrence occurrence);

  @DELETE
  @Path("{project}/occurrences/{name}")
  @Produces(APPLICATION_JSON)
  @ApiOperation(value = "Delete project occurrence")
  @ApiResponses({
      @ApiResponse(code = 204, message = "Occurrence deleted"),
      @ApiResponse(code = 404, message = "Occurrence not found")
  })
  void delete(@PathParam("project") String project, @PathParam("name") String name);

  // FIXME: for some reason the path is 'notes' but the protobuf spec returns a single note

  @GET
  @Path("{project}/occurrences/{name}/notes")
  @Produces(APPLICATION_JSON)
  @ApiOperation(value = "Read note attached to occurrence")
  @ApiResponses({
      @ApiResponse(code = 200, message = "Note"),
      @ApiResponse(code = 404, message = "Occurrence not found")
  })
  Note readNote(@PathParam("project") String project, @PathParam("name") String name);
}