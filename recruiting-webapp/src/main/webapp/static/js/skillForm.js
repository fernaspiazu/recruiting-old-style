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

var $skillForm = (function skillForm() {

	var skills = [];
	var skillLabel = '';

	function configure(params) {
		skills = params.skills;
		skillLabel = params.skillLabel;
	}

	function processSkillForm() {
		$("#skillFormModal").on("show.bs.modal", function (e) {
			for (var i = 0; i < skills.length; i++) {
				$(createNewSkillRow(skills[i])).insertBefore($('#linkAddNewSkill'));
			}
			$(createNewSkillRow('')).insertBefore($('#linkAddNewSkill'));
		});

		$("#skillFormModal").on("hidden.bs.modal", function (e) {
			$("#skillForm").find(".row").remove();
		});

		$('#linkAddNewSkill').click(function () {
			$(createNewSkillRow('')).insertBefore($('#linkAddNewSkill'));
		});
	}

	function createNewSkillRow(value) {
		return $('<div class="row">').append(
				'<div class="form-group col-md-11">' +
				'<label class="sr-only">' + skillLabel + '</label>' +
				'<input type="text" name="skill" value="' + value + '" class="form-control"/>' +
				'</div>' +
				'<div class="form-group col-md-1">' +
				'<button type="button" class="close" onclick="$skillForm.dropSkill(this);">&times;</button>' +
				'</div>'
		);
	}

	function dropSkill(button) {
		$(button).closest('.row').remove();
	}

	return {
		configure : configure,
		processSkillForm : processSkillForm,
		dropSkill : dropSkill
	}

}());