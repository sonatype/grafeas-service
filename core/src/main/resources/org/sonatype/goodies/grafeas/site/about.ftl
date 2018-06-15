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
<#assign page_title="About"/>
<@head title="${page_title}"/>
<@page>
  <div class="row">
    <div class="col">
      <h1><i class="fas fa-info-circle"></i> ${page_title}</h1>

      <p class="lead text-muted">
        ${loremIpsum(20)}
      </p>

      <h2>Attributions</h2>

      <h3>Bootstrap</h3>
      <p>
        <@extlink "https://getbootstrap.com/", "Bootstrap"/> is an open source toolkit for developing with HTML,
        CSS, and JS. Quickly prototype your ideas or build your entire app with our Sass variables and
        mixins, responsive grid system, extensive prebuilt components, and powerful plugins built on jQuery.
      </p>

      <h3>jQuery</h3>
      <p>
        <@extlink "https://jquery.com/", "jQuery"/> is a fast, small, and feature-rich JavaScript library. It
        makes things like HTML document traversal and manipulation, event handling, animation, and Ajax much
        simpler with an easy-to-use API that works across a multitude of browsers. With a combination of
        versatility and extensibility, jQuery has changed the way that millions of people write JavaScript.
      </p>

      <h3>Font Awesome</h3>
      <p>
        <@extlink "https://fontawesome.com/", "Font Awesome Free"/> is free, open source, and GPL friendly.
        You can use it for commercial projects, open source projects, or really almost whatever you want.
      </p>

      <h3>Swagger</h3>
      <p>
        <@extlink "https://swagger.io/", "Swagger"/> is the world’s largest framework of API developer tools for
        the OpenAPI Specification(OAS), enabling development across the entire API lifecycle, from design and
        documentation, to test and deployment.
      </p>

      <h3>Open Sans</h3>
      <p>
        <@extlink "https://fonts.google.com/specimen/Open+Sans", "Open Sans"/> is a humanist sans serif typeface
        designed by Steve Matteson, Type Director of Ascender Corp. This version contains the complete 897
        character set, which includes the standard ISO Latin 1, Latin CE, Greek and Cyrillic character sets.
        Open Sans was designed with an upright stress, open forms and a neutral, yet friendly appearance. It was
        optimized for print, web, and mobile interfaces, and has excellent legibility characteristics in its
        letterforms.
      </p>

      <h3>Source Code Pro</h3>
      <p>
        <@extlink "https://fonts.google.com/specimen/Source+Code+Pro", "Source Code Pro"/> was designed by
        Paul D.  Hunt as a companion to Source Sans. This complementary family was adapted from the Source
        design due to a request to create a monospaced version for coding applications. Source Code preserves
        the design features and vertical proportions of Source Sans, but alters the glyph widths so that they
        are uniform across all glyphs and weights.
      </p>

      <h3>Titillium Web</h3>
      <p>
        <@extlink "https://fonts.google.com/specimen/Titillium+Web", "Titillium Web"/> is born inside the
        Accademia di Belle Arti di Urbino as a didactic project Course Type design of the Master of Visual
        Design Campi Visivi.
      </p>
    </div>
  </div>
</@page>
</html>