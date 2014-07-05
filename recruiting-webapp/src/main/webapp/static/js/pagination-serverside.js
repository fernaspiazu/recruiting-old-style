/*
 * =============================================================================
 *
 *   Copyright (c) 2014, Fernando Aspiazu
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */

function datatableServerSide(params) {
  return $('.tableServerSide').css({'width' : '100%'}).dataTable({
    "bProcessing" : true,
    "bServerSide" : true,
    "sAjaxSource" : params.ajaxSource,
    "sServerMethod" : "GET",
    "aaSorting": [],
    "bAutoWidth" : false,
    "aoColumns": params.columns,
    "fnRowCallback" : params.rowCallback,
    "fnServerParams" : params.serverParamsCallback,
    "bFilter": false,
    //"sDom": "ifrtp",
    "sDom": "<'row'<'col-xs-6'l><'col-xs-6'f>r>t<'row'<'col-xs-6'i><'col-xs-6'p>>",
    "sPaginationType": "bootstrap",
    "bDeferRender": true,
    "aoColumnDefs": [
      {"bSortable": false, "aTargets": [ "nosort" ]}
    ],
    "oLanguage": oLanguage,
    "iDisplayLength": 10,
    "bLengthChange": true,
    "bInfo": true
  });
}