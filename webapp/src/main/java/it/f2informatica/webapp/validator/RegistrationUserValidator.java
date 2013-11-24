package it.f2informatica.webapp.validator;

import it.f2informatica.services.model.UserModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class RegistrationUserValidator extends GlobalValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UserModel.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserModel userModel = (UserModel) target;
		checkEmptyString("username", userModel.getUsername(), errors);
		checkEmptyString("password", userModel.getPassword(), errors);
		checkNotNull("role", userModel.getRole(), errors);
	}

}
