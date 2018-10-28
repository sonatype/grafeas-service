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
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiListNoteOccurrencesResponse;
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiListNotesResponse;
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiNote;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

// SEE: https://github.com/grafeas/grafeas/blob/master/v1alpha1/docs/GrafeasApi.md
// SEE: https://github.com/grafeas/grafeas/blob/master/v1alpha1/proto/grafeas.proto

/**
 * Notes endpoint.
 *
 * @since ???
 */
@Path("/v1alpha1/projects")
@Api(value = "Manage project notes")
public interface NotesEndpoint
{
  @GET
  @Path("{project_id}/notes")
  @Produces(APPLICATION_JSON)
  @ApiOperation(value = "Browse project notes")
  @ApiResponses({
      @ApiResponse(code = 200, message = "Notes")
  })
  ApiListNotesResponse browse(@PathParam("project_id") @ApiParam("Project ID") String projectId,
                              @QueryParam("filter") @Nullable String filter,
                              @QueryParam("page_size") @Nullable Integer pageSize,
                              @QueryParam("page_token") @Nullable String pageToken);

  @GET
  @Path("{project_id}/notes/{note_id}")
  @Produces(APPLICATION_JSON)
  @ApiOperation(value = "Read project note")
  @ApiResponses({
      @ApiResponse(code = 200, message = "Note"),
      @ApiResponse(code = 404, message = "Note not found")
  })
  ApiNote read(@PathParam("project_id") @ApiParam("Project ID") String projectId,
               @PathParam("note_id") @ApiParam("Note ID") String noteId);

  // FIXME: need clarification on what the HTTP PATCH for grafeas specification actually implements

  @PATCH
  @Path("{project_id}/notes/{note_id}")
  @Consumes(APPLICATION_JSON)
  @Produces(APPLICATION_JSON)
  @ApiOperation(value = "Edit project note")
  @ApiResponses({
      @ApiResponse(code = 200, message = "Note edited"),
      @ApiResponse(code = 404, message = "Note not found")
  })
  ApiNote edit(@PathParam("project_id") @ApiParam("Project ID") String projectId,
               @PathParam("note_id") @ApiParam("Note ID") String noteId,
               @ApiParam("Note") ApiNote note);

  @POST
  @Path("{project_id}/notes")
  @Consumes(APPLICATION_JSON)
  @Produces(APPLICATION_JSON)
  @ApiOperation(value = "Add note to project")
  @ApiResponses({
      @ApiResponse(code = 201, message = "Note added")
  })
  ApiNote add(@PathParam("project_id") @ApiParam("Project ID") String projectId,
              @ApiParam("Note") ApiNote note);

  @DELETE
  @Path("{project_id}/notes/{note_id}")
  @Produces(APPLICATION_JSON)
  @ApiOperation(value = "Delete project note")
  @ApiResponses({
      @ApiResponse(code = 204, message = "Note deleted"),
      @ApiResponse(code = 404, message = "Note not found")
  })
  void delete(@PathParam("project_id") @ApiParam("Project ID") String projectId,
              @PathParam("note_id") @ApiParam("Note ID") String noteId);

  @GET
  @Path("{project_id}/notes/{note_id}/occurrences")
  @Produces(APPLICATION_JSON)
  @ApiOperation(value = "Browse occurrences referencing project note")
  @ApiResponses({
      @ApiResponse(code = 200, message = "Occurrences"),
      @ApiResponse(code = 404, message = "Note not found")
  })
  ApiListNoteOccurrencesResponse readOccurrences(@PathParam("project_id") @ApiParam("Project ID") String projectId,
                                                 @PathParam("note_id") @ApiParam("Note ID") String noteId);
}
