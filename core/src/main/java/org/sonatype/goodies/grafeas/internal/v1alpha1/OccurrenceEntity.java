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

import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiOccurrence;

import com.google.common.base.MoreObjects;

/**
 * {@link ApiOccurrence} entity.
 *
 * @since ???
 */
public class OccurrenceEntity
    implements Serializable
{
  private static final long serialVersionUID = 1L;

  private Long id;

  //@ColumnName("project_name")
  private String projectName;

  //@ColumnName("occurrence_name")
  private String occurrenceName;

  //@ColumnName("note_id")
  private Long noteId;

  private String data;

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(final String projectName) {
    this.projectName = projectName;
  }

  public String getOccurrenceName() {
    return occurrenceName;
  }

  public void setOccurrenceName(final String occurrenceName) {
    this.occurrenceName = occurrenceName;
  }

  public Long getNoteId() {
    return noteId;
  }

  public void setNoteId(final Long noteId) {
    this.noteId = noteId;
  }

  public String getData() {
    return data;
  }

  public void setData(final String data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("id", id)
        .add("projectName", projectName)
        .add("occurrenceName", occurrenceName)
        .add("noteId", noteId)
        .add("data", data)
        .toString();
  }

  /**
   * Convert entity to API model.
   */
  public ApiOccurrence asApi() {
    // FIXME:
    return new ApiOccurrence();
  }
}
