package it.f2informatica.webapp.controller;

import com.google.common.collect.Lists;
import it.f2informatica.services.model.ConsultantModel;
import it.f2informatica.services.model.ExperienceModel;
import it.f2informatica.services.model.LanguageModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/consultant/profile")
public class ProfileController extends AbstractConsultantController {

	@RequestMapping(value = "/{consultantId}", method = RequestMethod.GET)
	public String profileRegistrationPage(
			@PathVariable String consultantId,
			HttpServletRequest request,
			ModelMap model) {

		ConsultantModel consultantModel = consultantService.findConsultantById(consultantId);
		addConsultantIdInSession(request.getSession(), consultantModel.getId());
		model.addAttribute("months", monthHelper.getMonths(request));
		model.addAttribute("consultantNo", consultantModel.getConsultantNo());
		model.addAttribute("consultantAge", consultantModel.getAge());
		model.addAttribute("registrationDate", consultantModel.getRegistrationDate());
		model.addAttribute("consultantFullName", consultantModel.getConsultantFullName());
		model.addAttribute("experienceModel", consultantService.buildNewExperienceModel());
		model.addAttribute("languageModel", consultantService.buildNewLanguageModel());
		model.addAttribute("experienceList", findExperiences(consultantId));
		model.addAttribute("languageList", findLanguages(consultantModel));
		model.addAttribute("skillList", findSkills(consultantModel));

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

	private List<LanguageModel> findLanguages(ConsultantModel consultantModel) {
		return (consultantModel.getProfile() != null)
				? consultantModel.getProfile().getLanguages()
				: Lists.<LanguageModel>newArrayList();
	}

	private String[] findSkills(ConsultantModel consultantModel) {
		List<String> skills = (consultantModel.getProfile() != null)
				? consultantModel.getProfile().getSkills()
				: Lists.<String>newArrayList();
		return skills.toArray(new String[skills.size()]);
	}

	@RequestMapping(value = "/addExperience", method = {RequestMethod.POST, RequestMethod.GET})
	public String saveExperience(
			@ModelAttribute("experienceModel") ExperienceModel experienceModel,
			BindingResult bindingResult,
			HttpServletRequest request) {

		experienceModel.setPeriodFrom(resolveExperiencePeriodFrom(request));
		experienceModel.setPeriodTo(resolveExperiencePeriodTo(request));
		String consultantId = getConsultantIdFromSession(request.getSession());
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

	@RequestMapping(value = "/addLanguage", method = {RequestMethod.POST, RequestMethod.GET})
	public String addLanguage(
			@ModelAttribute("languageModel") LanguageModel languageModel,
			BindingResult bindingResult, HttpSession session) {

		String consultantId = getConsultantIdFromSession(session);
		consultantService.addLanguage(languageModel, consultantId);

		return "redirect:/consultant/profile/" + consultantId;
	}

	@RequestMapping(value = "/addSkills", method = {RequestMethod.POST, RequestMethod.GET})
	public String addSkills(@RequestParam("skill") String[] skills, HttpSession session) {
		String consultantId = getConsultantIdFromSession(session);
		consultantService.addSkills(skills, consultantId);
		return "redirect:/consultant/profile/" + consultantId;
	}

}
