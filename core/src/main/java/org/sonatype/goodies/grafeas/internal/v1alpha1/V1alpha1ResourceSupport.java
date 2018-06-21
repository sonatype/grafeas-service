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

import java.io.IOException;

import javax.inject.Inject;

import org.sonatype.goodies.dropwizard.jaxrs.ResourceSupport;
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiNote;
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiOccurrence;
import org.sonatype.goodies.grafeas.api.v1alpha1.model.ApiProject;
import org.sonatype.goodies.grafeas.internal.db.DatabaseObjectMapperFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static org.sonatype.goodies.dropwizard.jaxrs.WebPreconditions.checkRequest;

/**
 * Support for {@code v1alpha1} resources.
 *
 * @since ???
 */
public abstract class V1alpha1ResourceSupport
    extends ResourceSupport
{
  //
  // Data-access objects
  //

  private ProjectEntityDao projectDao;

  private NoteEntityDao noteDao;

  private OccurrenceEntityDao occurrenceDao;

  @Inject
  public void setProjectDao(final ProjectEntityDao projectDao) {
    this.projectDao = checkNotNull(projectDao);
  }

  @Inject
  public void setNoteDao(final NoteEntityDao noteDao) {
    this.noteDao = checkNotNull(noteDao);
  }

  @Inject
  public void setOccurrenceDao(final OccurrenceEntityDao occurrenceDao) {
    this.occurrenceDao = checkNotNull(occurrenceDao);
  }

  protected ProjectEntityDao getProjectDao() {
    checkState(projectDao != null);
    return projectDao;
  }

  protected NoteEntityDao getNoteDao() {
    checkState(noteDao != null);
    return noteDao;
  }

  protected OccurrenceEntityDao getOccurrenceDao() {
    checkState(occurrenceDao != null);
    return occurrenceDao;
  }

  //
  // Name resolvers
  //

  protected static String projectName(final String projectId) {
    return String.format("projects/%s", projectId);
  }

  protected static String noteName(final String projectId, final String nodeId) {
    return String.format("projects/%s/notes/%s", projectId, nodeId);
  }

  protected static String occurrenceName(final String projectId, final String occurrenceId) {
    return String.format("projects/%s/occurrences/%s", projectId, occurrenceId);
  }

  //
  // Converters
  //

  protected ApiProject convert(final ProjectEntity entity) {
    String projectName = entity.getProjectName();
    checkNotNull(projectName);
    return new ApiProject().name(projectName);
  }

  protected ApiNote convert(final NoteEntity entity) {
    ApiNote model = entity.getData();
    checkNotNull(model);
    return model;
  }

  protected ApiOccurrence convert(final OccurrenceEntity entity) {
    ApiOccurrence model = entity.getData();
    checkNotNull(model);
    return model;
  }

  //
  // Modal merge
  //

  private static final ObjectMapper objectMapper = DatabaseObjectMapperFactory.create();

  protected <T> T merge(final T basis, final T updates) {
    ObjectReader reader = objectMapper.readerForUpdating(basis);
    JsonNode node = objectMapper.valueToTree(updates);
    try {
      return reader.readValue(node);
    }
    catch (IOException e) {
      throw new RuntimeException(String.format("Unable to merge: %s into %s", updates, basis), e);
    }
  }

  //
  // Helpers
  //

  /**
   * Ensure project with given name exists.
   */
  protected ProjectEntity ensureProjectExists(final String projectId) {
    ProjectEntity entity = getProjectDao().read(projectId);
    checkRequest(entity != null, "Invalid project");
    return entity;
  }
}
