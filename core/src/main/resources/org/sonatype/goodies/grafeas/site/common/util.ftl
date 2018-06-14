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

<#--
Generate a style-sheet reference.
-->
<#macro stylesheet url>
  <link rel="stylesheet" href="${url}" type="text/css"/>
</#macro>

<#--
Generate a web-font reference.
-->
<#macro webfont url>
  <link rel="stylesheet" href="${url}" type="text/css" crossorigin="anonymous"/>
</#macro>

<#--
Generate a javascript reference.
-->
<#macro javascript url, flags="">
  <script src="${url}" type="text/javascript" ${flags}></script>
</#macro>

<#--
Generate a pluralized string.
-->
<#function plural count singular plural="">
  <#if count == 1>
    <#return "${count} ${singular}">
  <#else>
    <#if plural == "">
      <#local plural="${singular}s">
    </#if>
    <#return "${count} ${plural}">
  </#if>
</#function>

<#--
Helper to generate missing-data representation.  Does not include any surrounding containers.
-->
<#macro missing_data text>
  <#compress>
    <i class="fas fa-ban"></i> ${text}
  </#compress>
</#macro>

<#--
Render an external link with title.

If title is longer than max_length, then title will be truncated.
-->
<#macro extlink url title max_length=100>
  <#compress>
    <#if (title?length > max_length)><#local title="${title[0..(max_length-1)]} ..."/></#if>
      <span style="display: inline; white-space: nowrap;"><a href="${url}"><small><i class="fas fa-external-link-alt"></i></small> ${title}</a></span>
  </#compress>
</#macro>

<#--
Render a raw URL link.
-->
<#macro rawlink url>
  <@extlink url, url/>
</#macro>

<#--
Render a mailto link.
-->
<#macro maillink url>
  <#compress>
    <span style="display: inline; white-space: nowrap;"><a href="${url}"><small><i class="far fa-envelope"></i></small> ${url}</a></span>
  </#compress>
</#macro>

<#--
Generate section table full of entries; Nested content should render a single section entry.
-->
<#macro section_table entries columns>
  <#local row_count=(entries?size / columns)?ceiling/>
  <#local max_cols=row_count * columns/>
    <div class="container-fluid mb-4">
      <div class="row">
        <#list entries as entry>
          <div class="col-md">
            <#nested entry/>
          </div>
            <#if (entry?counter % columns == 0) && entry?has_next>
              <div class="w-100 mb-3"></div>
            </#if>
            <#-- pad remaining colums if any to prefent content leakage -->
            <#if !entry?has_next && (entry?counter % columns != 0)>
              <#list (entry?counter + 1)..(max_cols) as ignored>
                <div class="col-md"></div>
              </#list>
            </#if>
        </#list>
      </div>
    </div>
</#macro>