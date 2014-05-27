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
