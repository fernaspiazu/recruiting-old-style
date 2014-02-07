package it.f2informatica.webapp.controller;

import com.google.common.collect.Lists;
import it.f2informatica.datastore.constant.LanguageProficiency;
import it.f2informatica.services.model.ConsultantModel;
import it.f2informatica.services.model.ExperienceModel;
import it.f2informatica.services.model.LanguageModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static it.f2informatica.services.model.builder.LanguageModelBuilder.languageModel;

@Controller
@RequestMapping("/consultant/profile")
@SessionAttributes({"consultantId"})
public class ProfileController extends AbstractConsultantController {

	@RequestMapping(value = "/{consultantId}", method = RequestMethod.GET)
	public String profileRegistrationPage(@PathVariable("consultantId") String consultantId, ModelMap model) {
		ConsultantModel consultantModel = consultantService.findConsultantById(consultantId);
		model.addAttribute("consultantId", consultantModel.getId());
		model.addAttribute("consultantNo", consultantModel.getConsultantNo());
		model.addAttribute("consultantAge", consultantModel.getAge());
		model.addAttribute("registrationDate", consultantModel.getRegistrationDate());
		model.addAttribute("consultantFullName", consultantModel.getConsultantFullName());
		model.addAttribute("languageModel", consultantService.buildNewLanguageModel());
		model.addAttribute("languageList", consultantModel.getLanguages());
		model.addAttribute("experienceList", findMinimalExperiences(consultantId));
		model.addAttribute("skillList", findSkills(consultantModel));
		return "consultant/profileDataRegistration";
	}

	private List<ExperienceModel> findMinimalExperiences(String consultantId) {
		List<ExperienceModel> experiences = consultantService.findMinimalExperiences(consultantId);
		for (ExperienceModel experience : experiences) {
			reduceExperienceDescriptionToHundredCharacters(experience);
			formatExperiencePeriods(experience);
		}
		return experiences;
	}

	private void reduceExperienceDescriptionToHundredCharacters(ExperienceModel experience) {
		String description = experience.getDescription();
		if (description.length() >= 100) {
			experience.setDescription(description.substring(0, 96).concat("..."));
		}
	}

	private String[] findSkills(ConsultantModel consultantModel) {
		List<String> skillList = consultantModel.getSkills();
		return skillList.toArray(new String[skillList.size()]);
	}

	@RequestMapping(value = "/addLanguage", method = RequestMethod.POST)
	public String addLanguage(
					@RequestParam("language") String[] languages,
					@RequestParam("proficiency") String[] proficiencies,
					@ModelAttribute("consultantId") String consultantId) {

		List<LanguageModel> languagesModel = Lists.newArrayList();
		for (int i = 0; i < languages.length; i++) {
			if (StringUtils.isEmpty(languages[i].trim())) continue;
			languagesModel.add(languageModel(languages[i]).withProficiency(resolveProficiency(proficiencies[i])).build());
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
	public String addSkills(@RequestParam("skill") String[] skills, @ModelAttribute("consultantId") String consultantId) {
		consultantService.addSkills(skills, consultantId);
		return "redirect:/consultant/profile/" + consultantId;
	}

}
