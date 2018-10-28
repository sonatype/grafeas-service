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

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiOccurrence;

import com.google.common.base.MoreObjects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link ApiOccurrence} entity.
 *
 * @since ???
 */
@Entity
@Table(name = "occurrences")
public class OccurrenceEntity
    implements Serializable
{
  private static final long serialVersionUID = 1L;

  /**
   * Entity key.
   */
  @Id
  @Column(name = "key")
  @SequenceGenerator(name="occurrences_sequence_generator", sequenceName = "occurrences_sequence")
  @GeneratedValue(generator = "occurrences_sequence_generator")
  private Long key;

  @Column(name = "project_id")
  private String projectId;

  @Column(name = "occurrence_id")
  private String occurrenceId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "note_key")
  private NoteEntity note;

  @Column
  @Convert(converter = ApiOccurrenceConverter.class)
  private ApiOccurrence data;

  @SuppressWarnings("unused")
  public OccurrenceEntity() {
    // empty
  }

  public OccurrenceEntity(final String projectId,
                          final String occurrenceId,
                          final NoteEntity note,
                          final ApiOccurrence data)
  {
    this.projectId = checkNotNull(projectId);
    this.occurrenceId = checkNotNull(occurrenceId);
    this.note = checkNotNull(note);
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

  public String getOccurrenceId() {
    return occurrenceId;
  }

  public String getOccurrenceName() {
    return name(projectId, occurrenceId);
  }

  /**
   * @see #getOccurrenceName()
   */
  public String getName() {
    return getOccurrenceName();
  }

  public NoteEntity getNote() {
    return note;
  }

  public void setNote(final NoteEntity note) {
    this.note = note;
  }

  public ApiOccurrence getData() {
    return data;
  }

  public void setData(final ApiOccurrence data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("key", key)
        .add("projectId", projectId)
        .add("occurrenceId", occurrenceId)
        .add("note", note)
        .add("data", data)
        .toString();
  }

  //
  // Helpers
  //

  private static final String NAME_PREFIX = "projects/%s/occurrences/";

  /**
   * Convert occurrence-id to occurrence-name.
   */
  public static String name(final String projectId, final String occurrenceId) {
    checkNotNull(projectId);
    checkNotNull(occurrenceId);

    return String.format(NAME_PREFIX + "%s", projectId, occurrenceId);
  }

  /**
   * Extract occurrence-id from occurrence-name.
   */
  @Nullable
  public static String extractId(final String projectId, final String occurrenceName) {
    checkNotNull(projectId);
    checkNotNull(occurrenceName);

    String prefix = String.format(NAME_PREFIX, projectId);
    if (occurrenceName.startsWith(prefix)) {
      return occurrenceName.substring(prefix.length(), occurrenceName.length());
    }

    return null;
  }
}
