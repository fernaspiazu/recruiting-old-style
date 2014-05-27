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

import it.f2informatica.core.model.EducationModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

@Component
public class ConsultantEducationValidator extends AbstractValidator {

  @Override
  public boolean supports(Class<?> clazz) {
    return EducationModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "school", FIELD_MANDATORY);
    doFurtherValidations(target, errors);
  }

  private void doFurtherValidations(Object target, Errors errors) {
    if (errors.hasErrors()) {
      return;
    }

    EducationModel education = (EducationModel) target;
    if (!education.isCurrent()) {
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endYear", FIELD_MANDATORY);
      if (!errors.hasErrors()) {
        checkPeriodCorrectness(education, errors);
      }
    }
  }

  private void checkPeriodCorrectness(EducationModel education, Errors errors) {
    if (education.getStartYear().compareTo(education.getEndYear()) > 0) {
      errors.rejectValue("endYear", "education.err.invalidperiods");
    }
  }

}
