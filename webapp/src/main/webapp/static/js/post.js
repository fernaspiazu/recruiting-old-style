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
		var url = contextRoot + asyncUrl;

		firedSubmitEvent = $(":input[type='submit'][clicked=true]").attr("value");
		data["submitEvent"] = firedSubmitEvent;

		$.post(url, data, function(response) {
			if (response.status == "FAIL") {
				resetAllErrorMessages();
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
				});
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
		data[$item.attr('name')] = $item.val();
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