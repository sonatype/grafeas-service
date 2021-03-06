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
$(function() {
  $(document).ready(function() {
    // SEE: layout.ftl <@head>
    var app = window.app;
    console.log("App:", app);

    // initialize all tooltips
    $('[data-toggle="tooltip"]').tooltip({
      // delay showing, hiding tooltips slightly
      delay: {show: 1000, hide: 250}
    });

    // custom handling for dropdown button tooltips
    var dropdown = $('.dropdown-tooltip');
    dropdown.tooltip({
      // delay showing, hiding tooltips slightly
      delay: {show: 1000, hide: 250},
      trigger: 'hover'
    });
    dropdown.on('click', function() {
      $(this).tooltip('hide');
    });
  });
});