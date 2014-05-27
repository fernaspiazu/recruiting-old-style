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

import com.google.common.base.Optional;
import it.f2informatica.core.model.UserModel;
import it.f2informatica.core.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserModelValidator extends AbstractValidator {
  private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,4})$";

  private static final String SAVE_EVENT = "save";
  private static final String UPDATE_EVENT = "update";

  @Autowired
  private RoleModelValidator roleModelValidator;

  @Autowired
  private UserService userService;

  @Override
  public boolean supports(Class<?> clazz) {
    return UserModel.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    UserModel userModel = (UserModel) target;
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", FIELD_MANDATORY);
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", FIELD_MANDATORY);
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", FIELD_MANDATORY);
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", FIELD_MANDATORY);
    if (isSaveEvent(userModel)) {
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", FIELD_MANDATORY);
    }
    invokeValidator(roleModelValidator, userModel.getRole(), "role", errors);
    doFurtherValidation(userModel, errors);
  }

  private void doFurtherValidation(UserModel userModel, Errors errors) {
    if (errors.hasErrors()) {
      return;
    }
    if (isEmailInvalid(userModel.getEmail())) {
      errors.rejectValue("email", "err.email");
    }

    final String username = userModel.getUsername();
    Optional<UserModel> user = userService.findByUsername(username);
    if (user.isPresent()) {
      if (isSaveEvent(userModel)) {
        errors.rejectValue("username", "err.username.exists");
      } else if (isUpdateEvent(userModel) && !username.equals(user.get().getUsername())) {
        errors.rejectValue("username", "err.username.exists");
      }
    }
  }

  private boolean isEmailInvalid(String email) {
    Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    Matcher matcher = pattern.matcher(email);
    return !matcher.matches();
  }

  private boolean isSaveEvent(UserModel userModel) {
    return SAVE_EVENT.equals(userModel.getSubmitEvent());
  }

  private boolean isUpdateEvent(UserModel userModel) {
    return UPDATE_EVENT.equals(userModel.getSubmitEvent());
  }

}
