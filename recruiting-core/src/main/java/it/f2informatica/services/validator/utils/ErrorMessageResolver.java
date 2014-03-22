package it.f2informatica.services.validator.utils;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Component
public class ErrorMessageResolver {

	@Autowired
	private MessageSource messageSource;

	public List<ErrorMessage> resolveErrorCodes(List<FieldError> fieldErrors, final Locale locale) {
		return Lists.newArrayList(Iterables.transform(fieldErrors,
			new Function<FieldError, ErrorMessage>() {
				@Override
				public ErrorMessage apply(FieldError input) {
					String errorCode = input.getCode();
					String errorMessage = messageSource.getMessage(errorCode, input.getArguments(), locale);
					String field = Iterables.getFirst(Arrays.asList(input.getField().split("\\.")), "");
					return new ErrorMessage(field, errorCode, errorMessage);
				}
			}
		));
	}

}
