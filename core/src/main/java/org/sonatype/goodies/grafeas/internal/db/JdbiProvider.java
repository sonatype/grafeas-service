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

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.grafeas.GrafeasConfiguration;

import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.h2.H2DatabasePlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

// FIXME: not a great name, should pick something better

/**
 * Provides the primary {@link Jdbi} singleton for the database.
 *
 * @since ???
 */
@Named
@Singleton
public class JdbiProvider
{
  private static final Logger log = LoggerFactory.getLogger(JdbiProvider.class);

  private final Environment environment;

  private final GrafeasConfiguration configuration;

  @Inject
  public JdbiProvider(final Environment environment, final GrafeasConfiguration configuration) {
    this.environment = checkNotNull(environment);
    this.configuration = checkNotNull(configuration);
  }

  public Jdbi get() {
    DatabaseConfiguration databaseConfiguration = configuration.getDatabaseConfiguration();

    JdbiFactory jdbiFactory = new JdbiFactory()
    {
      @Override
      protected void configure(final Jdbi jdbi) {
        super.configure(jdbi);

        // FIXME: install h2 plugin, probably want to make this aware if the data-source is actually h2 or not?
        jdbi.installPlugin(new H2DatabasePlugin());
      }
    };

    return jdbiFactory.build(environment, databaseConfiguration.getDataSourceFactory(), "database");
  }
}
