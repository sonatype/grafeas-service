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

import java.sql.Connection;
import java.util.List;

import org.sonatype.goodies.dropwizard.ApplicationCustomizer;
import org.sonatype.goodies.grafeas.GrafeasApplication;
import org.sonatype.goodies.grafeas.GrafeasConfiguration;

import com.google.inject.Module;
import io.dropwizard.db.ManagedDataSource;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import jersey.repackaged.com.google.common.collect.ImmutableList;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Database customizer.
 *
 * @since ???
 */
public class DatabaseCustomizer
    implements ApplicationCustomizer<GrafeasApplication,GrafeasConfiguration>
{
  private static final String MIGRATIONS_FILE = "migrations.xml";

  private static final Logger log = LoggerFactory.getLogger(DatabaseCustomizer.class);

  @Override
  public void initialize(final Bootstrap<GrafeasConfiguration> bootstrap) {
    // add support for db migration commands
    bootstrap.addBundle(new MigrationsBundle<GrafeasConfiguration>()
    {
      @Override
      public String name() {
        return "grafeas";
      }

      @Override
      public String getMigrationsFileName() {
        return MIGRATIONS_FILE;
      }

      @Override
      public PooledDataSourceFactory getDataSourceFactory(final GrafeasConfiguration config) {
        return config.getDatabaseConfiguration().getDataSourceFactory();
      }
    });
  }

  @Override
  public void customize(final GrafeasApplication application,
                        final GrafeasConfiguration config,
                        final Environment environment)
      throws Exception
  {
    DatabaseConfiguration databaseConfiguration = config.getDatabaseConfiguration();

    if (databaseConfiguration.isMigrate()) {
      log.info("Applying database migrations");
      PooledDataSourceFactory dataSourceFactory = databaseConfiguration.getDataSourceFactory();
      ManagedDataSource dataSource = dataSourceFactory.build(environment.metrics(), "database-migration");

      try (Connection connection = dataSource.getConnection()) {
        JdbcConnection jdbcConnection = new JdbcConnection(connection);
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);
        Liquibase liquibase = new Liquibase(
            MIGRATIONS_FILE,
            new ClassLoaderResourceAccessor(),
            database
        );

        liquibase.update(new Contexts(), new LabelExpression());
      }
      finally {
        dataSource.stop();
      }
    }
  }
}
