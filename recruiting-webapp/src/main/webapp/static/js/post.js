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
var firedSubmitEvent = null;

$(document).ready(function() {
	var $form = $(targetForm);

	$("form :input[type=submit]").click(function() {
		$(":input[type=submit]", $(this).parents("form")).removeAttr("clicked");
		$(this).attr("clicked", "true");
	});

	$form.bind('submit', function(event) {
		var $inputs = $form.find(':input');
		var data = collectFormData($inputs);
		var url = evaluateContextRoot() + asyncUrl;

		firedSubmitEvent = $(":input[type='submit'][clicked=true]").attr("value");
		data["submitEvent"] = firedSubmitEvent;

		$.post(url, data, function(response) {
			if (response.status == "FAIL") {
				resetAllErrorMessages();
				var $inputToFocus = null;
				$.each(response.errorMessages, function(i, item) {
					var errorMessage = item.errorMessage;
					var $field = $('#'+item.fieldName);
					var $fieldErrors = $('#fieldErrors').html('<div class="alert alert-danger"></div>');

					$field.parent().addClass('has-error');
					if (!$fieldErrors.find('.alert-danger').html()) {
						$fieldErrors.find('.alert-danger').html(errorMessage);
					} else {
						$fieldErrors.find('.alert-danger').append(errorMessage);
					}

					if ($inputToFocus === null && $field.attr('autofocus') !== undefined) {
						$inputToFocus = $field;
					}
				});

				$inputToFocus.focus();
			} else {
				$form.unbind('submit');
				$form.append('<input type="hidden">').attr({
					"name" : "submitEvent",
					"value" : firedSubmitEvent
				});
				$form.submit();
			}
		}, "json");

		event.preventDefault();
		return false;
	});
});

function collectFormData(fields) {
	var data = {};
	for (var i = 0; i < fields.length; i++) {
		var $item = $(fields[i]);
		if ($item.attr('type') == 'file') {
			continue;
		}
		if ($item.attr('type') == 'checkbox') {
			data[$item.attr('name')] = $item.is(':checked');
		} else {
			data[$item.attr('name')] = $item.val();
		}
	}
	return data;
}

function resetAllErrorMessages() {
	var $form = $(targetForm);
	var fields = $form.find(':input');
	for (var i = 0; i < fields.length; i++) {
		var $item = $(fields[i]);
		$item.parent().removeClass('has-error');
	}
	$('#fieldErrors').children().remove();
}