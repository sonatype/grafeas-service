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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

// SEE: https://github.com/grafeas/grafeas/blob/master/v1alpha1/docs/GrafeasProjectsApi.md

/**
 * Projects endpoint.
 *
 * @since ???
 */
@Path("/v1alpha1/projects")
public interface ProjectsEndpoint
{
  @GET
  @Produces(APPLICATION_JSON)
  List<Project> browse(@QueryParam("filter") @Nullable String filter,
                       @QueryParam("page_size") @Nullable Integer pageSize,
                       @QueryParam("page_token") @Nullable String pageToken);

  @GET
  @Path("{name}")
  @Produces(APPLICATION_JSON)
  @Nullable
  Project read(@PathParam("name") String name);

  // no edit

  @POST
  @Consumes(APPLICATION_JSON)
  void add(Project project);

  @DELETE
  @Path("{name}")
  void delete(@PathParam("name") String name);
}
