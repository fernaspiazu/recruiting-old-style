package it.f2informatica.core.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public abstract class AbstractValidator implements Validator {
	protected static final String FIELD_MANDATORY = "err.required";

	protected void invokeValidator(Validator validator, Object target, String nestedPath, Errors errors) {
		try {
			errors.pushNestedPath(nestedPath);
			ValidationUtils.invokeValidator(validator, target, errors);
		} finally {
			errors.popNestedPath();
		}
	}

}
