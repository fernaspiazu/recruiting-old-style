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

import it.f2informatica.core.model.ConsultantModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

@Component
public class ConsultantPersonalDetailsValidator extends AbstractValidator {

  @Override
  public boolean supports(Class<?> clazz) {
    return ConsultantModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "consultantNo", FIELD_MANDATORY);
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fiscalCode", FIELD_MANDATORY);
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", FIELD_MANDATORY);
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", FIELD_MANDATORY);
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", FIELD_MANDATORY);
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", FIELD_MANDATORY);
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "birthDate", FIELD_MANDATORY);
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "birthCity", FIELD_MANDATORY);
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "birthCountry", FIELD_MANDATORY);
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "birthDate", FIELD_MANDATORY);
  }

}
