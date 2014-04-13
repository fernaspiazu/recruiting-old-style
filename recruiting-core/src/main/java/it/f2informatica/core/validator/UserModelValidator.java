package it.f2informatica.core.validator;

import it.f2informatica.core.model.UserModel;
import it.f2informatica.core.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserModelValidator extends AbstractValidator {
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
		"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,4})$";

	private static final String SAVE_EVENT = "save";
	private static final String UPDATE_EVENT = "update";

	@Autowired
	private RoleModelValidator roleModelValidator;

	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return UserModel.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserModel userModel = (UserModel) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", FIELD_MANDATORY);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", FIELD_MANDATORY);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", FIELD_MANDATORY);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", FIELD_MANDATORY);
		if (isSaveEvent(userModel)) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", FIELD_MANDATORY);
		}
		invokeValidator(roleModelValidator, userModel.getRole(), "role", errors);
		doFurtherValidation(userModel, errors);
	}

	private void doFurtherValidation(UserModel userModel, Errors errors) {
		if (errors.hasErrors()) {
			return;
		}
		if (isEmailInvalid(userModel.getEmail())) {
			errors.rejectValue("email", "err.email");
		}

		final String username = userModel.getUsername();
		final UserModel user = getUser(username);
		if (isSaveEvent(userModel) && existsUser(user)) {
			errors.rejectValue("username", "err.username.exists");
		} else if (isUpdateEvent(userModel) && existsUser(user) && !username.equals(user.getUsername())) {
			errors.rejectValue("username", "err.username.exists");
		}
	}

	private boolean isEmailInvalid(String email) {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return !matcher.matches();
	}

	private boolean isSaveEvent(UserModel userModel) {
		return SAVE_EVENT.equals(userModel.getSubmitEvent());
	}

	private boolean isUpdateEvent(UserModel userModel) {
		return UPDATE_EVENT.equals(userModel.getSubmitEvent());
	}

	private boolean existsUser(UserModel userFromDB) {
		return userFromDB != null;
	}

	private UserModel getUser(String username) {
		return userService.findByUsername(username);
	}

}
