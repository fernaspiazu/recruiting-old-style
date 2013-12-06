package it.f2informatica.webapp.controller;

import com.google.common.collect.Lists;
import it.f2informatica.services.model.ConsultantModel;
import it.f2informatica.services.model.ExperienceModel;
import it.f2informatica.webapp.gateway.ConsultantServiceGateway;
import it.f2informatica.webapp.help.Month;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Controller
@RequestMapping("/consultant")
public class ConsultantController {
	private static final String CONSULTANT_ID_SESSION_ATTR = "consultantId";

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ConsultantServiceGateway consultantServiceGateway;

	@RequestMapping(method = RequestMethod.GET)
	public String consultantManagementPage(ModelMap modelMap, Pageable pageable) {
		modelMap.addAttribute("consultants", consultantServiceGateway.showAllConsultants(pageable));
		return "consultant/consultants";
	}

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
	public String profileRegistrationPage(@PathVariable String consultantId, HttpServletRequest request, ModelMap modelMap) {
		ConsultantModel consultantModel = consultantServiceGateway.findConsultantById(consultantId);
		/*
		 * TODO: Validation, checkNotNull(consultantModel)
		 */
		addConsultantIdInSession(request, consultantModel);
		modelMap.addAttribute("consultantFullName", consultantModel.getConsultantFullName())
				.addAttribute("consultantNo", consultantModel.getConsultantNo())
				.addAttribute("registrationDate", consultantModel.getRegistrationDate())
				.addAttribute("months", getMonths(request))
				.addAttribute("experienceModel", consultantServiceGateway.prepareNewExperienceModel())
				.addAttribute("experienceList", consultantServiceGateway.getConsultantExperiences(consultantId));
		return "consultant/profileDataRegistration";
	}

	@RequestMapping(value = "/profileDataRegistration/saveExperience", method = RequestMethod.POST)
	public String saveExperience(@ModelAttribute("experienceModel") ExperienceModel experienceModel,
															 BindingResult bindingResult, HttpServletRequest request) {
		String consultantId = String.valueOf(request.getSession().getAttribute(CONSULTANT_ID_SESSION_ATTR));
		resolvePeriods(experienceModel, request);
		consultantServiceGateway.addConsultantExperience(experienceModel, consultantId);
		return "redirect:/consultant/profileDataRegistration/" + consultantId;
	}

	private void resolvePeriods(ExperienceModel experienceModel, HttpServletRequest request) {
		String monthPeriodFrom = request.getParameter("monthPeriodFrom");
		String yearPeriodFrom = request.getParameter("yearPeriodFrom");
		experienceModel.setPeriodFrom(getDate(monthPeriodFrom, yearPeriodFrom));

		String monthPeriodTo = request.getParameter("monthPeriodTo");
		String yearPeriodTo = request.getParameter("yearPeriodTo");
		experienceModel.setPeriodTo(getDate(monthPeriodTo, yearPeriodTo));
	}

	private Date getDate(String month, String year) {
		try {
			return new GregorianCalendar(Integer.valueOf(year), Integer.valueOf(month), 1).getTime();
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private void addConsultantIdInSession(HttpServletRequest request, ConsultantModel consultantModel) {
		request.getSession().setAttribute(CONSULTANT_ID_SESSION_ATTR, consultantModel.getId());
	}

	private void removeConsultantIdFromSession(HttpServletRequest request) {
		request.getSession().removeAttribute(CONSULTANT_ID_SESSION_ATTR);
	}

	private List<Month> getMonths(HttpServletRequest request) {
		return Lists.newArrayList(
				new Month("0", getMessage("month.january", request)),
				new Month("1", getMessage("month.february", request)),
				new Month("2", getMessage("month.march", request)),
				new Month("3", getMessage("month.april", request)),
				new Month("4", getMessage("month.may", request)),
				new Month("5", getMessage("month.june", request)),
				new Month("6", getMessage("month.july", request)),
				new Month("7", getMessage("month.august", request)),
				new Month("8", getMessage("month.september", request)),
				new Month("9", getMessage("month.october", request)),
				new Month("10", getMessage("month.november", request)),
				new Month("11", getMessage("month.december", request))
		);
	}

	private String getMessage(String label, HttpServletRequest request) {
		return messageSource.getMessage(label, null, request.getLocale());
	}

}
