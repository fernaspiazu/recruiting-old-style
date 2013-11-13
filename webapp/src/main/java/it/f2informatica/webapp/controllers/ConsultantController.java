package it.f2informatica.webapp.controllers;

import it.f2informatica.webapp.gateway.ConsultantServiceGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/consultant")
public class ConsultantController {

	@Autowired
	private ConsultantServiceGateway consultantServiceGateway;

	@RequestMapping(value = "/consultant-management", method = RequestMethod.GET)
	public String consultantManagementPage(ModelMap modelMap, Pageable pageable) {
		modelMap.addAttribute("consultants", consultantServiceGateway.findAllConsultants(pageable));
		return "consultant/consultants";
	}

}
