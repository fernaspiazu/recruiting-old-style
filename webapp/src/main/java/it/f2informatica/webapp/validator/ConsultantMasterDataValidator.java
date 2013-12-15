package it.f2informatica.webapp.validator;

import it.f2informatica.services.model.ConsultantModel;
import it.f2informatica.webapp.controller.helper.CurrentHttpServletRequest;
import it.f2informatica.webapp.controller.helper.ShortDateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import java.text.ParseException;

@Component
public class ConsultantMasterDataValidator extends GlobalValidator {

	@Autowired
	private ShortDateFormatter shortDateFormatter;

	@Autowired
	private CurrentHttpServletRequest currentHttpServletRequest;

	@Override
	public boolean supports(Class<?> clazz) {
		return ConsultantModel.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ConsultantModel consultantModel = (ConsultantModel) target;
		checkEmptyValues(consultantModel, errors);
		checkBirthDate(errors);
	}

	private void checkEmptyValues(ConsultantModel consultantModel, Errors errors) {
		checkEmptyString("firstName", consultantModel.getFirstName(), errors);
		checkEmptyString("lastName", consultantModel.getLastName(), errors);
		checkNotNull("gender", consultantModel.getGender(), errors);
		checkEmptyString("email", consultantModel.getEmail(), errors);
		checkEmptyString("fiscalCode", consultantModel.getFiscalCode(), errors);
	}

	private void checkBirthDate(Errors errors) {
		String birthDateParam = currentHttpServletRequest.currentHttpServletRequest().getParameter("birthDate");
		if (StringUtils.hasText(birthDateParam)) {
			try {
				shortDateFormatter.parse(birthDateParam);
			} catch (ParseException e) {
				showErrorMessage("birthDate", DATE_FORMAT_ERROR_CODE, errors);
			}
		}
	}
}
