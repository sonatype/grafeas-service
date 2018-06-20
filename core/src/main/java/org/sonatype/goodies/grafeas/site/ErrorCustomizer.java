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

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.dropwizard.EnvironmentCustomizer;

import io.dropwizard.jersey.errors.ErrorEntityWriter;
import io.dropwizard.jersey.errors.ErrorMessage;
import io.dropwizard.jersey.validation.ValidationErrorMessage;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.View;

import static javax.ws.rs.core.MediaType.TEXT_HTML_TYPE;

/**
 * Error view customizer.
 *
 * @see ErrorView
 * @see ValidationErrorView
 * @since ???
 */
@Named
@Singleton
public class ErrorCustomizer
    implements EnvironmentCustomizer
{
  @Override
  public void customize(final Environment environment) {
    // adapt ErrorMessage to view
    environment.jersey().register(new ErrorEntityWriter<ErrorMessage, View>(TEXT_HTML_TYPE, View.class)
    {
      @Override
      protected View getRepresentation(final ErrorMessage entity) {
        return new ErrorView(entity);
      }
    });

    // adapt ValidationErrorMessage to view
    environment.jersey().register(new ErrorEntityWriter<ValidationErrorMessage, View>(TEXT_HTML_TYPE, View.class)
    {
      @Override
      protected View getRepresentation(final ValidationErrorMessage entity) {
        return new ValidationErrorView(entity);
      }
    });
  }
}
