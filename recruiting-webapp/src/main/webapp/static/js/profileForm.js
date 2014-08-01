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

var $profileForm = (function profileForm() {

	var saveLabel = '';
	var updateLabel = '';

	function config(params) {
		saveLabel = params.saveLabel;
		updateLabel = params.updateLabel;
		return this;
	}

	function processProfileForm() {
		/*<![CDATA[*/
		$('#btnSubmitExperience').click(function () {
			executePost("#experienceForm", "/consultant/validate-experience");
		});

		$('#btnAddExperience').click(function () {
			clearFormInputs('#experienceForm');
			var saveLabel = /*[[#{global.save}]]*/ 'Save';
			$('#experienceForm').attr('action', contextRoot + '/consultant/save-experience');
			$('#btnSubmitExperience').html(saveLabel).val('saveExperience');
		});

		$('.edit-experience').click(function () {
			clearFormInputs('#experienceForm');
			var url = contextRoot + '/consultant/edit-experience';
			var params = {'experienceId': $(this).closest('div.row').attr('id')};
			$.get(url, params, function (data) {
				$('#experienceId').val(data.id);
				$('#companyName').val(data.companyName);
				$('#position').val(data.position);
				$('#locality').val(data.locality);
				$('#monthFrom').val(data.monthFrom);
				$('#yearFrom').val(data.yearFrom);
				$('#monthTo').val(data.monthTo);
				if (eval(data.current)) {
					$('#periodToBlock').addClass('hidden');
					$('#presentPeriodBlock').removeClass('hidden');
					$('#isCurrent-endDate-position').attr('checked', 'checked');
				} else {
					$('#presentPeriodBlock').addClass('hidden');
					$('#periodToBlock').removeClass('hidden');
					$('#isCurrent-endDate-position').removeAttr('checked');
				}
				$('#yearTo').val(data.yearTo);
				$('#description').val(data.description);
				var updateLabel = /*[[#{global.update}]]*/ 'Update';
				$('#experienceForm').attr('action', contextRoot + '/consultant/update-experience');
				$('#btnSubmitExperience').html(updateLabel).val('updateExperience');
				$("#experienceFormModal").modal({
					'show': true,
					'backdrop': 'static'
				});
			});
		});

		$('.delete-experience').click(function () {
			$("#deleteExperienceMessageModal").modal({
				'show': true,
				'backdrop': 'static'
			});
			$('#btnDeleteExperience').val($(this).closest('div.row').attr('id'));
		});

		$('#btnDeleteExperience').click(function () {
			window.location = contextRoot + '/consultant/delete-experience?' + $.param({'experienceId': $(this).val()});
		});


		$('#btnSubmitEducation').click(function () {
			executePost("#educationForm", "/consultant/validate-education");
		});

		$('#btnAddEducation').click(function () {
			clearFormInputs('#educationForm');
			var saveLabel = /*[[#{global.save}]]*/ 'Save';
			$('#educationForm').attr('action', contextRoot + '/consultant/save-education');
			$('#btnSubmitEducation').html(saveLabel).val('saveExperience');
		});

		$('.edit-education').click(function () {
			clearFormInputs('#educationForm');
			var url = contextRoot + '/consultant/edit-education';
			var params = {'educationId': $(this).closest('div.row').attr('id')};
			$.get(url, params, function (data) {
				$('#educationId').val(data.id);
				$('#school').val(data.school);
				$('#startYear').val(data.startYear);
				$('#endYear').val(data.endYear);
				$('#schoolDegree').val(data.schoolDegree);
				$('#schoolFieldOfStudy').val(data.schoolFieldOfStudy);
				$('#schoolGrade').val(data.schoolGrade);
				$('#schoolActivities').val(data.schoolActivities);
				if (eval(data.current)) {
					$('#endYearBlock').addClass('hidden');
					$('#currentEducationBlock').removeClass('hidden');
					$('#isCurrent-education').attr('checked', 'checked');
				} else {
					$('#endYearBlock').removeClass('hidden');
					$('#currentEducationBlock').addClass('hidden');
					$('#isCurrent-education').removeAttr('checked');
				}
				$('#educationDescription').val(data.description);
				var updateLabel = /*[[#{global.update}]]*/ 'Update';
				$('#educationForm').attr('action', contextRoot + '/consultant/update-education');
				$('#btnSubmitEducation').html(updateLabel).val('updateEducation');
				$("#educationFormModal").modal({
					'show': true,
					'backdrop': 'static'
				});
			});
		});

		$('.delete-education').click(function () {
			$("#deleteEducationMessageModal").modal({
				'show': true,
				'backdrop': 'static'
			});
			$('#btnDeleteEducation').val($(this).closest('div.row').attr('id'));
		});

		$('#btnDeleteEducation').click(function () {
			window.location = contextRoot + '/consultant/delete-education?' + $.param({'educationId': $(this).val()});
		});
		/*]]>*/
	}

	return {
		config : config,
		processProfileForm : processProfileForm
	};

}());