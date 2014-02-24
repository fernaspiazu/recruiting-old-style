package it.f2informatica.services.validator.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.Locale;

@Service
public class ValidationResponseServiceImpl implements ValidationResponseService {

	@Autowired
	private ErrorMessageResolver resolver;

	@Override
	public ValidationResponse validationSuccess() {
		ValidationResponse validationResponse = new ValidationResponse();
		validationResponse.setStatus(ValidationStatus.SUCCESS.toString());
		return validationResponse;
	}

	@Override
	public ValidationResponse validationFail(Errors errors, Locale locale) {
		ValidationResponse validationResponse = new ValidationResponse();
		validationResponse.setStatus(ValidationStatus.FAIL.toString());
		validationResponse.setErrorMessages(resolver.resolveErrorCodes(errors.getFieldErrors(), locale));
		return validationResponse;
	}

}
