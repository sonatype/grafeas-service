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
<#-- @ftlvariable name="" type="org.sonatype.goodies.grafeas.site.ProjectView" -->
<#include "common/common.ftl">
<!DOCTYPE html>
<html lang="en">
<#assign page_title="Project"/>
<#assign navbar_selected="projects"/>
<@head title="${page_title}"/>
<@page>
<div class="row">
  <div class="col">
    <h1><i class="fas fa-cube"></i> ${page_title}</h1>
    <p class="lead text-muted">
      ${project.name}
    </p>

    <h2>Notes</h2>
    <#list project.notes?sort_by("name")>
      <div class="table-responsive">
        <table class="table table-striped table-hover">
          <caption>${plural(project.notes?size, "note")}</caption>
          <thead>
          <tr>
            <th scope="col">Name</th>
            <th scope="col">Description</th>
          </tr>
          </thead>
          <tbody>
            <#items as note>
            <#assign data=note.data/>
            <tr>
              <td>
                <a href="${basePath}/project/${project.name}/note/${note.name}"><i class="fas fa-sticky-note"></i> ${note.name}</a>
              </td>
              <td>
                <#-- generated models have fluent setters which conflict with default simple property access -->
                <#if data.getShortDescription()?has_content>
                  ${data.getShortDescription()}
                <#else>
                  <@missing_data "no description"/>
                </#if>
              </td>
            </tr>
            </#items>
          </tbody>
        </table>
      </div>
    <#else>
      <p class="text-muted"><@missing_data "no notes found"/></p>
    </#list>
  </div>
</div>
</@page>
</html>
