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
<#-- @ftlvariable name="" type="org.sonatype.goodies.grafeas.site.SiteViewSupport" -->
<#include "common/common.ftl">
<!DOCTYPE html>
<html lang="en">
<@head title="REST API">
  <@webfont "https://fonts.googleapis.com/css?family=Open+Sans:400,700|Source+Code+Pro:300,600|Titillium+Web:400,600,700"/>

  <#switch resourceMode>
    <#case "DEV">
      <@stylesheet "${basePath}/assets/swagger/swagger-ui.css?${urlSuffix}"/>
      <#break>
    <#default>
      <@stylesheet "${basePath}/assets/swagger-all.css?${urlSuffix}"/>
  </#switch>

  <style type="text/css">
    <#-- Hide the information container which duplicates title/description/etc -->
    <#--noinspection CssUnusedSymbol-->
    .information-container {
      display: none;
    }
  </style>
</@head>
<@page>
<div class="row">
  <div class="col">
    <h1><i class="fas fa-bed"></i> REST API</h1>
    <p class="lead text-muted">RESTful Application Programming Interface</p>

    <div id="swagger-ui"></div>
  </div>
</div>

<#switch resourceMode>
  <#case "DEV">
    <@javascript "${basePath}/assets/swagger/swagger-ui-bundle.js?${urlSuffix}"/>
    <@javascript "${basePath}/assets/swagger/swagger-ui-standalone-preset.js?${urlSuffix}"/>
    <#break>
  <#default>
    <@javascript "${basePath}/assets/swagger-all.js?${urlSuffix}"/>
</#switch>

<script type="text/javascript">
  window.onload = function () {
    // overrides the Topbar component to return nothing
    function HideTopbarPlugin() {
      return {
        components: {
          Topbar: function () {
            return null;
          }
        }
      }
    }

    // Build a system
    const ui = SwaggerUIBundle({
      url: "${baseUrl}/swagger.json?${urlSuffix}",
      dom_id: '#swagger-ui',
      deepLinking: true,
      validatorUrl: null,
      docExpansion: 'list',
      apisSorter: 'alpha',
      operationsSorter: 'alpha',
      presets: [
        SwaggerUIBundle.presets.apis,
        SwaggerUIStandalonePreset
      ],
      plugins: [
        SwaggerUIBundle.plugins.DownloadUrl,
        HideTopbarPlugin
      ],
      layout: "StandaloneLayout"
    });

    window.ui = ui
  }
</script>
</@page>
</html>
