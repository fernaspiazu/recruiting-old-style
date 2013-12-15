package it.f2informatica.webapp.controller;

import it.f2informatica.services.model.ConsultantModel;
import it.f2informatica.webapp.validator.ConsultantMasterDataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/consultant")
public class ConsultantController extends AbstractConsultantController {

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
			BindingResult bindingResult) {

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

}
