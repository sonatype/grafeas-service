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

import io.dropwizard.jersey.errors.ErrorMessage;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Custom view for {@link ErrorMessage}.
 *
 * @see ErrorCustomizer
 * @since ???
 */
public class ErrorView
    extends SiteViewSupport
{
  private final ErrorMessage errorMessage;

  public ErrorView(final ErrorMessage errorMessage) {
    super("error.ftl");
    this.errorMessage = checkNotNull(errorMessage);
  }

  public Integer getCode() {
    return errorMessage.getCode();
  }

  public String getMessage() {
    return errorMessage.getMessage();
  }

  // ignore details, we will not expose this to rendered web-pages
}
