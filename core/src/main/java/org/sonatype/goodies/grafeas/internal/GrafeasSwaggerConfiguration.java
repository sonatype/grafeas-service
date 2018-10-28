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
package org.sonatype.goodies.grafeas.internal;

import org.sonatype.goodies.dropwizard.swagger.SwaggerConfiguration;

/**
 * Grafeas {@link SwaggerConfiguration}.
 *
 * @since ???
 */
public class GrafeasSwaggerConfiguration
    extends SwaggerConfiguration
{
  public GrafeasSwaggerConfiguration() {
    setTitle("Sonatype Grafeas REST API");
    // FIXME: this may not be appropriate if more than one version is eventually supported (ie v1alpha1 and v1beta1)?
    setVersion("v1alpha");
    setDescription("RESTful Application Programming Interface");
  }
}
