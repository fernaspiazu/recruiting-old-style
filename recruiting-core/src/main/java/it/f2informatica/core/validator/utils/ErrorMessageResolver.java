/*
 * =============================================================================
 *
 *   Copyright (c) 2014, Fernando Aspiazu
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */
package it.f2informatica.core.validator.utils;

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
