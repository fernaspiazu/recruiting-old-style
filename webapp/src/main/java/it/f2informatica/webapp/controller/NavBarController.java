package it.f2informatica.webapp.controller;

import it.f2informatica.webapp.gateway.ConsultantServiceGateway;
import it.f2informatica.webapp.gateway.UserServiceGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class NavBarController {

	@Autowired
	private UserServiceGateway userServiceGateway;

	@Autowired
	private ConsultantServiceGateway consultantServiceGateway;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String homePage(ModelMap modelMap) {
		return "homePage";
	}

	@RequestMapping(value = "/user/loadUsers", method = RequestMethod.GET)
	public String userManagementPage(ModelMap modelMap, Pageable pageable) {
		modelMap.addAttribute("users", userServiceGateway.findAllUsers(pageable));
		return "user/users";
	}

	@RequestMapping(value = "/consultant", method = RequestMethod.GET)
	public String consultantManagementPage(ModelMap modelMap, Pageable pageable) {
		modelMap.addAttribute("consultants", consultantServiceGateway.showAllConsultants(pageable));
		return "consultant/consultants";
	}

}
