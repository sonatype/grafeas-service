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
package org.sonatype.goodies.grafeas.testsuite

import org.sonatype.goodies.grafeas.GrafeasApplication
import org.sonatype.goodies.grafeas.GrafeasConfiguration
import org.sonatype.goodies.testsupport.TestSupport

import groovy.transform.Memoized
import io.dropwizard.testing.ResourceHelpers
import io.dropwizard.testing.junit.DropwizardAppRule
import org.junit.ClassRule

/**
 * Support for integration-tests.
 */
abstract class TestsuiteSupportIT
    extends TestSupport
{
  static {
    System.setProperty('test.log.level', 'DEBUG')
  }

  // TODO: may need a way to allow sub-class to provide configuration file

  @ClassRule
  public static final DropwizardAppRule<GrafeasConfiguration> dropwizard = new DropwizardAppRule<>(
      GrafeasApplication.class,
      ResourceHelpers.resourceFilePath('service.yml')
  )

  @Memoized
  URI getBaseUrl() {
    // trailing "/" is important
    return URI.create("http://localhost:${dropwizard.localPort}/")
  }

  @Memoized
  URI getAdminUrl() {
    // trailing "/" is important
    return URI.create("http://localhost:${dropwizard.adminPort}/")
  }
}
