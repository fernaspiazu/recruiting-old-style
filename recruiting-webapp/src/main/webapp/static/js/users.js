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

$user = {

	init: function (params) {
		this.paginate();

		$(params.userFormModal).on("shown.bs.modal", function (e) {
			$("#username").focus();
		});

		$(params.userFormModal).on("hidden.bs.modal", function (e) {
			$("#userModelForm").find("input, select").each(function (i, element) {
				resetAllErrorMessages();
				$(element).val('');
			});
		});

		$("#newUserButton").click(function () {
			$("#password").parent().parent().show();
			$('#userModelForm').attr('action', contextRoot + '/user/save');
			$('#submitEvent').html(params.saveLabelBtn).val('save');
		});

		$('#deleteUserBtn').click(function () {
			window.location = evaluateContextRoot() + '/user/delete?' + $.param({'userId': $(this).val()});
		});
	},

	paginate: function () {
		var datatableParams = {
			ajaxSource: evaluateContextRoot() + '/user/load-users',
			columns: [
				{"mData": "username", "sClass": "username", bAutoWidth: false},
				{"mData": "role_name", "sClass": "role_name", bAutoWidth: false},
				{"mData": "lastName", "sClass": "lastName", bAutoWidth: false},
				{"mData": "firstName", "sClass": "firstName", bAutoWidth: false},
				{"mData": "email", "sClass": "email", bAutoWidth: false},
				{"mData": "id", "sClass": "editUser", bSortable: false, sWidth: "30px"},
				{"mData": "id", "sClass": "deleteUser", bSortable: false, sWidth: "30px"},
				{"mData": "id", "sClass": "changePassword", bSortable: false, sWidth: "30px"}
			],
			rowCallback: function (nRow, aData, iDisplayIndex) {
				$(nRow).attr('id', aData['id']);
				$(nRow).find('td.editUser').css({'text-align': 'center'}).html('').append('<div class="glyphicon glyphicon-edit click-icon" onclick="_editUser(this);" title="Edit"></div>');
				$(nRow).find('td.deleteUser').css({'text-align': 'center'}).html('').append('<div class="glyphicon glyphicon-trash click-icon" onclick="_deleteUser(this);" title="Delete"></div>');
				$(nRow).find('td.changePassword').css({'text-align': 'center'}).html('').append('<div class="glyphicon glyphicon-lock click-icon" onclick="window.location = evaluateContextRoot()+\'/user/change-password?\'+$.param({\'userId\':$(this).closest(\'tr\').attr(\'id\')});" title="Change Password"></div>');
			}
		};

		return datatableServerSide(datatableParams);
	}

};

function _editUser(element) {
	var userId = $(element).closest('tr').attr('id');
	var url = contextRoot + "/user/edit";
	$.get(url, {"userId": userId}, function (data) {
		$('#userId').val(data.userId);
		$('#username').val(data.username).focus();
		$('#firstName').val(data.firstName);
		$('#lastName').val(data.lastName);
		$('#email').val(data.email);
		$('#role').val(data.role.roleId);
		$("#password").parent().parent().hide();
		$('#userModelForm').attr('action', contextRoot + '/user/update');
		$('#submitEvent').html(userParams.updateLabelBtn).val('update');
		$("#userFormModal").modal({ 'show': true, 'backdrop': 'static' });
	});
}

function _deleteUser(element) {
	var userId = $(element).closest('tr').attr('id');
	$("#deleteUserMessageModal").modal({
		'show': true,
		'backdrop': 'static'
	});
	$('#deleteUserBtn').val(userId);
}
