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
package org.sonatype.goodies.grafeas.site;

import javax.inject.Inject;

import org.sonatype.goodies.dropwizard.ApplicationVersion;
import org.sonatype.goodies.dropwizard.util.Version;
import org.sonatype.goodies.dropwizard.view.ViewSupport;
import org.sonatype.goodies.grafeas.GrafeasConfiguration;

/**
 * Support for site views.
 *
 * @since ???
 */
public class SiteViewSupport
    extends ViewSupport
{
  @Inject
  private GrafeasConfiguration configuration;

  @Inject
  private ApplicationVersion version;

  public SiteViewSupport(final String templateName) {
    super(templateName);
  }

  /**
   * Expose application version.
   */
  public String getApplicationVersion() {
    return version.getVersion();
  }

  /**
   * Expose application title.
   */
  public String getApplicationTitle() {
    return "Sonatype Grafeas";
  }

  /**
   * Expose site resource-mode.
   */
  public SiteConfiguration.ResourceMode getResourceMode() {
    return configuration.getSiteConfiguration().getResourceMode();
  }

  /**
   * Cache URL suffix for duration of service lifetime.
   */
  private static volatile String urlSuffix;

  /**
   * Expose URL suffix for cache-busting.
   */
  public String getUrlSuffix() {
    if (urlSuffix == null) {
      String version = getApplicationVersion();
      if (version.contains("SNAPSHOT") || Version.UNKNOWN.equals(version)) {
        version = String.valueOf(System.currentTimeMillis());
      }
      urlSuffix = "_v=" + version;
    }
    return urlSuffix;
  }
}
