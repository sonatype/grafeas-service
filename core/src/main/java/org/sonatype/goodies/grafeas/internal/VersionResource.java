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

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.goodies.dropwizard.ApplicationVersion;
import org.sonatype.goodies.dropwizard.jaxrs.ResourceSupport;
import org.sonatype.goodies.grafeas.api.Version;
import org.sonatype.goodies.grafeas.api.VersionEndpoint;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link VersionEndpoint} resource.
 *
 * @since ???
 */
@Named
@Singleton
@Path("/api/version")
public class VersionResource
    extends ResourceSupport
    implements VersionEndpoint
{
  private final ApplicationVersion version;

  @Inject
  public VersionResource(final ApplicationVersion version) {
    this.version = checkNotNull(version);
  }

  @Override
  public Version get() {
    return new Version(version.getVersion(), version.getBuildTimestamp(), version.getBuildTag());
  }
}
