package it.f2informatica.webapp.controller;

import it.f2informatica.services.model.ConsultantModel;
import it.f2informatica.services.validator.ConsultantPersonalDetailsValidator;
import it.f2informatica.services.validator.utils.ValidationResponse;
import it.f2informatica.services.validator.utils.ValidationResponseService;
import it.f2informatica.webapp.utils.CurrentHttpServletRequest;
import it.f2informatica.webapp.utils.Month;
import it.f2informatica.webapp.utils.MonthHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/consultant")
public class ConsultantController extends AbstractConsultantController {

	@Autowired
	private MonthHelper monthHelper;

	@Autowired
	private CurrentHttpServletRequest httpRequest;

	@Autowired
	private ValidationResponseService validationResponseService;

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

	@RequestMapping(value = "/validate-personal-details", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ValidationResponse validatePersonalDetails(@ModelAttribute("consultantModel") ConsultantModel consultantModel, BindingResult result) {
		personalDetailsValidator.validate(consultantModel, result);
		if (result.hasErrors()) {
			return validationResponseService.validationFail(result, httpRequest.getRequestLocale());
		}
		return validationResponseService.validationSuccess();
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String profilePage(@RequestParam String consultantId, ModelMap model) {
		ConsultantModel consultantModel = consultantService.findConsultantById(consultantId);
		model.addAttribute("consultantModel", consultantModel);
		return "consultant/profileForm";
	}

}
