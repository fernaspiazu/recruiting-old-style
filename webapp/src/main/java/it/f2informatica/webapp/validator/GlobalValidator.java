package it.f2informatica.webapp.validator;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public abstract class GlobalValidator implements Validator {

	protected static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

	protected static final String DEFAULT_ERROR_CODE = "global.err.default";
	protected static final String EMPTY_FIELD_ERROR_CODE = "global.err.field.empty";
	protected static final String NUMBER_FORMAT_ERROR_CODE = "global.err.field.number.format";

	protected void checkNotNull(String field, Object target, Errors errors) {
		checkNotNull(field, target, EMPTY_OBJECT_ARRAY, errors);
	}

	protected void checkNotNull(String field, Object target, Object[] errorArgs, Errors errors) {
		if (target == null) {
			showErrorMessage(field, EMPTY_FIELD_ERROR_CODE, errorArgs, errors);
		}
	}

	protected void checkEmptyString(String field, String target, Errors errors) {
		checkEmptyString(field, target, EMPTY_OBJECT_ARRAY, errors);
	}

	protected void checkEmptyString(String field, String target, Object[] errorArgs, Errors errors) {
		if (StringUtils.isBlank(target)) {
			showErrorMessage(field, EMPTY_FIELD_ERROR_CODE, errorArgs, errors);
		}
	}

	protected void checkStringNumberFormat(String field, String target, Errors errors) {
		checkStringNumberFormat(field, target, EMPTY_OBJECT_ARRAY, errors);
	}

	protected void checkStringNumberFormat(String field, String target, Object[] errorArgs, Errors errors) {
		if (!NumberUtils.isNumber(target)) {
			showErrorMessage(field, NUMBER_FORMAT_ERROR_CODE, errorArgs, errors);
		}
	}

	protected void showErrorMessage(String field, String errorCode, Object[] errorArgs, Errors errors) {
		errors.rejectValue(field, errorCode, errorArgs, DEFAULT_ERROR_CODE);
	}

}
