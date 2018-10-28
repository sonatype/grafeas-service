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
package org.sonatype.goodies.grafeas.testsuite.v1alpha1

import org.sonatype.goodies.grafeas.GrafeasApplication
import org.sonatype.goodies.grafeas.GrafeasConfiguration

import io.dropwizard.testing.ConfigOverride

/**
 * Support rule for endpoint tests.
 */
class EndpointSupportRule
    extends org.sonatype.goodies.dropwizard.testsupport.EndpointSupportRule<GrafeasApplication, GrafeasConfiguration>
{
  EndpointSupportRule(final String configPath, final ConfigOverride... configOverrides) {
    super(GrafeasApplication.class, configPath, configOverrides)
  }

  @Override
  def <E> E endpoint(final Class<E> type) {
    return super.endpoint(type, "api")
  }
}
