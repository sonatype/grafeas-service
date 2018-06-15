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
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

/**
 * {@link ProjectEntity} data-access object.
 *
 * @since ???
 */
public interface ProjectsDao
{
  // FIXME: need to expose/handling cursor details for #browse

  @SqlQuery("SELECT * FROM projects")
  @RegisterBeanMapper(ProjectEntity.class)
  List<ProjectEntity> browse();

  @SqlQuery("SELECT * FROM projects WHERE name = :name")
  @RegisterBeanMapper(ProjectEntity.class)
  @Nullable
  ProjectEntity read(@Bind("name") String name);

  // no edit

  @SqlUpdate("INSERT INTO projects (name) VALUES (:name)")
  @GetGeneratedKeys("id")
  long add(@Bind("name") String name);

  @SqlUpdate("DELETE FROM projects WHERE name = :name")
  void delete(@Bind("name") String name);
}
