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
package org.sonatype.goodies.grafeas;

import java.io.IOException;

import javax.annotation.Nullable;
import javax.validation.Validator;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.configuration.ConfigurationException;
import io.dropwizard.configuration.ConfigurationFactory;
import io.dropwizard.configuration.ConfigurationFactoryFactory;
import io.dropwizard.configuration.YamlConfigurationFactory;
import io.dropwizard.jackson.LogbackModule;

/**
 * {@link GrafeasConfiguration} factory.
 *
 * @since ???
 */
public class GrafeasConfigurationFactory
    extends YamlConfigurationFactory<GrafeasConfiguration>
{
  public GrafeasConfigurationFactory(final Class<GrafeasConfiguration> klass,
                                     @Nullable final Validator validator,
                                     final ObjectMapper objectMapper,
                                     final String propertyPrefix)
  {
    super(klass, validator, objectMapper, propertyPrefix);
  }

  @Override
  protected GrafeasConfiguration build(final JsonNode node, final String path)
      throws IOException, ConfigurationException
  {
    GrafeasConfiguration config = super.build(node, path);
    customize(config);
    return config;
  }

  private void customize(final GrafeasConfiguration config) {
    // TODO: remove if nothing is needed here
  }

  public static class Factory
      implements ConfigurationFactoryFactory<GrafeasConfiguration>
  {
    @Override
    public ConfigurationFactory<GrafeasConfiguration> create(final Class<GrafeasConfiguration> type,
                                                              final Validator validator,
                                                              final ObjectMapper objectMapper,
                                                              final String propertyPrefix)
    {
      return new GrafeasConfigurationFactory(type, validator, configure(objectMapper.copy()), propertyPrefix);
    }

    /**
     * Adjust {@link ObjectMapper} to include some more specifics and modules.
     */
    private ObjectMapper configure(final ObjectMapper objectMapper) {
      objectMapper.registerModule(new LogbackModule());
      objectMapper.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
      return objectMapper;
    }
  }
}
