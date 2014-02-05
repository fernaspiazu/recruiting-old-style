/**
 * @author Fernando Aspiazu
 */

var errors = [];

$(document).ready(function() {
	validateUserCreation();
	validateUserEdit();
	validateChangePassword();
	validateExperience();
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

function validateExperience() {
	$("#experienceForm").submit(function() {
		errors = [];
		validateRequiredFields(this);
		if (isEmpty(errors)) {
			validatePeriods(this);
		}
		if (isEmpty(errors)) {
			this.submit();
		}
		return false;
	});
}

function validatePeriods(form) {
	var monthPeriodFrom = $(form).find("#monthPeriodFrom").val();
	var yearPeriodFrom = $(form).find("#yearPeriodFrom").val();
	var monthPeriodTo = $(form).find("#monthPeriodTo").val();
	var yearPeriodTo = $(form).find("#yearPeriodTo").val();
	var isCurrentJob = $(form).find("#thisIsCurrentJob").is(":checked");

	var currentDate = new Date();
	var periodFrom = new Date(yearPeriodFrom, monthPeriodFrom, 1, 0, 0, 0, 0);
	if (!eval(isCurrentJob)) {
		var periodTo = new Date(yearPeriodTo, monthPeriodTo, 1, 0, 0, 0, 0);
		if (!(currentDate > periodFrom && periodFrom < periodTo)) {
			attachFieldErrorMessage($(form).find("#yearPeriodTo"), "experience.err.invalidperiods");
		}
	} else if (currentDate < periodFrom) {
		attachFieldErrorMessage($(form).find("#experienceToday"), "experience.err.invalidperiods");
	}
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
	if ($(select).val() === "-1") {
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
	if ($(element).next().length && $(element).next().attr("class") === "field-error") {
		$(element).next().remove();
	}
}

function buildMessageError(errorMsg) {
	return '<span class="field-error">' + errorMsg + '</span>';
}

function isEmpty(value) {
	return isNull(value) || value.length === 0 || value === "";
}

function isNull(value) {
	return value === undefined || value === null;
}