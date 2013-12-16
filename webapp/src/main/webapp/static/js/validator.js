/**
 * @author Fernando Aspiazu
 */

var errors = [];

$(document).ready(function() {
	validateUserCreation();
	validateUserEdit();
	validateChangePassword();
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

function validateUserEdit() {
	$("#editUserForm").submit(function() {
		errors = [];
		validateRequiredFields(this);
		if (isEmpty(errors)) {
			this.submit();
		}
		return false;
	});
}

function validateChangePassword() {
	$("#changePasswordForm").submit(function() {
		errors = [];
		validateRequiredFields(this);
		if (isEmpty(errors)) {
			validateCurrentPassword(this);
			validateNewPassword(this);
		}
		if (isEmpty(errors)) {
			this.submit();
		}
		return false;
	});
}

function validateCurrentPassword(form) {
	var url = "/user/verifyCurrentPassword";
	var parameters = {
		"userId" : $(form).find("#userId").val(),
		"currentPwd" : $(form).find("#currentPassword").val()
	};
	var method = "POST";
	var successCallback = function(isCurrentPasswordValid) {
		if (!eval(isCurrentPasswordValid)) {
			attachFieldErrorMessage($(form).find("#currentPassword"), "user.err.currentpwd");
		}
	};
	ajaxRequest(url, parameters, method, successCallback);
}

function validateNewPassword(form) {
	var newPassword = $(form).find("#newPassword").val();
	var passwordConfirmed = $(form).find("#passwordConfirmed").val();
	if (newPassword != passwordConfirmed) {
		attachFieldErrorMessage($(form).find("#passwordConfirmed"), "user.err.confirmedpwd");
		errors.push("error on -> " + $($(form).find("#passwordConfirmed")).attr("id"));
	}
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
		attachFieldErrorMessage(input, "global.err.field.empty");
	} else {
		removeErrorMessageIfExists(input);
	}
}

function validateRequiredSelectField(select) {
	if ($(select).val() === "0") {
		attachFieldErrorMessage(select, "global.err.field.empty");
	} else {
		removeErrorMessageIfExists(select);
	}
}

function attachFieldErrorMessage(element, code) {
	removeErrorMessageIfExists(element);
	var url = "/js/resolvei18nCode";
	var parameters = {"code" : code};
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