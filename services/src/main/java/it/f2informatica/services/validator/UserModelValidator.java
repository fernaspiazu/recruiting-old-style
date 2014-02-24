package it.f2informatica.services.validator;

import it.f2informatica.services.model.UserModel;
import it.f2informatica.services.user.UserService;
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
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "err.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "err.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "err.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "err.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "err.required");
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
		if (existsUser(userModel.getUsername())) {
			errors.rejectValue("username", "err.username.exists");
		}
	}

	private boolean isEmailInvalid(String email) {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return !matcher.matches();
	}

	private boolean existsUser(String username) {
		return userService.findByUsername(username) != null;
	}

}
