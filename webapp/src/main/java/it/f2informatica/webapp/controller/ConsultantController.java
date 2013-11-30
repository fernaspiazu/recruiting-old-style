package it.f2informatica.webapp.controller;

import it.f2informatica.services.model.ConsultantModel;
import it.f2informatica.webapp.gateway.ConsultantServiceGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/consultant")
public class ConsultantController {

	@Autowired
	private ConsultantServiceGateway consultantServiceGateway;

	@RequestMapping(method = RequestMethod.GET)
	public String consultantManagementPage(ModelMap modelMap, Pageable pageable) {
		modelMap.addAttribute("consultants", consultantServiceGateway.showAllConsultants(pageable));
		return "consultant/consultants";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String consultantRegistrationPage(ModelMap modelMap) {
		modelMap.addAttribute("consultantModel", consultantServiceGateway.prepareForNewConsultantModel());
		return "consultant/consultantMasterDataRegistration";
	}

	@RequestMapping(value = "/registerMasterData", method = RequestMethod.POST)
	public String registerConsultantMasterData(
			@ModelAttribute("consultantModel") ConsultantModel consultantModel, BindingResult bindingResult) {
		ConsultantModel consReg = consultantServiceGateway.registerConsultantMasterData(consultantModel);
		return "redirect:/consultant/profileDataRegistration/" + consReg.getId();
	}

	@RequestMapping(value = "/profileDataRegistration/{consultantId}", method = RequestMethod.GET)
	public String profileRegistrationPage(@PathVariable String consultantId, ModelMap modelMap) {
		ConsultantModel consultant = consultantServiceGateway.findConsultantById(consultantId);
		modelMap.addAttribute("consultantNo", consultant.getConsultantNo())
						.addAttribute("registrationDate", consultant.getRegistrationDate())
						.addAttribute("consultantFullName", consultant.getConsultantFullName());
		return "consultant/profileDataRegistration";
	}

}
