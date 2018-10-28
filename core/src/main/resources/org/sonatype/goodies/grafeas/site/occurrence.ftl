<#--

    Copyright (c) 2018-present Sonatype, Inc. All rights reserved.

    This program is licensed to you under the Apache License Version 2.0,
    and you may not use this file except in compliance with the Apache License Version 2.0.
    You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.

    Unless required by applicable law or agreed to in writing,
    software distributed under the Apache License Version 2.0 is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.

-->
<#-- @ftlvariable name="" type="org.sonatype.goodies.grafeas.site.OccurrenceView" -->
<#include "common/common.ftl">
<!DOCTYPE html>
<html lang="en">
<#assign page_title="Occurrence"/>
<#--<#assign navbar_selected="projects"/>-->
<@head title="${page_title}"/>
<@page>
<div class="row">
  <div class="col">
    <h1><i class="fas fa-location-arrow"></i> ${page_title}</h1>
    <p class="lead text-muted">
      ${occurrence.occurrenceName}
    </p>

    <dl class="row">
      <dt class="col-sm-2">Project</dt>
      <dd class="col-sm-9"><a href="${basePath}/project/${projectId}">
        <i class="fas fa-cube"></i> ${projectName}</a></dd>

      <dt class="col-sm-2">Note</dt>
      <dd class="col-sm-9"><a href="${basePath}/project/${projectId}/note/${noteId}">
        <i class="fas fa-sticky-note"></i> ${noteName}</a></dd>

      <#-- TODO: add more bits from data model -->
    </dl>
</div>
</@page>
</html>
