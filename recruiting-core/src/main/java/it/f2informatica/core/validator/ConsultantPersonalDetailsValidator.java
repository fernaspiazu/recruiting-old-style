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
