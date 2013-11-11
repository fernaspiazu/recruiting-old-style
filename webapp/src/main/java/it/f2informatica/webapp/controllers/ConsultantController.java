package it.f2informatica.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/consultant")
public class ConsultantController {

	@RequestMapping(value = "/consultant-management", method = RequestMethod.GET)
	public String consultantManagementPage() {
		return "consultant/consultants";
	}

}
