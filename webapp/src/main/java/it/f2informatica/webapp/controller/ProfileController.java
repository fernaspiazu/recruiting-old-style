package it.f2informatica.webapp.controller;

import com.google.common.collect.Lists;
import it.f2informatica.datastore.constant.LanguageProficiency;
import it.f2informatica.services.model.ConsultantModel;
import it.f2informatica.services.model.ExperienceModel;
import it.f2informatica.services.model.LanguageModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static it.f2informatica.services.model.builder.LanguageModelBuilder.languageModel;

@Controller
@RequestMapping("/consultant/profile")
@SessionAttributes({"consultantId"})
public class ProfileController extends AbstractConsultantController {

	@RequestMapping(value = "/{consultantId}", method = RequestMethod.GET)
	public String profileRegistrationPage(
			@PathVariable("consultantId") String consultantId, ModelMap model) {

		ConsultantModel consultantModel = consultantService.findConsultantById(consultantId);
		model.addAttribute("consultantId", consultantModel.getId());
		model.addAttribute("months", monthHelper.getMonths());
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
		List<ExperienceModel> experiences = consultantService.findMinimalExperiences(consultantId);
		for (ExperienceModel experience : experiences) {
			reduceExperienceDescriptionToHundredCharacters(experience);
			experience.setPeriodFromStr(periodResolver.periodToString(experience.getPeriodFrom()));
			experience.setPeriodToStr(periodResolver.periodToString(experience.getPeriodTo()));
		}
		return experiences;
	}

	private void reduceExperienceDescriptionToHundredCharacters(ExperienceModel experience) {
		String description = experience.getDescription();
		if (description.length() >= 100) {
			experience.setDescription(description.substring(0, 96).concat("..."));
		}
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

	@RequestMapping(value = "/addExperience", method = RequestMethod.POST)
	public String saveExperience(
			@ModelAttribute("experienceModel") ExperienceModel experienceModel,
			@ModelAttribute("consultantId") String consultantId,
			@RequestParam("monthPeriodFrom") String monthPeriodFrom,
			@RequestParam("yearPeriodFrom") String yearPeriodFrom,
			@RequestParam("monthPeriodTo") String monthPeriodTo,
			@RequestParam("yearPeriodTo") String yearPeriodTo,
			BindingResult bindingResult) {

		experienceModel.setPeriodFrom(periodResolver.resolveDateByMonthAndYear(monthPeriodFrom, yearPeriodFrom));
		experienceModel.setPeriodTo(periodResolver.resolveDateByMonthAndYear(monthPeriodTo, yearPeriodTo));
		consultantService.addConsultantExperience(experienceModel, consultantId);

		return "redirect:/consultant/profile/" + consultantId;
	}

	@RequestMapping(value = "/addLanguage", method = RequestMethod.POST)
	public String addLanguage(
			@RequestParam("language") String[] languages,
			@RequestParam("proficiency") String[] proficiencies,
			@ModelAttribute("consultantId") String consultantId,
			BindingResult bindingResult) {

		List<LanguageModel> languagesModel = Lists.newArrayList();
		for (int i = 0; i < languages.length; i++) {
			if (StringUtils.isEmpty(languages[i].trim())) {
				continue;
			}
			languagesModel.add(languageModel(languages[i])
					.withProficiency(resolveProficiency(proficiencies[i])).build());
		}
		consultantService.addLanguages(languagesModel.toArray(new LanguageModel[languagesModel.size()]), consultantId);
		return "redirect:/consultant/profile/" + consultantId;
	}

	private LanguageProficiency resolveProficiency(String proficiency) {
		for (LanguageProficiency languageProficiency : LanguageProficiency.values()) {
			if (proficiency.equals(languageProficiency.toString())) {
				return languageProficiency;
			}
		}
		return LanguageProficiency.ELEMENTARY;
	}

	@RequestMapping(value = "/addSkills", method = RequestMethod.POST)
	public String addSkills(
			@RequestParam("skill") String[] skills,
			@ModelAttribute("consultantId") String consultantId) {
		consultantService.addSkills(skills, consultantId);
		return "redirect:/consultant/profile/" + consultantId;
	}

}
