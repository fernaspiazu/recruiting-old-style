package it.f2informatica.webapp.controller;

import it.f2informatica.services.domain.consultant.ConsultantService;
import it.f2informatica.webapp.controller.helper.MonthHelper;
import it.f2informatica.webapp.controller.resolver.PeriodResolver;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

public abstract class AbstractConsultantController {
	protected static final String CONSULTANT_ID_SESSION_ATTR = "consultantId";

	@Autowired
	protected MonthHelper monthHelper;

	@Autowired
	protected PeriodResolver periodResolver;

	@Autowired
	protected ConsultantService consultantService;

	protected void addConsultantIdInSession(HttpSession session, String consultantId) {
		session.setAttribute(CONSULTANT_ID_SESSION_ATTR, consultantId);
	}

	protected String getConsultantIdFromSession(HttpSession session) {
		return String.valueOf(session.getAttribute(CONSULTANT_ID_SESSION_ATTR));
	}

	protected void removeConsultantIdFromSession(HttpSession session) {
		session.removeAttribute(CONSULTANT_ID_SESSION_ATTR);
	}

}
