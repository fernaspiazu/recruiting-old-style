package it.f2informatica.webapp.controller;

import it.f2informatica.services.model.ConsultantModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/consultant")
public class ConsultantController extends AbstractConsultantController {

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String consultantRegistrationPage(ModelMap modelMap) {
		modelMap.addAttribute("consultantModel", consultantService.buildNewConsultantModel());
		return "consultant/masterDataRegistration";
	}

	@RequestMapping(value = "/registerMasterData", method = {RequestMethod.POST, RequestMethod.GET})
	public String registerConsultantMasterData(@ModelAttribute("consultantModel") ConsultantModel consultantModel, BindingResult bindingResult) {
		ConsultantModel consReg = consultantService.registerConsultantMasterData(consultantModel);
		return "redirect:/consultant/profile/" + consReg.getId();
	}

}
