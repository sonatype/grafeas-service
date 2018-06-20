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
<#-- @ftlvariable name="" type="org.sonatype.goodies.grafeas.site.ValidationErrorView" -->
<#include "common/common.ftl">
<!DOCTYPE html>
<html lang="en">
<#assign page_title="Validation Error"/>
<@head title="${page_title}"/>
<@page>
<div class="row">
  <div class="col">
    <h1><i class="fas fa-exclamation-triangle text-warning"></i> ${page_title}</h1>
    <p class="lead text-muted">Input validation errors detected!</p>

    <h2>Errors</h2>
    <ul>
      <#list errors as error>
        <li>${error}</li>
      </#list>
    </ul>
  </div>
</div>
</@page>
</html>
