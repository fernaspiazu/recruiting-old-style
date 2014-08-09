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

var $languageForm = (function languagesForm() {

	var languages = [];
	var selectLanguage = '';
	var english = '';
	var italian = '';
	var spanish = '';
	var german = '';
	var dutch = '';
	var selectproficiency = '';
	var elementary = '';
	var limited_working = '';
	var professional_working = '';
	var full_professional = '';
	var native_or_bilingual = '';
	var lastIndex = 0;
	var languageLbl = '';
	var proficiencyLbl = '';

	function manageForm(p) {
		setFields(p);

		$("#languageFormModal").on("show.bs.modal", function (e) {
			for (var i = 0; i < languages.length; i++) {
				$(createSingleRowEntry(languages[i], i)).insertBefore($('#linkAddNewLanguage'));
			}
			lastIndex++;
			$(createSingleRowEntry(null, lastIndex)).insertBefore($('#linkAddNewLanguage'));
		});

		$("#languageFormModal").on("hidden.bs.modal", function (e) {
			$("#languagesForm").find(".row").remove();
		});

		$('#linkAddNewLanguage').click(function () {
			$(createSingleRowEntry(null, lastIndex++)).insertBefore($('#linkAddNewLanguage'));
		});
	}

	function setFields(p) {
		languages = p.languages;
		selectLanguage = p.selectLanguage;
		english = p.english;
		italian = p.italian;
		spanish = p.spanish;
		german = p.german;
		dutch = p.dutch;
		selectproficiency = p.selectproficiency;
		elementary = p.elementary;
		limited_working = p.limited_working;
		professional_working = p.professional_working;
		full_professional = p.full_professional;
		native_or_bilingual = p.native_or_bilingual;
		lastIndex = languages.length - 1;
		languageLbl = p.language;
		proficiencyLbl = p.proficiency;
	}

	function createSingleRowEntry(languageModel, index) {
		/*<![CDATA[*/
		var englishSelected = (languageModel != null && languageModel.language === 'english') ? ' selected="selected"' : '';
		var italianSelected = (languageModel != null && languageModel.language === 'italian') ? ' selected="selected"' : '';
		var spanishSelected = (languageModel != null && languageModel.language === 'spanish') ? ' selected="selected"' : '';
		var germanSelected = (languageModel != null && languageModel.language === 'german') ? ' selected="selected"' : '';
		var dutchSelected = (languageModel != null && languageModel.language === 'dutch') ? ' selected="selected"' : '';

		var elementarySelected = (languageModel != null && languageModel.proficiency === 'elementary') ? ' selected="selected"' : '';
		var limitedWorkinSelected = (languageModel != null && languageModel.proficiency === 'limited_working') ? ' selected="selected"' : '';
		var professionalWorkingSelected = (languageModel != null && languageModel.proficiency === 'professional_working') ? ' selected="selected"' : '';
		var fullProfessionalSelected = (languageModel != null && languageModel.proficiency === 'full_professional') ? ' selected="selected"' : '';
		var nativeOrBilingualSelected = (languageModel != null && languageModel.proficiency === 'native_or_bilingual') ? ' selected="selected"' : '';

		return $('<div class="row">').append(
				'<div class="form-group col-md-5"><label class="control-label">'+ languageLbl +'</label>' +
				'<select name="languages[' + index + '].language" class="form-control">' +
				'<option value="">' + selectLanguage + '</option>' +
				'<option value="english"' + englishSelected + '>' + english + '</option>' +
				'<option value="italian"' + italianSelected + '>' + italian + '</option>' +
				'<option value="spanish"' + spanishSelected + '>' + spanish + '</option>' +
				'<option value="german"' + germanSelected + '>' + german + '</option>' +
				'<option value="dutch"' + dutchSelected + '>' + dutch + '</option>' +
				'</select>' +
				'</div>' +
				'<div class="form-group col-md-5"><label class="control-label">'+ proficiencyLbl +'</label>' +
				'<select name="languages[' + index + '].proficiency" class="form-control">' +
				'<option value="">' + selectproficiency + '</option>' +
				'<option value="elementary"' + elementarySelected + '>' + elementary + '</option>' +
				'<option value="limited_working"' + limitedWorkinSelected + '>' + limited_working + '</option>' +
				'<option value="professional_working"' + professionalWorkingSelected + '>' + professional_working + '</option>' +
				'<option value="full_professional"' + fullProfessionalSelected + '>' + full_professional + '</option>' +
				'<option value="native_or_bilingual"' + nativeOrBilingualSelected + '>' + native_or_bilingual + '</option>' +
				'</select>' +
				'</div>' +
				'<div class="form-group col-md-1">' +
				'<button type="button" class="close" onclick="$languageForm.dropLanguage(this);">&times;</button>' +
				'</div>'
		);
		/*]]>*/
	}

	function dropLanguage(button) {
		lastIndex--;
		$(button).closest('.row').remove();
		$('#languagesForm').find('select[name$="].language"]').each(function (i, item) {
			$(item).attr('name', 'languages[' + i + '].language');
		});
		$('#languagesForm').find('select[name$="].proficiency"]').each(function (i, item) {
			$(item).attr('name', 'languages[' + i + '].proficiency');
		});
	}

	return {
		manageForm : manageForm,
		dropLanguage : dropLanguage
	};

}());