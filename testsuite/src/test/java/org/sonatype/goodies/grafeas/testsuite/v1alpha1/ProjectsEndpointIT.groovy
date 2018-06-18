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

import javax.ws.rs.core.Response.Status

import org.sonatype.goodies.dropwizard.client.endpoint.EndpointException
import org.sonatype.goodies.grafeas.api.v1alpha1.ProjectsEndpoint
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiProject
import org.sonatype.goodies.testsupport.TestSupport

import io.dropwizard.testing.ResourceHelpers
import org.junit.ClassRule
import org.junit.Test

import static org.junit.Assert.fail
import static org.sonatype.goodies.grafeas.testsuite.ResponseAssert.assertStatus

/**
 * {@link ProjectsEndpoint} tests.
 */
class ProjectsEndpointIT
  extends TestSupport
{
  @ClassRule
  public static final EndpointSupportRule dropwizard = new EndpointSupportRule(
      ResourceHelpers.resourceFilePath('service.yml')
  )

  @Test
  void 'look up missing project'() {
    def ep = dropwizard.endpoint(ProjectsEndpoint.class)
    try {
      ep.read("unique-${System.currentTimeMillis()}")
      fail()
    }
    catch (EndpointException e) {
      assertStatus(e.response, Status.NOT_FOUND)
    }
  }

  @Test
  void 'create project'() {
    def ep = dropwizard.endpoint(ProjectsEndpoint.class)

    def project1 = new ApiProject(
        name: 'foo'
    )
    log project1
    ep.add(project1)

    def project2 = ep.read('foo')
    log project2
    assert project2.name == 'foo'

    ep.browse(null, null, null).with {
      assert it.projects.size() == 1

      it.projects.each { project ->
        log project
      }
    }

    ep.delete('foo')

    try {
      ep.read('foo')
      fail()
    }
    catch (EndpointException e) {
      assertStatus(e.response, Status.NOT_FOUND)
    }
  }
}
