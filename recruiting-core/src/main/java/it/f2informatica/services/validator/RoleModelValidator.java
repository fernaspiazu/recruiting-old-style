package it.f2informatica.services.validator;

import it.f2informatica.services.model.RoleModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RoleModelValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return RoleModel.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roleId", "err.required");
	}

}
