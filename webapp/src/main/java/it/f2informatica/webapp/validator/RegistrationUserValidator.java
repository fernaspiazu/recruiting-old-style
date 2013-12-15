package it.f2informatica.webapp.validator;

import it.f2informatica.services.model.UserModel;
import it.f2informatica.webapp.controller.helper.CurrentHttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;

@Component
public class RegistrationUserValidator extends GlobalValidator {

	@Autowired
	private CurrentHttpServletRequest currentHttpServletRequest;

	@Override
	public boolean supports(Class<?> clazz) {
		return UserModel.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserModel userModel = (UserModel) target;
		checkEmptyString("username", userModel.getUsername(), errors);
		if (mustPasswordBeChecked()) {
			checkEmptyString("password", userModel.getPassword(), errors);
		}
		checkSelectValue("role", userModel.getRole().getRoleId(), errors);
	}

	private boolean mustPasswordBeChecked() {
		HttpServletRequest request = currentHttpServletRequest.currentHttpServletRequest();
		return request.getRequestURI().contains("/save");
	}

}
