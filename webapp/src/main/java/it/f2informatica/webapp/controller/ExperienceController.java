package it.f2informatica.webapp.controller;

import it.f2informatica.services.model.ExperienceModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/consultant/profile/experiences")
@SessionAttributes({"consultantId"})
public class ExperienceController extends AbstractConsultantController {

	@RequestMapping(value = "/{consultantId}", method = RequestMethod.GET)
	public String experiencesPage(@PathVariable("consultantId") String consultantId, ModelMap model) {
		model.addAttribute("experienceList", findExperiences(consultantId));
		return "consultant/experiencesPage";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String addNewExperience(ModelMap model) {
		model.addAttribute("months", monthHelper.getMonths());
		model.addAttribute("experienceModel", consultantService.buildNewExperienceModel());
		return "consultant/experienceForm";
	}

	@RequestMapping(value = {"/save", "/update"},method = RequestMethod.POST)
	public String saveOrUpdateExperience(
			@ModelAttribute("experienceModel") ExperienceModel experienceModel,
			@ModelAttribute("consultantId") String consultantId,
			@RequestParam("monthPeriodFrom") String monthPeriodFrom,
			@RequestParam("yearPeriodFrom") String yearPeriodFrom,
			@RequestParam("monthPeriodTo") String monthPeriodTo,
			@RequestParam("yearPeriodTo") String yearPeriodTo,
			@RequestParam("isEdit") String isEdit) {

		experienceModel.setPeriodFrom(periodResolver.resolveDateByMonthAndYear(monthPeriodFrom, yearPeriodFrom));
		if (!experienceModel.isCurrent()) {
			experienceModel.setPeriodTo(periodResolver.resolveDateByMonthAndYear(monthPeriodTo, yearPeriodTo));
		}
		if (isExperienceInEditForm(isEdit)) {
			consultantService.updateConsultantExperience(experienceModel, consultantId);
			return "redirect:/consultant/profile/experiences/" + consultantId;
		} else {
			consultantService.addConsultantExperience(experienceModel, consultantId);
			return "redirect:/consultant/profile/" + consultantId;
		}
	}

	private boolean isExperienceInEditForm(String isEdit) {
		return StringUtils.hasText(isEdit) && Boolean.parseBoolean(isEdit);
	}

	@RequestMapping(value = "/delete/{expId}", method = RequestMethod.GET)
	public String removeExperience(@PathVariable("expId") String experienceId, @ModelAttribute("consultantId") String consultantId) {
		consultantService.removeExperience(consultantId, experienceId);
		return "redirect:/consultant/profile/experiences/" + consultantId;
	}

	@RequestMapping(value = "/edit/{consultantId}/{expId}", method = RequestMethod.GET)
	public String editExperience(ModelMap model,
			@PathVariable("consultantId") String consultantId,
			@PathVariable("expId") String experienceId) {

		ExperienceModel experienceModel = consultantService.findExperience(consultantId, experienceId);
		model.addAttribute("edit", true);
		model.addAttribute("months", monthHelper.getMonths());
		model.addAttribute("monthPeriodFrom", getExperienceMonth(experienceModel.getPeriodFrom()));
		model.addAttribute("yearPeriodFrom", getExperienceYear(experienceModel.getPeriodFrom()));
		if (experienceModel.getPeriodTo() != null && !experienceModel.isCurrent()) {
			model.addAttribute("monthPeriodTo", getExperienceMonth(experienceModel.getPeriodTo()));
			model.addAttribute("yearPeriodTo", getExperienceYear(experienceModel.getPeriodTo()));
		}
		model.addAttribute("experienceModel", experienceModel);
		return "consultant/experienceForm";
	}

	private String getExperienceMonth(Date date) {
		return String.valueOf(createCalendar(date).get(Calendar.MONTH));
	}

	private String getExperienceYear(Date date) {
		return String.valueOf(createCalendar(date).get(Calendar.YEAR));
	}

	private Calendar createCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

}
