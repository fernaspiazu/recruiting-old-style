package it.f2informatica.core.validator.utils;

import org.springframework.validation.Errors;

import java.util.Locale;

public interface ValidationResponseService {

	ValidationResponse validationSuccess();

	ValidationResponse validationFail(Errors errors, Locale locale);

}
