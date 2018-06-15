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

import java.util.List;

import javax.annotation.Nullable;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

/**
 * {@link NoteEntity} data-access object.
 *
 * @since ???
 */
public interface NotesDao
{
  // FIXME: need to expose/handling cursor details for #browse

  @SqlQuery("SELECT * FROM notes WHERE project_name = :project")
  @RegisterBeanMapper(NoteEntity.class)
  List<NoteEntity> browse(@Bind("project") String project);

  @SqlQuery("SELECT * FROM notes WHERE id = :id")
  @RegisterBeanMapper(NoteEntity.class)
  @Nullable
  NoteEntity read(@Bind("id") long id);

  @SqlQuery("SELECT * FROM notes WHERE project_name = :project AND name = :name")
  @RegisterBeanMapper(NoteEntity.class)
  @Nullable
  NoteEntity read(@Bind("project") String project, @Bind("name") String name);

  // FIXME: need to figure out how edit works

  //@SqlUpdate("INSERT INTO notes (project_name, note_name, data) VALUES (:projectName, :noteName, :data)")
  //void edit(@BindBean NoteEntity note);

  @SqlUpdate("INSERT INTO notes (project_name, note_name, data) VALUES (:projectName, :noteName, :data)")
  @GetGeneratedKeys("id")
  long add(@BindBean NoteEntity note);

  @SqlUpdate("DELETE FROM notes WHERE project_name = :project AND name = :name")
  void delete(@Bind("project") String project, @Bind("name") String name);
}
