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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiProject;

import com.google.common.base.MoreObjects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link ApiProject} entity.
 *
 * @since ???
 */
@Entity
@Table(name = "projects")
public class ProjectEntity
{
  @Id
  @SequenceGenerator(name="projects_sequence_generator", sequenceName = "projects_sequence")
  @GeneratedValue(generator = "projects_sequence_generator")
  private Long id;

  @Column
  private String name;

  @SuppressWarnings("unused")
  public ProjectEntity() {
    // empty
  }

  public ProjectEntity(final String name) {
    this.name = checkNotNull(name);
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("id", id)
        .add("name", name)
        .toString();
  }
}
