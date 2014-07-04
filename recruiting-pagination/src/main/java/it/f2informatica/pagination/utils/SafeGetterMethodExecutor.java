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
package it.f2informatica.pagination.utils;

import com.google.common.base.Optional;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Component
public class SafeGetterMethodExecutor {

	public <T> Object invokeGetterOnField(String fieldName, T entity) {
		try {
			// -------------------------------------
			return _invokeGetter(fieldName, entity);
			// -------------------------------------
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	private <T> Object _invokeGetter(String fieldName, T entity) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Optional<Object> result = Optional.absent();
		for (String field : fieldName.split("_")) {
			if (fieldName.endsWith(".") || 0 == field.length()) {
				throw new IllegalArgumentException("Invalid property name '" + fieldName + "'");
			}

			Method getter = entity.getClass().getMethod("get" + StringUtils.capitalize(field));
			result = Optional.fromNullable(getter.invoke(entity));

			if (!result.isPresent()) {
				break;
			} else if (!field.equals(fieldName)) {
				// If the current field is not equals to the nested
				// field properties, then it will call itself until
				// reach the last property (via Recursive Call).
				// On the other hand, fieldName.substring(field.length() + 1)
				// means that I'm passing the entire nested field path
				// to the next recursive call, except the current property
				// evaluated considering also the 'dot'.
				// For example, if we have "someProperty." (will be excluded)
				return _invokeGetter(fieldName.substring(field.length() + 1), result.get());
			}
		}
		return result.orNull(); // Holds the final result from the last getter method
	}

}
