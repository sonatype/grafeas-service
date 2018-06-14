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
package org.sonatype.goodies.grafeas.internal.api.v1alpha1;

import java.util.List;

import javax.annotation.Nullable;

import org.sonatype.goodies.grafeas.api.v1alpha1.Project;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

/**
 * Projects data-access object.
 *
 * @since ???
 */
public interface ProjectsDao
{
  // FIXME: need to expose/handling cursor details for #browse

  @SqlQuery("SELECT * FROM projects")
  @RegisterBeanMapper(Project.class)
  List<Project> browse();

  @SqlQuery("SELECT * FROM projects WHERE name = :name")
  @RegisterBeanMapper(Project.class)
  @Nullable
  Project read(@Bind("name") String name);

  // no edit

  @SqlUpdate("INSERT INTO projects (name) VALUES (:name)")
  void add(@Bind("name") String name);

  @SqlUpdate("DELETE FROM projects WHERE name = :name")
  void delete(@Bind("name") String name);
}
