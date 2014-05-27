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

import it.f2informatica.core.model.UpdatePasswordModel;
import it.f2informatica.core.services.PasswordUpdaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

@Component
public class UpdatePasswordModelValidator extends AbstractValidator {

  @Autowired
  private PasswordUpdaterService passwordUpdaterService;

  @Override
  public boolean supports(Class<?> clazz) {
    return UpdatePasswordModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    UpdatePasswordModel updatePasswordModel = (UpdatePasswordModel) target;
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userId", FIELD_MANDATORY);
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentPassword", FIELD_MANDATORY);
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", FIELD_MANDATORY);
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirmed", FIELD_MANDATORY);
    doFurtherValidation(updatePasswordModel, errors);

  }

  private void doFurtherValidation(UpdatePasswordModel updatePasswordModel, Errors errors) {
    if (errors.hasErrors()) {
      return;
    }
    if (isPasswordNotValid(updatePasswordModel)) {
      errors.rejectValue("currentPassword", "user.err.currentpwd");
    }
    if (isPasswordConfirmationNotValid(updatePasswordModel)) {
      errors.rejectValue("passwordConfirmed", "user.err.confirmedpwd");
    }
  }

  private boolean isPasswordNotValid(UpdatePasswordModel updatePasswordModel) {
    return !passwordUpdaterService.isCurrentPasswordValid(updatePasswordModel.getUserId(), updatePasswordModel.getCurrentPassword());
  }

  private boolean isPasswordConfirmationNotValid(UpdatePasswordModel updatePasswordModel) {
    return !updatePasswordModel.getNewPassword().equals(updatePasswordModel.getPasswordConfirmed());
  }

}
