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

import org.sonatype.goodies.grafeas.api.v1alpha1.Note;

import com.google.common.base.MoreObjects;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link Note} entity.
 *
 * @since ???
 */
public class NoteEntity
    implements Serializable
{
  private static final long serialVersionUID = 1L;

  public NoteEntity() {
    // empty
  }

  public NoteEntity(final Note note) {
    checkNotNull(note);

    // TODO:
  }

  private Long id;

  @ColumnName("project_name")
  private String projectName;

  @ColumnName("note_name")
  private String noteName;

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

  public String getNoteName() {
    return noteName;
  }

  public void setNoteName(final String noteName) {
    this.noteName = noteName;
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
        .add("noteName", noteName)
        .add("data", data)
        .toString();
  }

  /**
   * Convert entity to API model.
   */
  public Note asApi() {
    // FIXME:
    return new Note();
  }
}
