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
package org.sonatype.goodies.grafeas.internal.v1alpha1

import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiNote
import org.sonatype.goodies.grafeas.internal.ObjectMapperFactory
import org.sonatype.goodies.testsupport.TestSupport

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectReader
import org.junit.Test

/**
 * JSON merging trials.
 */
class JsonMergeTrial
    extends TestSupport
{
  private ObjectMapper objectMapper = ObjectMapperFactory.create()

  @Test
  void 'merge models'() {
    def note1 = new ApiNote(
        name: 'foo'
    )

    def note2 = new ApiNote(
        shortDescription: 'foo'
    )

    ObjectReader reader = objectMapper.readerForUpdating(note1)
    JsonNode updates = objectMapper.valueToTree(note2)

    ApiNote updated = reader.readValue(updates)
    log updated

    assert updated.name == 'foo'
    assert updated.shortDescription == 'foo'
  }
}
