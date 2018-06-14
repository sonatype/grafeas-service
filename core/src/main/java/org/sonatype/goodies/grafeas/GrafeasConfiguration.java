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

import java.util.Collections;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.sonatype.goodies.dropwizard.swagger.SwaggerConfiguration;
import org.sonatype.goodies.grafeas.site.SiteConfiguration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * ???
 *
 * @since ???
 */
public class GrafeasConfiguration
    extends Configuration
{
  @Valid
  @NonNull
  @JsonProperty("database")
  private DataSourceFactory dataSourceFactory = new DataSourceFactory();

  public DataSourceFactory getDataSourceFactory() {
    return dataSourceFactory;
  }

  public void setDataSourceFactory(final DataSourceFactory dataSourceFactory) {
    this.dataSourceFactory = dataSourceFactory;
  }

  // SEE https://freemarker.apache.org/docs/api/freemarker/template/Configuration.html#setSetting-java.lang.String-java.lang.String-

  @NotNull
  private Map<String, Map<String, String>> viewRenderersConfiguration = Collections.emptyMap();

  @JsonProperty("viewRenderers")
  public Map<String, Map<String, String>> getViewRenderersConfiguration() {
    return viewRenderersConfiguration;
  }

  @JsonProperty("viewRenderers")
  public void setViewRenderersConfiguration(final Map<String, Map<String, String>> config) {
    ImmutableMap.Builder<String, Map<String, String>> builder = ImmutableMap.builder();
    for (Map.Entry<String, Map<String, String>> entry : config.entrySet()) {
      builder.put(entry.getKey(), ImmutableMap.copyOf(entry.getValue()));
    }
    this.viewRenderersConfiguration = builder.build();
  }

  @Valid
  @NotNull
  @JsonProperty("site")
  private SiteConfiguration siteConfiguration = new SiteConfiguration();

  public SiteConfiguration getSiteConfiguration() {
    return siteConfiguration;
  }

  public void setSiteConfiguration(final SiteConfiguration siteConfiguration) {
    this.siteConfiguration = siteConfiguration;
  }

  @NotNull
  @JsonProperty("swagger")
  private SwaggerConfiguration swaggerConfiguration = new SwaggerConfiguration();

  public SwaggerConfiguration getSwaggerConfiguration() {
    return swaggerConfiguration;
  }

  public void setSwaggerConfiguration(final SwaggerConfiguration swaggerConfiguration) {
    this.swaggerConfiguration = swaggerConfiguration;
  }
}
