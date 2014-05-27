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

import it.f2informatica.core.model.ExperienceModel;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

@Component
public class ConsultantExperienceValidator extends AbstractValidator {

  @Override
  public boolean supports(Class<?> clazz) {
    return ExperienceModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyName", FIELD_MANDATORY);
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "position", FIELD_MANDATORY);
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "locality", FIELD_MANDATORY);
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "monthFrom", FIELD_MANDATORY);
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "yearFrom", FIELD_MANDATORY);
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", FIELD_MANDATORY);
    doFurtherValidations(target, errors);
  }

  private void doFurtherValidations(Object target, Errors errors) {
    if (errors.hasErrors()) {
      return;
    }

    ExperienceModel experience = (ExperienceModel) target;
    if (!experience.isCurrent()) {
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "monthTo", FIELD_MANDATORY);
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "yearTo", FIELD_MANDATORY);
      checkPeriodCorrectness(experience, errors);
    }
  }

  private void checkPeriodCorrectness(ExperienceModel experience, Errors errors) {
    DateTime periodFrom = new DateTime().withMonthOfYear(getMonth(experience.getMonthFrom())).withYear(getYear(experience.getYearFrom()));
    DateTime periodTo = new DateTime().withMonthOfYear(getMonth(experience.getMonthTo())).withYear(getYear(experience.getYearTo()));
    if (periodTo.isBefore(periodFrom)) {
      errors.rejectValue("monthTo", "experience.err.invalidperiods");
      errors.rejectValue("yearTo", "experience.err.invalidperiods");
    }
  }

  private int getMonth(String month) {
    return Integer.parseInt(month) + 1;
  }

  private int getYear(String year) {
    return Integer.parseInt(year);
  }

}
