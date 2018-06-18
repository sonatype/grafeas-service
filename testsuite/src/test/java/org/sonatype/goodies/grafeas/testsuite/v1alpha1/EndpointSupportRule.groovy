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

import org.sonatype.goodies.dropwizard.client.endpoint.EndpointFactory
import org.sonatype.goodies.grafeas.GrafeasApplication
import org.sonatype.goodies.grafeas.GrafeasConfiguration

import groovy.transform.Memoized
import io.dropwizard.testing.ConfigOverride
import io.dropwizard.testing.junit.DropwizardAppRule

/**
 * Support rule for endpoint tests.
 */
class EndpointSupportRule
    extends DropwizardAppRule<GrafeasConfiguration>
{
  EndpointSupportRule(final String configPath, final ConfigOverride... configOverrides) {
    super(GrafeasApplication.class, configPath, configOverrides)
  }

  @Memoized
  URI getBaseUrl() {
    // trailing "/" is important
    return URI.create("http://localhost:${localPort}/")
  }

  @Memoized
  URI getAdminUrl() {
    // trailing "/" is important
    return URI.create("http://localhost:${adminPort}/")
  }

  def <T> T endpoint(final Class<T> type) {
    def target = client().target(baseUrl).path('api')
    return EndpointFactory.create(type, target)
  }
}
