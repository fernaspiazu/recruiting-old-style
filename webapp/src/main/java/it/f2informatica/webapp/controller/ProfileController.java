package it.f2informatica.webapp.controller;

import it.f2informatica.services.model.ConsultantModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

//@Controller
//@RequestMapping("/consultant/profile")
//@SessionAttributes({"consultantId"})
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
		model.addAttribute("skillList", consultantModel.getSkills());
		return "consultant/profileDataRegistration";
	}

	@RequestMapping(value = "/addLanguage", method = RequestMethod.POST)
	public String addLanguage(@RequestParam("language") String[] languages, @RequestParam("proficiency") String[] proficiencies,
														@ModelAttribute("consultantId") String consultantId) {
		consultantService.addLanguages(getLanguageModels(languages, proficiencies), consultantId);
		return "redirect:/consultant/profile/" + consultantId;
	}

	@RequestMapping(value = "/addSkills", method = RequestMethod.POST)
	public String addSkills(@RequestParam("skill") String[] skills, @ModelAttribute("consultantId") String consultantId) {
		consultantService.addSkills(skills, consultantId);
		return "redirect:/consultant/profile/" + consultantId;
	}

}
