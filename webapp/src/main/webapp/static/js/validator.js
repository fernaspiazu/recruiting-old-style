/**
 * @author Fernando Aspiazu
 */

var errors = [];

$(document).ready(function() {
	validateUserCreation();
});

function validateUserCreation() {
	$("#createUserForm").submit(function() {
		errors = []; // clear always errors before perform validation
		validateRequiredFields(this);
		if (isEmpty(errors)) {
			this.submit();
		}
		return false;
	});
}

function validateRequiredFields(form) {
	$(form).find(".required").each(function() {
		if ($(this).is("input")) {
			validateRequiredInputField(this);
		}
		if ($(this).is("select")) {
			validateRequiredSelectField(this);
		}
	});
}

function validateRequiredInputField(input) {
	if (isEmpty($(input).val())) {
		attachRequiredFieldErrorMessage(input);
	} else {
		removeErrorMessageIfExists(input);
	}
}

function validateRequiredSelectField(select) {
	if ($(select).val() === "0") {
		attachRequiredFieldErrorMessage(select);
	} else {
		removeErrorMessageIfExists(select);
	}
}

function attachRequiredFieldErrorMessage(element) {
	removeErrorMessageIfExists(element);
	var url = "/js/resolvei18nCode";
	var parameters = {"code" : "global.err.field.empty"};
	var method = "POST";
	var successCallback = function(response) {
		$(element).after(buildMessageError(response));
	};
	ajaxRequest(url, parameters, method, successCallback);
	errors.push("error on -> " + $(element).attr("id"));
}

function removeErrorMessageIfExists(element) {
	if ($(element).next().length) {
		$(element).next().remove();
	}
}

function buildMessageError(errorMsg) {
	return '<span class="field-error">' + errorMsg + '</span>';
}

function isNotEmpty(value) {
	return !isEmpty(value);
}

function isEmpty(value) {
	return isNull(value) || value.length === 0 || value === "";
}

function isNotNull(value) {
	return !isNull(value);
}

function isNull(value) {
	return value === undefined || value === null;
}