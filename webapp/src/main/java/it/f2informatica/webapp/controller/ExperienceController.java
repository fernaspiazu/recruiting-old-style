package it.f2informatica.webapp.controller;

import it.f2informatica.services.model.ExperienceModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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

	private List<ExperienceModel> findExperiences(String consultantId) {
		List<ExperienceModel> experiences = consultantService.findExperiences(consultantId);
		for (ExperienceModel experience : experiences) {
			formatExperiencePeriods(experience);
		}
		return experiences;
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String addNewExperience(ModelMap model) {
		model.addAttribute("months", monthHelper.getMonths());
		model.addAttribute("experienceModel", consultantService.buildNewExperienceModel());
		return "consultant/experienceForm";
	}

	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public String saveExperience(
			@ModelAttribute("experienceModel") ExperienceModel experienceModel,
			@ModelAttribute("consultantId") String consultantId,
			@RequestParam("monthPeriodFrom") String monthPeriodFrom,
			@RequestParam("yearPeriodFrom") String yearPeriodFrom,
			@RequestParam("monthPeriodTo") String monthPeriodTo,
			@RequestParam("yearPeriodTo") String yearPeriodTo) {

		boolean isNotCurrentJob = !experienceModel.isCurrent();

		experienceModel.setPeriodFrom(periodResolver.resolveDateByMonthAndYear(monthPeriodFrom, yearPeriodFrom));
		if (isNotCurrentJob) {
			experienceModel.setPeriodTo(periodResolver.resolveDateByMonthAndYear(monthPeriodTo, yearPeriodTo));
		}
		consultantService.addConsultantExperience(experienceModel, consultantId);

		return "redirect:/consultant/profile/" + consultantId;
	}

	@RequestMapping(value = "/verifyExperiencePeriods", method = RequestMethod.POST)
	@ResponseBody
	public String areExperiencePeriodsValid(
			@RequestParam("monthPeriodFrom") String monthPeriodFrom,
			@RequestParam("yearPeriodFrom") String yearPeriodFrom,
			@RequestParam("monthPeriodTo") String monthPeriodTo,
			@RequestParam("yearPeriodTo") String yearPeriodTo,
			@RequestParam("isCurrentJob") String isCurrentJob) {

		Date currentDate = new Date();
		Date periodFrom = periodResolver.resolveDateByMonthAndYear(monthPeriodFrom, yearPeriodFrom);
		if (!Boolean.parseBoolean(isCurrentJob)) {
			Date periodTo = periodResolver.resolveDateByMonthAndYear(monthPeriodTo, yearPeriodTo);
			return String.valueOf(currentDate.after(periodFrom) && periodFrom.before(periodTo));
		} else {
			return String.valueOf(currentDate.after(periodFrom));
		}
	}

}
