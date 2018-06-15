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
package org.sonatype.goodies.grafeas.internal.v1alpha1;

import java.io.Serializable;

import com.google.common.base.MoreObjects;
import io.grafeas.model.ApiProject;

/**
 * {@link ApiProject} entity.
 *
 * @since ???
 */
public class ProjectEntity
    implements Serializable
{
  private static final long serialVersionUID = 1L;

  private Long id;

  private String name;

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("id", id)
        .add("name", name)
        .toString();
  }

  /**
   * Convert entity to API model.
   */
  public ApiProject asApi() {
    ApiProject result = new ApiProject();
    result.setName(name);
    return result;
  }
}
