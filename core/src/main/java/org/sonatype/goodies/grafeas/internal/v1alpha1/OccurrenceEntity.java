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
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiOccurrence;

import com.google.common.base.MoreObjects;

/**
 * {@link ApiOccurrence} entity.
 *
 * @since ???
 */
@Entity
@Table(name = "occurrences")
public class OccurrenceEntity
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "project_name")
  private String projectName;

  @Column(name = "occurrence_name")
  private String occurrenceName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "note_id")
  private NoteEntity note;

  @Column
  @Convert(converter = ApiOccurrenceConverter.class)
  private ApiOccurrence data;

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
        .add("id", id)
        .add("projectName", projectName)
        .add("occurrenceName", occurrenceName)
        .add("note", note)
        .add("data", data)
        .toString();
  }
}
