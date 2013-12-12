package it.f2informatica.webapp.controller;

import it.f2informatica.services.domain.consultant.ConsultantService;
import it.f2informatica.services.model.ConsultantModel;
import it.f2informatica.services.model.ExperienceModel;
import it.f2informatica.webapp.controller.helper.MonthHelper;
import it.f2informatica.webapp.controller.resolver.PeriodResolver;
import it.f2informatica.webapp.validator.ConsultantMasterDataValidator;
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
import java.util.List;

@Controller
@RequestMapping("/consultant")
public class ConsultantController {
	private static final String CONSULTANT_ID_SESSION_ATTR = "consultantId";

	@Autowired
	private MonthHelper monthHelper;

	@Autowired
	private PeriodResolver periodResolver;

	@Autowired
	private ConsultantService consultantService;

	@Autowired
	private ConsultantMasterDataValidator consultantMasterDataValidator;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String consultantRegistrationPage(ModelMap modelMap) {
		modelMap.addAttribute("consultantModel", consultantService.buildNewConsultantModel());
		return "consultant/masterDataRegistration";
	}

	@RequestMapping(value = "/registerMasterData", method = {RequestMethod.POST, RequestMethod.GET})
	public String registerConsultantMasterData(
				@ModelAttribute("consultantModel") ConsultantModel consultantModel,
				BindingResult bindingResult, ModelMap modelMap) {
		if (hasConsultantModelErrors(consultantModel, bindingResult)) {
			return "consultant/masterDataRegistration";
		}
		ConsultantModel consReg = consultantService.registerConsultantMasterData(consultantModel);
		return "redirect:/consultant/profile/" + consReg.getId();
	}

	private boolean hasConsultantModelErrors(ConsultantModel consultantModel, BindingResult bindingResult) {
		consultantMasterDataValidator.validate(consultantModel, bindingResult);
		return bindingResult.hasErrors();
	}

	@RequestMapping(value = "/profile/{consultantId}", method = RequestMethod.GET)
	public String profileRegistrationPage(@PathVariable String consultantId,
				HttpServletRequest request, ModelMap modelMap) {
		ConsultantModel consultantModel = consultantService.findConsultantById(consultantId);
		addConsultantIdInSession(request, consultantModel);
		modelMap.addAttribute("months", monthHelper.getMonths(request));
		modelMap.addAttribute("consultantNo", consultantModel.getConsultantNo());
		modelMap.addAttribute("consultantAge", consultantModel.getAge());
		modelMap.addAttribute("registrationDate", consultantModel.getRegistrationDate());
		modelMap.addAttribute("consultantFullName", consultantModel.getConsultantFullName());
		modelMap.addAttribute("experienceModel", consultantService.buildNewExperienceModel());
		modelMap.addAttribute("experienceList", findExperiences(consultantId));
		return "consultant/profileDataRegistration";
	}

	private List<ExperienceModel> findExperiences(String consultantId) {
		List<ExperienceModel> experiences = consultantService.findExperiences(consultantId);
		for (ExperienceModel experience : experiences) {
			experience.setPeriodFromStr(periodResolver.periodToString(experience.getPeriodFrom()));
			experience.setPeriodToStr(periodResolver.periodToString(experience.getPeriodTo()));
		}
		return experiences;
	}

	@RequestMapping(value = "/profile/saveExperience", method = RequestMethod.POST)
	public String saveExperience(@ModelAttribute("experienceModel") ExperienceModel experienceModel,
			BindingResult bindingResult, HttpServletRequest request) {
		experienceModel.setPeriodFrom(resolveExperiencePeriodFrom(request));
		experienceModel.setPeriodTo(resolveExperiencePeriodTo(request));
		String consultantId = getConsultantIdFromSession(request);
		consultantService.addConsultantExperience(experienceModel, consultantId);
		return "redirect:/consultant/profile/" + consultantId;
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
