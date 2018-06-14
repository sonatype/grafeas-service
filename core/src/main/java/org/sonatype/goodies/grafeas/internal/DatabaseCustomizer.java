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
package org.sonatype.goodies.grafeas.internal;

import org.sonatype.goodies.dropwizard.ApplicationCustomizer;
import org.sonatype.goodies.grafeas.GrafeasApplication;
import org.sonatype.goodies.grafeas.GrafeasConfiguration;

import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.migrations.CloseableLiquibase;
import io.dropwizard.migrations.CloseableLiquibaseWithClassPathMigrationsFile;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.h2.H2DatabasePlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ???
 *
 * @since ???
 */
public class DatabaseCustomizer
    implements ApplicationCustomizer<GrafeasApplication,GrafeasConfiguration>
{
  private static final Logger log = LoggerFactory.getLogger(DatabaseCustomizer.class);

  @Override
  public void initialize(final Bootstrap<GrafeasConfiguration> bootstrap) {
    bootstrap.addBundle(new MigrationsBundle<GrafeasConfiguration>()
    {
      @Override
      public PooledDataSourceFactory getDataSourceFactory(final GrafeasConfiguration config) {
        return config.getDataSourceFactory();
      }
    });
  }

  @Override
  public void customize(final GrafeasApplication application,
                        final GrafeasConfiguration config,
                        final Environment environment)
  {
    PooledDataSourceFactory dataSourceFactory = config.getDataSourceFactory();

    JdbiFactory jdbiFactory = new JdbiFactory();
    Jdbi jdbi = jdbiFactory.build(environment, dataSourceFactory, "database");
    jdbi.installPlugin(new H2DatabasePlugin());

    log.debug("JDBI: {}", jdbi);

    //CloseableLiquibase liquibase = new CloseableLiquibaseWithClassPathMigrationsFile(
    //    dataSourceFactory, jdbi, "migrations.xml");
  }
}
