/**
 * Unused
 */

var selectorToValidate = "*[required], .required";

function validateRequiredInputFields() {
	$(selectorToValidate).blur(function() {
		checkInputMandatoryField($(this));
	});
}

function validateRequiredFieldsOnFormSubmit() {
	var success = true;
	$("input[type='submit']").click(function() {
		var form = $(this).parentsUntil("form").first().parent();
		$(form).find(selectorToValidate).each(function() {
			success = checkInputMandatoryField($(this));
		});
	});
	return success;
}

function checkInputMandatoryField(element) {
	if (isEmpty(element.val())) {
		addErrorMessageIfNecessary(element);
		return false;
	} else {
		removeErrorMessageIfNecessary(element);
	}
	return true;
}

function addErrorMessageIfNecessary(element) {
	if (!element.next().hasClass("error-block")) {
		resolvei18nMessage(element, "global.mandatory");
	}
}

function resolvei18nMessage(element, code) {
	$.ajax({
		"url" : contextRoot + "/js/resolvei18nCode",
		"type" : "POST",
		"data" : {"code" : code},
		"success" : function(response) {
			element.after(createErrorMessage(response));
		},
		"statusCode" : {
			"500" : function() {
				element.after(createErrorMessage("Server Error"));
			}
		}
	});
}

function createErrorMessage(msg) {
	return $("<div>", {
		"html" : msg,
		"class" : "error-block"
	});
}

function removeErrorMessageIfNecessary(element) {
	if (element.next().hasClass("error-block")) {
		element.next().remove();
	}
}

function isEmpty(value) {
	return isNull(value) || value === "";
}

function isNull(value) {
	return value === undefined || value === null;
}