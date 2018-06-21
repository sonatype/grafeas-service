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
<#-- @ftlvariable name="" type="org.sonatype.goodies.grafeas.site.NoteView" -->
<#include "common/common.ftl">
<!DOCTYPE html>
<html lang="en">
<#assign page_title="Note"/>
<#--<#assign navbar_selected="projects"/>-->
<@head title="${page_title}"/>
<@page>
<div class="row">
  <div class="col">
    <h1><i class="fas fa-sticky-note"></i> ${page_title}</h1>
    <p class="lead text-muted">
      ${note.noteName}
    </p>

    <dl class="row">
      <dt class="col-sm-2">Project</dt>
      <dd class="col-sm-9"><a href="${basePath}/project/${projectId}">
        <i class="fas fa-cube"></i> ${projectName}</a></dd>

      <#-- TODO: add more bits from data model -->

      <dt class="col-sm-2">Occurrences</dt>
      <dd class="col-sm-9"><a href="${basePath}/project/${projectId}/note/${note.noteId}/occurrences">
        <i class="fas fa-location-arrow"></i> ${plural(note.occurrences?size, "occurrence")}</a></dd>
    </dl>
</div>
</@page>
</html>
