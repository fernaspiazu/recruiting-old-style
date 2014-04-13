package it.f2informatica.core.validator;

import it.f2informatica.core.model.UpdatePasswordModel;
import it.f2informatica.core.services.PasswordUpdaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

@Component
public class UpdatePasswordModelValidator extends AbstractValidator {

	@Autowired
	private PasswordUpdaterService passwordUpdaterService;

	@Override
	public boolean supports(Class<?> clazz) {
		return UpdatePasswordModel.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UpdatePasswordModel updatePasswordModel = (UpdatePasswordModel) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userId", FIELD_MANDATORY);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentPassword", FIELD_MANDATORY);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", FIELD_MANDATORY);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirmed", FIELD_MANDATORY);
		doFurtherValidation(updatePasswordModel, errors);

	}

	private void doFurtherValidation(UpdatePasswordModel updatePasswordModel, Errors errors) {
		if (errors.hasErrors()) {
			return;
		}
		if (isPasswordNotValid(updatePasswordModel)) {
			errors.rejectValue("currentPassword", "user.err.currentpwd");
		}
		if (isPasswordConfirmationNotValid(updatePasswordModel)) {
			errors.rejectValue("passwordConfirmed", "user.err.confirmedpwd");
		}
	}

	private boolean isPasswordNotValid(UpdatePasswordModel updatePasswordModel) {
		return !passwordUpdaterService.isCurrentPasswordValid(updatePasswordModel.getUserId(), updatePasswordModel.getCurrentPassword());
	}

	private boolean isPasswordConfirmationNotValid(UpdatePasswordModel updatePasswordModel) {
		return !updatePasswordModel.getNewPassword().equals(updatePasswordModel.getPasswordConfirmed());
	}

}
