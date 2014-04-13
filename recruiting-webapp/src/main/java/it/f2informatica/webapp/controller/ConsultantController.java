package it.f2informatica.webapp.controller;

import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import it.f2informatica.core.model.ConsultantModel;
import it.f2informatica.core.model.EducationModel;
import it.f2informatica.core.model.ExperienceModel;
import it.f2informatica.core.model.LanguageModel;
import it.f2informatica.core.services.ConsultantService;
import it.f2informatica.core.validator.ConsultantEducationValidator;
import it.f2informatica.core.validator.ConsultantExperienceValidator;
import it.f2informatica.core.validator.ConsultantPersonalDetailsValidator;
import it.f2informatica.core.validator.utils.ValidationResponse;
import it.f2informatica.core.validator.utils.ValidationResponseHandler;
import it.f2informatica.webapp.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/consultant")
@SessionAttributes({"consultantId"})
public class ConsultantController {

  @Autowired
  private Gson gson;

  @Autowired
  private MonthHelper monthHelper;

  @Autowired
  private PeriodParser periodParser;

  @Autowired
  private CurrentHttpRequest httpRequest;

  @Autowired
  private ConsultantService consultantService;

  @Autowired
  private ConsultantEducationValidator educationValidator;

  @Autowired
  private ConsultantExperienceValidator experienceValidator;

  @Autowired
  private ValidationResponseHandler validationResponseHandler;

  @Autowired
  private ConsultantPersonalDetailsValidator personalDetailsValidator;

  @ModelAttribute("months")
  public List<Month> loadMonths() {
    return monthHelper.getMonths();
  }

  @RequestMapping(value = "/new-consultant", method = RequestMethod.GET)
  public String createConsultant(ModelMap modelMap) {
    modelMap.addAttribute("consultantModel", consultantService.buildNewConsultantModel());
    return "consultant/consultantForm";
  }

  @RequestMapping(value = "/save-personal-details", method = RequestMethod.POST)
  public String savePersonalDetails(@ModelAttribute("consultantModel") ConsultantModel consultantModel) {
    consultantService.savePersonalDetails(consultantModel);
    return "redirect:/consultants";
  }

  @RequestMapping(value = "/edit-personal-details", method = RequestMethod.GET)
  public String editPersonalDetails(@RequestParam("consultantId") String consultantId, ModelMap model) {
    model.addAttribute("edit", true);
    model.addAttribute("consultantId", consultantId);
    model.addAttribute("consultantModel", consultantService.findConsultantById(consultantId));
    return "consultant/consultantForm";
  }

  @RequestMapping(value = "/update-personal-details", method = RequestMethod.POST)
  public String updatePersonalDetails(@ModelAttribute("consultantModel") ConsultantModel consultantModel) {
    consultantService.updatePersonalDetails(consultantModel, consultantModel.getId());
    return "redirect:/consultants";
  }

  @RequestMapping(value = "/validate-personal-details", method = RequestMethod.POST, produces = MediaTypeUTF8.JSON_UTF_8)
  public @ResponseBody ValidationResponse validatePersonalDetails(@ModelAttribute("consultantModel") ConsultantModel consultantModel, BindingResult result) {
    personalDetailsValidator.validate(consultantModel, result);
    if (result.hasErrors()) {
      return validationResponseHandler.validationFail(result, httpRequest.getLocale());
    }
    return validationResponseHandler.validationSuccess();
  }

  @RequestMapping(value = "/profile", method = RequestMethod.GET)
  public String profilePage(@RequestParam String consultantId, ModelMap model) {
    ConsultantModel consultantModel = consultantService.findConsultantById(consultantId);
    setTotalTimeOfPeriodWhichHasElapsed(consultantModel);
    model.addAttribute("consultantId", consultantId);
    model.addAttribute("consultantModel", consultantModel);
    model.addAttribute("experienceModel", new ExperienceModel());
    model.addAttribute("educationModel", new EducationModel());
    return "consultant/profileForm";
  }

  private void setTotalTimeOfPeriodWhichHasElapsed(ConsultantModel consultantModel) {
    for (ExperienceModel experienceModel : consultantModel.getExperiences()) {
      Date from = experienceModel.getPeriodFrom();
      Date to = experienceModel.getPeriodTo();
      experienceModel.setTotalPeriodElapsed(periodParser.printTotalTimeOfPeriodWhichHasElapsed(from, to));
    }
  }

  @RequestMapping(value = "/edit-experience", method = RequestMethod.GET, produces = MediaTypeUTF8.JSON_UTF_8)
  public @ResponseBody String editExperience(@ModelAttribute("consultantId") String consultantId, @RequestParam String experienceId) {
    ExperienceModel experienceModel = consultantService.findExperience(consultantId, experienceId);
    formatDateByMonthNameAndYear(experienceModel);
    return gson.toJson(experienceModel);
  }

  private void formatDateByMonthNameAndYear(ExperienceModel experienceModel) {
    experienceModel.setFormattedPeriodFrom(periodParser.formatDateByMonthNameAndYear(experienceModel.getPeriodFrom()));
    if (!experienceModel.isCurrent()) {
      experienceModel.setFormattedPeriodTo(periodParser.formatDateByMonthNameAndYear(experienceModel.getPeriodTo()));
    }
  }

  @RequestMapping(value = "/save-experience", method = RequestMethod.POST)
  public String saveExperience(@ModelAttribute("experienceModel") ExperienceModel experienceModel, @ModelAttribute("consultantId") String consultantId) {
    setExperiencePeriods(experienceModel);
    consultantService.addConsultantExperience(experienceModel, consultantId);
    return "redirect:/consultant/profile";
  }

  @RequestMapping(value = "/update-experience", method = RequestMethod.POST)
  public String updateExperience(@ModelAttribute("experienceModel") ExperienceModel experienceModel, @ModelAttribute("consultantId") String consultantId) {
    setExperiencePeriods(experienceModel);
    consultantService.updateConsultantExperience(experienceModel, consultantId);
    return "redirect:/consultant/profile";
  }

  private void setExperiencePeriods(ExperienceModel experienceModel) {
    experienceModel.setPeriodFrom(periodParser.resolveDateByMonthAndYear(experienceModel.getMonthFrom(), experienceModel.getYearFrom()));
    if (!experienceModel.isCurrent()) {
      experienceModel.setPeriodTo(periodParser.resolveDateByMonthAndYear(experienceModel.getMonthTo(), experienceModel.getYearTo()));
    }
  }

  @RequestMapping(value = "/validate-experience", method = RequestMethod.POST, produces = MediaTypeUTF8.JSON_UTF_8)
  public @ResponseBody ValidationResponse validateProfile(@ModelAttribute("experienceModel") ExperienceModel experienceModel, BindingResult result) {
    experienceValidator.validate(experienceModel, result);
    if (result.hasErrors()) {
      return validationResponseHandler.validationFail(result, httpRequest.getLocale());
    }
    return validationResponseHandler.validationSuccess();
  }

  @RequestMapping(value = "/delete-experience", method = RequestMethod.GET)
  public String deleteExperience(@ModelAttribute("consultantId") String consultantId, @RequestParam String experienceId) {
    consultantService.removeExperience(consultantId, experienceId);
    return "redirect:/consultant/profile";
  }

  @RequestMapping(value = "/save-languages", method = RequestMethod.POST)
  public String saveLanguages(@ModelAttribute("consultantModel") ConsultantModel consultantModel, @ModelAttribute("consultantId") String consultantId) {
    consultantService.addLanguages(Iterables.toArray(consultantModel.getLanguages(), LanguageModel.class), consultantId);
    return "redirect:/consultant/profile";
  }

  @RequestMapping(value = "/save-skills", method = RequestMethod.POST)
  public String saveSkills(@ModelAttribute("consultantId") String consultantId, @RequestParam("skill") String[] skill) {
    consultantService.addSkills(skill, consultantId);
    return "redirect:/consultant/profile";
  }

  @RequestMapping(value = "/save-education", method = RequestMethod.POST)
  public String saveEducation(@ModelAttribute("educationModel") EducationModel educationModel, @ModelAttribute("consultantId") String consultantId) {
    consultantService.addConsultantEducation(educationModel, consultantId);
    return "redirect:/consultant/profile";
  }

  @RequestMapping(value = "/edit-education", method = RequestMethod.GET, produces = MediaTypeUTF8.JSON_UTF_8)
  public @ResponseBody String editEducation(@ModelAttribute("consultantId") String consultantId, @RequestParam String educationId) {
    EducationModel educationModel = consultantService.findEducation(consultantId, educationId);
    return gson.toJson(educationModel);
  }

  @RequestMapping(value = "/update-education", method = RequestMethod.POST)
  public String updateEducation(@ModelAttribute("educationModel") EducationModel educationModel, @ModelAttribute("consultantId") String consultantId) {
    consultantService.updateConsultantEducation(educationModel, consultantId);
    return "redirect:/consultant/profile";
  }

  @RequestMapping(value = "/delete-education", method = RequestMethod.GET)
  public String deleteEducation(@ModelAttribute("consultantId") String consultantId, @RequestParam String educationId) {
    consultantService.removeEducation(consultantId, educationId);
    return "redirect:/consultant/profile";
  }

  @RequestMapping(value = "/validate-education", method = RequestMethod.POST, produces = MediaTypeUTF8.JSON_UTF_8)
  public @ResponseBody ValidationResponse validateEducation(@ModelAttribute("educationModel") EducationModel educationModel, BindingResult result) {
    educationValidator.validate(educationModel, result);
    if (result.hasErrors()) {
      return validationResponseHandler.validationFail(result, httpRequest.getLocale());
    }
    return validationResponseHandler.validationSuccess();
  }

}
