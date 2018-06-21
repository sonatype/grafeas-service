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
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.print.attribute.standard.MediaSize.NA;

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

  /**
   * Entity key.
   */
  @Id
  @Column(name = "key")
  @SequenceGenerator(name = "notes_sequence_generator", sequenceName = "notes_sequence")
  @GeneratedValue(generator = "notes_sequence_generator")
  private Long key;

  @Column(name = "project_id")
  private String projectId;

  @Column(name = "note_id")
  private String noteId;

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

  public NoteEntity(final String projectId, final String noteId, final ApiNote data) {
    this.projectId = checkNotNull(projectId);
    this.noteId = checkNotNull(noteId);
    this.data = checkNotNull(data);
  }

  public Long getKey() {
    return key;
  }

  public String getProjectId() {
    return projectId;
  }

  public String getProjectName() {
    return ProjectEntity.name(projectId);
  }

  public String getNoteId() {
    return noteId;
  }

  public String getNoteName() {
    return name(projectId, noteId);
  }

  /**
   * @see #getNoteName()
   */
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
        .add("key", key)
        .add("projectId", projectId)
        .add("noteId", noteId)
        .add("data", data)
        .toString();
  }

  //
  // Helpers
  //

  private static final String NAME_PREFIX = "projects/%s/notes/";

  /**
   * Convert note-id to note-name.
   */
  public static String name(final String projectId, final String noteId) {
    checkNotNull(projectId);
    checkNotNull(noteId);

    return String.format(NAME_PREFIX + "%s", projectId, noteId);
  }

  /**
   * Extract note-id from note-name.
   */
  @Nullable
  public static String extractId(final String projectId, final String noteName) {
    checkNotNull(projectId);
    checkNotNull(noteName);

    String prefix = String.format(NAME_PREFIX, projectId);
    if (noteName.startsWith(prefix)) {
      return noteName.substring(prefix.length(), noteName.length());
    }

    return null;
  }
}
