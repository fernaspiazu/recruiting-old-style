package it.f2informatica.services.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public abstract class AbstractValidator implements Validator {

	protected void invokeValidator(Validator validator, Object target, String nestedPath, Errors errors) {
		try {
			errors.pushNestedPath(nestedPath);
			ValidationUtils.invokeValidator(validator, target, errors);
		} finally {
			errors.popNestedPath();
		}
	}

}
