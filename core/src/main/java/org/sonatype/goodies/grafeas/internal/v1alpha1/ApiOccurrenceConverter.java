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
package org.sonatype.goodies.grafeas.internal.v1alpha1;

import javax.persistence.Converter;

import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiOccurrence;
import org.sonatype.goodies.grafeas.internal.db.JsonAttributeConverterSupport;

/**
 * {@link ApiOccurrence} JSON-converter.
 *
 * @since ???
 */
@Converter
public class ApiOccurrenceConverter
    extends JsonAttributeConverterSupport<ApiOccurrence>
{
  public ApiOccurrenceConverter() {
    super(ApiOccurrence.class);
  }
}