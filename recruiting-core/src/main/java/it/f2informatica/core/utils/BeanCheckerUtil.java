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
package it.f2informatica.core.utils;

import java.lang.reflect.Field;

public class BeanCheckerUtil {

	public static boolean areFieldsEmpty(Object obj) {
		try {
			return checkIfFieldIsNullOrEmpty(obj);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Illegal Access Exception.", e);
		}
	}

	private static boolean checkIfFieldIsNullOrEmpty(Object obj) throws IllegalAccessException {
		if (obj == null) {
			return true;
		}

		for (Field field : obj.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			Object value = field.get(obj);
			if (value == null || (value instanceof String && ((String) value).isEmpty())) {
				continue;
			}
			return true;
		}

		return false;
	}

}
