package it.f2informatica.webapp.controller;

import it.f2informatica.services.domain.consultant.ConsultantService;
import it.f2informatica.webapp.controller.helper.MonthHelper;
import it.f2informatica.webapp.controller.helper.PeriodResolver;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractConsultantController {
	protected static final String CONSULTANT_ID_SESSION_ATTR = "consultantId";

	@Autowired
	protected MonthHelper monthHelper;

	@Autowired
	protected PeriodResolver periodResolver;

	@Autowired
	protected ConsultantService consultantService;

}
