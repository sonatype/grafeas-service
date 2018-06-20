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

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiNote;

import com.google.common.base.MoreObjects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link ApiNote} entity.
 *
 * @since ???
 */
@Entity
@Table(name = "notes")
public class NoteEntity
    implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @SequenceGenerator(name="notes_sequence_generator", sequenceName = "notes_sequence")
  @GeneratedValue(generator = "notes_sequence_generator")
  private Long id;

  @Column(name = "project_name")
  private String projectName;

  @Column(name = "note_name")
  private String noteName;

  @Column
  @Convert(converter = ApiNoteConverter.class)
  private ApiNote data;

  /**
   * Resolves occurrences attached to the note.
   */
  @SuppressWarnings("unused")
  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "note_id")
  private List<OccurrenceEntity> occurrences;

  @SuppressWarnings("unused")
  public NoteEntity() {
    // empty
  }

  public NoteEntity(final String projectName, final String noteName, final ApiNote data) {
    this.projectName = checkNotNull(projectName);
    this.noteName = checkNotNull(noteName);
    this.data = checkNotNull(data);
  }

  public Long getId() {
    return id;
  }

  public String getProjectName() {
    return projectName;
  }

  public String getNoteName() {
    return noteName;
  }

  public String getName() {
    return getNoteName();
  }

  public ApiNote getData() {
    return data;
  }

  public void setData(final ApiNote data) {
    this.data = data;
  }

  public List<OccurrenceEntity> getOccurrences() {
    return occurrences;
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
}
