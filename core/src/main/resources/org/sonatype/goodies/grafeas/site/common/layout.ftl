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

<#include "util.ftl"/>

<#--
Generate common head contents with optional title.
-->
<#macro head title="">
  <#compress>
    <head>
      <meta charset="UTF-8"/>
      <meta name="description" content="${applicationTitle}"/>
      <meta name="author" content="Sonatype, Inc."/>
      <meta name="keywords" content="Sonatype Grafeas,Grafeas,Sonatype">
      <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

      <title><#if title?has_content>${title} - </#if>${applicationTitle}</title>

      <#-- noinspection HtmlUnknownTarget -->
      <link rel="icon" type="image/x-icon" href="${basePath}/favicon.ico?${urlSuffix}"/>
      <link rel="icon" type="image/png" href="${basePath}/assets/images/favicon-16x16.png?${urlSuffix}" sizes="16x16"/>
      <link rel="icon" type="image/png" href="${basePath}/assets/images/favicon-32x32.png?${urlSuffix}" sizes="32x32"/>

      <#switch resourceMode>
        <#case "DEV">
          <@stylesheet "${basePath}/assets/bootstrap/css/bootstrap.css?${urlSuffix}"/>
          <@stylesheet "${basePath}/assets/fontawesome/css/fontawesome.css?${urlSuffix}"/>
          <@stylesheet "${basePath}/assets/service.css?${urlSuffix}"/>
          <#break>
        <#default>
          <@stylesheet "${basePath}/assets/service-all.css?${urlSuffix}"/>
      </#switch>

      <#-- expose some state to javascript from template -->
      <#--<script type="text/javascript">-->
        <#--window.app = {-->
          <#--basePath: "${basePath?js_string}",-->
          <#--baseUrl: "${baseUrl?js_string}",-->
          <#--path: "${path?js_string}",-->
          <#--anonymous: ${anonymous?c}-->
        <#--};-->
      <#--</script>-->

      <#-- include any other optional nested <head> content -->
      <#nested>
    </head>
  </#compress>
</#macro>

<#--
Generate script references to include at bottom of page for faster loading.
-->
<#macro scripts>
  <#switch resourceMode>
    <#case "DEV">
      <@javascript "${basePath}/assets/jquery/jquery.js?${urlSuffix}"/>
      <@javascript "${basePath}/assets/bootstrap/js/bootstrap.bundle.js?${urlSuffix}"/>
      <@javascript "${basePath}/assets/service.js?${urlSuffix}"/>
      <#break>
    <#default>
      <@javascript "${basePath}/assets/service-all.js?${urlSuffix}"/>
  </#switch>
</#macro>

<#macro navlink name title href>
  <a class="nav-link <#if (navbar_selected?? && navbar_selected==name)>active</#if>" href="${href}">${title}</a>
</#macro>

<#--
Generate common page header and navigation.
-->
<#macro header>
  <#compress>
    <header>
      <nav class="navbar fixed-top navbar-expand-lg navbar-dark bg-black bg-black">
        <div class="container-fluid">
          <a class="navbar-brand" href="${basePath}/">
            <img class="d-inline-block align-top mr-2" src="${basePath}/assets/images/sonatype-dark-32x32.png?${urlSuffix}" width="32" height="32" alt="">
            ${applicationTitle}
          </a>
          <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar-toggle" aria-controls="navbar-toggle" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>

          <#--<div class="collapse navbar-collapse" id="navbar-toggle">-->
            <#--<ul class="navbar-nav mr-auto">-->
              <#--<li class="nav-item"><@navlink "search", "Search", "${basePath}/search"/></li>-->
              <#--<li class="nav-item"><@navlink "docs", "Documentation", "${basePath}/docs"/></li>-->
            <#--</ul>-->
          <#--</div>-->
        </div>
      </nav>
    </header>
  </#compress>
</#macro>

<#--
Primary page layout.
-->
<#macro page>
  <body>
    <wrapper class="d-flex flex-column">
      <@header/>

      <main class="container flex-fill">
        <#-- show notification if there is one to display -->
        <#if notification?has_content>
          <div class="${notification.displayClass} alert-dismissible fade show" role="alert">
            <i class="${notification.iconClass} pr-2"></i>
            ${notification.message}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
              <span aria-hidden="true">
                <i class="far fa-times-circle"></i>
              </span>
            </button>
          </div>
        </#if>

        <#nested/>
      </main>
      <@footer/>
    </wrapper>
  </body>
</#macro>

<#--
Generate common page footer.
-->
<#macro footer default_scripts=true>
  <#compress>
    <footer class="footer container-fluid py-5 mt-3 bg-light">
      <div class="container">
        <div class="row">
          <div class="col-12 col-md">
            <h5><img src="${basePath}/assets/images/sonatype-24x24.png?${urlSuffix}" width="24" height="24" alt=""></h5>
            <small class="d-block mb-3 text-muted">&copy; 2018-present, Sonatype Inc.</small>
          </div>

          <@footer_section "About">
            <@footer_reference "${basePath}/about", applicationTitle/>
            <@footer_reference "https://sonatype.com", "Sonatype", true/>
          </@footer_section>

          <@footer_section "Resources">
            <@footer_reference "${basePath}/rest", "REST"/>
            <@footer_reference "${basePath}/contact", "Contact"/>
          </@footer_section>
        </div>
      </div>
    </footer>

    <#-- optionally include defaults scripts; disable for custom handling -->
    <#if default_scripts>
      <@scripts/>
    </#if>

    <#-- include any other optional nested content -->
    <#nested>
  </#compress>
</#macro>

<#--
Generate footer section.
-->
<#macro footer_section title>
  <div class="col-6 col-md">
    <h5>${title}</h5>
    <ul class="list-unstyled text-small">
      <#nested/>
    </ul>
  </div>
</#macro>

<#--
Generate footer reference.
-->
<#macro footer_reference href title external=false>
  <li><a class="text-muted" href="${href}" <#if external>target="_blank"</#if>>
    <#if external><small><i class="fas fa-external-link-alt"></i></small> </#if>${title}</a></li>
</#macro>
