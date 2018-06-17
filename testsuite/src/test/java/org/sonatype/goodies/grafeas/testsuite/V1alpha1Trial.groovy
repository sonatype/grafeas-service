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

import javax.ws.rs.client.Client
import javax.ws.rs.core.Response.Status

import org.sonatype.goodies.dropwizard.client.endpoint.EndpointException
import org.sonatype.goodies.dropwizard.client.endpoint.EndpointFactory
import org.sonatype.goodies.grafeas.api.v1alpha1.ProjectsEndpoint
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiProject

import org.junit.Before
import org.junit.Test

import static org.junit.Assert.fail
import static org.sonatype.goodies.grafeas.testsuite.ResponseAssert.assertStatus

/**
 * Trials for v1alpha1.
 */
class V1alpha1Trial
  extends TestsuiteSupportIT
{
  private Client client

  @Before
  void setUp() {
    client = dropwizard.client()
  }

  private <T> T endpoint(final Class<T> type) {
    def target = client.target(baseUrl).path('api')
    return EndpointFactory.create(type, target)
  }

  @Test
  void 'create project'() {
    def ep = endpoint(ProjectsEndpoint.class)

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
