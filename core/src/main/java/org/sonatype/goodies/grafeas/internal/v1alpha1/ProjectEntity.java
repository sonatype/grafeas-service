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
import java.util.List;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Null;

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
    implements Serializable
{
  private static final long serialVersionUID = 1L;

  /**
   * Entity key.
   */
  @Id
  @Column(name = "key")
  @SequenceGenerator(name="projects_sequence_generator", sequenceName = "projects_sequence")
  @GeneratedValue(generator = "projects_sequence_generator")
  private Long key;

  @Column(name = "project_id")
  private String projectId;

  @SuppressWarnings("unused")
  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id", referencedColumnName = "project_id")
  private List<NoteEntity> notes;

  @SuppressWarnings("unused")
  public ProjectEntity() {
    // empty
  }

  public ProjectEntity(final String projectId) {
    this.projectId = checkNotNull(projectId);
  }

  public Long getKey() {
    return key;
  }

  public String getProjectId() {
    return projectId;
  }

  public String getProjectName() {
    return name(projectId);
  }

  public List<NoteEntity> getNotes() {
    return notes;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("key", key)
        .add("projectId", projectId)
        .toString();
  }

  //
  // Helpers
  //

  private static String PREFIX = "projects/";

  /**
   * Convert project-id to project-name.
   */
  public static String name(final String projectId) {
    checkNotNull(projectId);
    return String.format("%s%s", PREFIX, projectId);
  }

  /**
   * Extract project-id from project-name.
   */
  @Nullable
  public static String extractId(final String projectName) {
    checkNotNull(projectName);
    if (projectName.startsWith(PREFIX)) {
      return projectName.substring(PREFIX.length(), projectName.length());
    }
    return null;
  }
}
