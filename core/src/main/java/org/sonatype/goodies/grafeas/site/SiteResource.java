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
package org.sonatype.goodies.grafeas.site;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.sonatype.goodies.dropwizard.jaxrs.ResourceSupport;

import io.dropwizard.views.View;

/**
 * Site resource.
 *
 * @since ???
 */
@Named
@Singleton
@Path("/")
@Produces(MediaType.TEXT_HTML)
public class SiteResource
    extends ResourceSupport
{
  /**
   * Expose legacy {@code favicon.ico}.
   */
  @GET
  @Path("favicon.ico")
  @Produces("image/x-icon")
  public Response favicon(@Context final UriInfo uriInfo) {
    return Response.seeOther(uriInfo.getBaseUri().resolve("/assets/images/favicon.ico")).build();
  }

  private View view(final String template) {
    return new SiteViewSupport(template);
  }

  @GET
  public View welcome() {
    return new SiteViewSupport("welcome.ftl");
  }

  /**
   * Support for direct {@code /index.html} resolution for welcome page.
   */
  @GET
  @Path("index.html")
  public View index() {
    return welcome();
  }

  @GET
  @Path("about")
  public View about() {
    return view("about.ftl");
  }

  @GET
  @Path("contact")
  public View contact() {
    return view("contact.ftl");
  }

  @GET
  @Path("rest")
  public View rest() {
    return view("rest.ftl");
  }

  @GET
  @Path("tos")
  public View tos() {
    return view("tos.ftl");
  }
}
