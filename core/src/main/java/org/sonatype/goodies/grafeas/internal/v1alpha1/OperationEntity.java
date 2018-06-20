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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;

/**
 * Operation entity.
 *
 * @since ???
 */
@Entity
@Table(name="operations")
public class OperationEntity
    implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @SequenceGenerator(name="operations_sequence_generator", sequenceName = "operations_sequence")
  @GeneratedValue(generator = "operations_sequence_generator")
  private Long id;

  @Column(name = "project_name")
  private String projectName;

  @Column(name = "operation_name")
  private String operationName;

  // FIXME: resolve what the datatype is here ApiCreateOperationRequest or LongrunningOperation?
  // FIXME: looks like its LongrunningOperation, but this has presently wrinkles requiring protobuf encoding :-\

  @Column
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

  public String getOperationName() {
    return operationName;
  }

  /**
   * @see #getOperationName()
   */
  public String getName() {
    return getOperationName();
  }

  public void setOperationName(final String operationName) {
    this.operationName = operationName;
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
        .add("operationName", operationName)
        .add("data", data)
        .toString();
  }
}
