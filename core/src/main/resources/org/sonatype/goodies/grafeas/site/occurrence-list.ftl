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
<#-- @ftlvariable name="" type="org.sonatype.goodies.grafeas.site.OccurrenceListView" -->
<#include "common/common.ftl">
<!DOCTYPE html>
<html lang="en">
<#assign page_title="Occurrences"/>
<#--<#assign navbar_selected="projects"/>-->
<@head title="${page_title}"/>
<@page>
<div class="row">
  <div class="col">
    <h1><i class="fas fa-location-arrow"></i> ${page_title}</h1>

    <dl class="row">
      <dt class="col-sm-2">Project</dt>
      <dd class="col-sm-9"><a href="${basePath}/project/${project.projectId}">
        <i class="fas fa-cube"></i> ${project.projectName}</a></dd>
    </dl>

    <#list occurrences?sort_by("occurrenceName")>
      <div class="table-responsive">
        <table class="table table-striped table-hover">
          <caption>${plural(occurrences?size, "occurrence")}</caption>
          <thead>
          <tr>
            <th scope="col">Name</th>
          </tr>
          </thead>
          <tbody>
            <#items as occurrence>
            <#assign data=occurrence.data/>
            <tr>
              <td>
                <a href="${basePath}/project/${project.projectId}/occurrence/${occurrence.occurrenceId}">
                  <i class="fas fa-location-arrow"></i> ${occurrence.occurrenceName}</a>
              </td>
            </tr>
            </#items>
          </tbody>
        </table>
      </div>
    <#else>
      <p class="text-muted"><@missing_data "no occurences found"/></p>
    </#list>
  </div>
</div>
</@page>
</html>
