package it.f2informatica.webapp.controller;

import it.f2informatica.services.model.ConsultantModel;
import it.f2informatica.services.model.ExperienceModel;
import it.f2informatica.webapp.controller.helper.MonthHelper;
import it.f2informatica.webapp.controller.resolver.PeriodResolver;
import it.f2informatica.webapp.gateway.ConsultantServiceGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("/consultant")
public class ConsultantController {
	private static final String CONSULTANT_ID_SESSION_ATTR = "consultantId";

	@Autowired
	private MonthHelper monthHelper;

	@Autowired
	private PeriodResolver periodResolver;

	@Autowired
	private ConsultantServiceGateway consultantServiceGateway;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String consultantRegistrationPage(ModelMap modelMap) {
		modelMap.addAttribute("consultantModel", consultantServiceGateway.prepareForNewConsultantModel());
		return "consultant/consultantMasterDataRegistration";
	}

	@RequestMapping(value = "/registerMasterData", method = RequestMethod.POST)
	public String registerConsultantMasterData(
			@ModelAttribute("consultantModel") ConsultantModel consultantModel, BindingResult bindingResult) {
		ConsultantModel consReg = consultantServiceGateway.registerConsultantMasterData(consultantModel);
		return "redirect:/consultant/profileDataRegistration/" + consReg.getId();
	}

	@RequestMapping(value = "/profileDataRegistration/{consultantId}", method = RequestMethod.GET)
	public String profileRegistrationPage(
			@PathVariable String consultantId,
			HttpServletRequest request, ModelMap modelMap) {
		ConsultantModel consultantModel = consultantServiceGateway.findConsultantById(consultantId);
		addConsultantIdInSession(request, consultantModel);
		modelMap.addAttribute("months", monthHelper.getMonths(request));
		modelMap.addAttribute("consultantNo", consultantModel.getConsultantNo());
		modelMap.addAttribute("registrationDate", consultantModel.getRegistrationDate());
		modelMap.addAttribute("consultantFullName", consultantModel.getConsultantFullName());
		modelMap.addAttribute("experienceModel", consultantServiceGateway.prepareNewExperienceModel());
		modelMap.addAttribute("experienceList", consultantServiceGateway.getConsultantExperiences(consultantId));
		return "consultant/profileDataRegistration";
	}

	@RequestMapping(value = "/profileDataRegistration/saveExperience", method = RequestMethod.POST)
	public String saveExperience(
			@ModelAttribute("experienceModel") ExperienceModel experienceModel,
			BindingResult bindingResult, HttpServletRequest request) {
		experienceModel.setPeriodFrom(resolveExperiencePeriodFrom(request));
		experienceModel.setPeriodTo(resolveExperiencePeriodTo(request));
		String consultantId = getConsultantIdFromSession(request);
		consultantServiceGateway.addConsultantExperience(experienceModel, consultantId);
		return "redirect:/consultant/profileDataRegistration/" + consultantId;
	}

	private Date resolveExperiencePeriodFrom(HttpServletRequest request) {
		String monthPeriodFrom = request.getParameter("monthPeriodFrom");
		String yearPeriodFrom = request.getParameter("yearPeriodFrom");
		return periodResolver.resolveDateByMonthAndYear(monthPeriodFrom, yearPeriodFrom);
	}

	private Date resolveExperiencePeriodTo(HttpServletRequest request) {
		String monthPeriodTo = request.getParameter("monthPeriodTo");
		String yearPeriodTo = request.getParameter("yearPeriodTo");
		return periodResolver.resolveDateByMonthAndYear(monthPeriodTo, yearPeriodTo);
	}

	private void addConsultantIdInSession(HttpServletRequest request, ConsultantModel consultantModel) {
		request.getSession().setAttribute(CONSULTANT_ID_SESSION_ATTR, consultantModel.getId());
	}

	private String getConsultantIdFromSession(HttpServletRequest request) {
		return String.valueOf(request.getSession().getAttribute(CONSULTANT_ID_SESSION_ATTR));
	}

	private void removeConsultantIdFromSession(HttpServletRequest request) {
		request.getSession().removeAttribute(CONSULTANT_ID_SESSION_ATTR);
	}

}
