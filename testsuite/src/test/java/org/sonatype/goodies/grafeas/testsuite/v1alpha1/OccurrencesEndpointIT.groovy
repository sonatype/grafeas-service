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

import org.sonatype.goodies.grafeas.api.v1alpha1.NotesEndpoint
import org.sonatype.goodies.grafeas.api.v1alpha1.OccurrencesEndpoint
import org.sonatype.goodies.grafeas.api.v1alpha1.ProjectsEndpoint
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiNote
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiOccurrence
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiProject
import org.sonatype.goodies.testsupport.TestSupport

import io.dropwizard.testing.ResourceHelpers
import org.junit.ClassRule
import org.junit.Test

/**
 * {@link OccurrencesEndpoint} tests.
 */
class OccurrencesEndpointIT
  extends TestSupport
{
  @ClassRule
  public static final EndpointSupportRule dropwizard = new EndpointSupportRule(
      ResourceHelpers.resourceFilePath('service.yml')
  )

  @Test
  void 'create occurrence'() {
    def projects = dropwizard.endpoint(ProjectsEndpoint.class)
    def notes = dropwizard.endpoint(NotesEndpoint.class)
    def occurrences = dropwizard.endpoint(OccurrencesEndpoint.class)

    projects.add(new ApiProject(
        name: 'foo'
    ))

    def note1 = notes.add('foo', new ApiNote(
        name: 'note1',
        shortDescription: 'first note'
    ))
    log note1

    def occurrence1 = occurrences.add('foo', new ApiOccurrence(
        name: 'occurrence1',
        noteName: 'note1'
    ))
    log occurrence1

    def note2 = occurrences.readNote('foo', 'occurrence1')
    log note2
    assert note2.name == note1.name
    assert note2.shortDescription == note1.shortDescription

    notes.readOccurrences('foo', 'note1').with { related ->
      log related
      related.occurrences.each {
        log it
      }
    }
  }
}
