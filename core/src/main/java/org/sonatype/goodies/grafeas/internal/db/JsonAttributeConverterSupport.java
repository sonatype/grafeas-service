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
package org.sonatype.goodies.grafeas.internal.db;

import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Support for JSON {@link AttributeConverter} implementations.
 *
 * @since ???
 */
public abstract class JsonAttributeConverterSupport<T>
    implements AttributeConverter<T, String>
{
  private static final ObjectMapper objectMapper = DatabaseObjectMapperFactory.create();

  private final Logger log = LoggerFactory.getLogger(getClass());

  private final Class<T> type;

  public JsonAttributeConverterSupport(final Class<T> type) {
    this.type = checkNotNull(type);
  }

  @Override
  public String convertToDatabaseColumn(final T model) {
    log.trace("Converting to column: {}", model);
    try {
      String value = objectMapper.writeValueAsString(model);
      log.trace("Converted: {}", value);
      return value;
    }
    catch (Exception e) {
      throw new RuntimeException(String.format("Failed to convert to String column: %s", model), e);
    }
  }

  @Override
  public T convertToEntityAttribute(final String value) {
    log.trace("Converting to model: {}", value);
    try {
      T model = objectMapper.readValue(value, type);
      log.trace("Converted: {}", model);
      return model;
    }
    catch (Exception e) {
      throw new RuntimeException(String.format("Failed to convert to %s model: %s", type.getSimpleName(), value), e);
    }
  }
}
