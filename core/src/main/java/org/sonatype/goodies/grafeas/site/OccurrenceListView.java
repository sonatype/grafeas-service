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

import java.util.List;

import org.sonatype.goodies.grafeas.internal.v1alpha1.OccurrenceEntity;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Occurrence list view.
 *
 * @since ???
 */
public class OccurrenceListView
  extends SiteViewSupport
{
  private final String projectId;

  private final String noteId;

  private final List<OccurrenceEntity> occurrences;

  public OccurrenceListView(final String projectId, final String noteId, final List<OccurrenceEntity> occurrences) {
    super("occurrence-list.ftl");
    this.projectId = checkNotNull(projectId);
    this.noteId = checkNotNull(noteId);
    this.occurrences = checkNotNull(occurrences);
  }

  public String getProjectId() {
    return projectId;
  }

  public String getNoteId() {
    return noteId;
  }

  public List<OccurrenceEntity> getOccurrences() {
    return occurrences;
  }
}
