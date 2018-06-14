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

import java.util.Map;

import org.sonatype.goodies.dropwizard.ApplicationSupport;
import org.sonatype.goodies.dropwizard.swagger.SwaggerConfiguration;
import org.sonatype.goodies.dropwizard.swagger.SwaggerCustomizer;
import org.sonatype.goodies.dropwizard.view.InjectableViewBundle;
import org.sonatype.goodies.grafeas.internal.DatabaseCustomizer;
import org.sonatype.goodies.grafeas.internal.ObjectMapperFactory;

import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;

/**
 * ???
 *
 * @since ???
 */
public class GrafeasApplication
    extends ApplicationSupport<GrafeasConfiguration>
{
  @Override
  public String getName() {
    return "grafeas";
  }

  /**
   * Pre-injection and dynamic component detection initialization.
   */
  @Override
  protected void init(final Bootstrap<GrafeasConfiguration> bootstrap) {
    // enable environment variable substitution
    bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
        bootstrap.getConfigurationSourceProvider(),
        new EnvironmentVariableSubstitutor(false)
    ));

    // customize configuration factory
    bootstrap.setConfigurationFactoryFactory(new GrafeasConfigurationFactory.Factory());

    // customize object-mapper
    bootstrap.setObjectMapper(ObjectMapperFactory.create());

    // configure database
    addCustomizer(new DatabaseCustomizer());

    // enable asset support
    bootstrap.addBundle(new AssetsBundle("/assets", "/assets", null, "assets"));

    // enable injectable view support
    bootstrap.addBundle(new InjectableViewBundle<GrafeasConfiguration>()
    {
      @Override
      public Map<String, Map<String, String>> getViewConfiguration(final GrafeasConfiguration config) {
        return config.getViewRenderersConfiguration();
      }
    });

    // enable swagger support
    addCustomizer(new SwaggerCustomizer<GrafeasApplication, GrafeasConfiguration>()
    {
      @Override
      protected SwaggerConfiguration getSwaggerConfiguration(final GrafeasConfiguration config) {
        return config.getSwaggerConfiguration();
      }
    });
  }

  public static void main(final String[] args) throws Exception {
    new GrafeasApplication().run(args);
  }
}
