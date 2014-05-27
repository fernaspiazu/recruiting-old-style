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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.Locale;

@Service
public class ValidationResponseHandlerImpl implements ValidationResponseHandler {

  @Autowired
  private ErrorMessageResolver resolver;

  @Override
  public ValidationResponse validationSuccess() {
    ValidationResponse validationResponse = new ValidationResponse();
    validationResponse.setStatus(ValidationStatus.SUCCESSFUL);
    return validationResponse;
  }

  @Override
  public ValidationResponse validationFail(Errors errors, Locale locale) {
    ValidationResponse validationResponse = new ValidationResponse();
    validationResponse.setStatus(ValidationStatus.FAILED);
    validationResponse.setErrorMessages(resolver.resolveErrorCodes(errors.getFieldErrors(), locale));
    return validationResponse;
  }

}
