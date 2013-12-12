package it.f2informatica.webapp.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public abstract class GlobalValidator implements Validator {
	protected static final String ZERO = "0";
	protected static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

	protected static final String DEFAULT_ERROR_CODE = "global.err.default";
	protected static final String EMPTY_FIELD_ERROR_CODE = "global.err.field.empty";
	protected static final String DATE_FORMAT_ERROR_CODE = "global.err.field.date.format";

	protected boolean isNull(Object target) {
		return target == null;
	}

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

	protected void checkSelectValue(String field, String target, Errors errors) {
		checkEmptyString(field, target, errors);
		checkSelectValue(field, target, EMPTY_OBJECT_ARRAY, errors);
	}

	protected void checkSelectValue(String field, String target, Object[] errorArgs, Errors errors) {
		if (ZERO.equals(target)) {
			showErrorMessage(field, EMPTY_FIELD_ERROR_CODE, errorArgs, errors);
		}
	}

	protected void showErrorMessage(String field, String errorCode, Errors errors) {
		showErrorMessage(field, errorCode, EMPTY_OBJECT_ARRAY, errors);
	}

	protected void showErrorMessage(String field, String errorCode, Object[] errorArgs, Errors errors) {
		errors.rejectValue(field, errorCode, errorArgs, DEFAULT_ERROR_CODE);
	}

}
