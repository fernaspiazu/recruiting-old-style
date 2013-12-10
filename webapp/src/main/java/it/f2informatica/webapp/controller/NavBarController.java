package it.f2informatica.webapp.controller;

import it.f2informatica.webapp.gateway.ConsultantServiceGateway;
import it.f2informatica.webapp.gateway.UserServiceGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NavBarController {
	public static final String NAVBAR_ITEM_ACTIVE = "navbarItemActive";

	@Autowired
	private UserServiceGateway userServiceGateway;

	@Autowired
	private ConsultantServiceGateway consultantServiceGateway;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String homePage(HttpServletRequest request) {
		request.getSession().setAttribute(NAVBAR_ITEM_ACTIVE, 0);
		return "homePage";
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String userManagementPage(ModelMap modelMap, Pageable pageable, HttpServletRequest request) {
		modelMap.addAttribute("users", userServiceGateway.findAllUsers(pageable));
		request.getSession().setAttribute(NAVBAR_ITEM_ACTIVE, 1);
		return "user/users";
	}

	@RequestMapping(value = "/consultant", method = RequestMethod.GET)
	public String consultantManagementPage(ModelMap modelMap, Pageable pageable, HttpServletRequest request) {
		modelMap.addAttribute("consultants", consultantServiceGateway.showAllConsultants(pageable));
		request.getSession().setAttribute(NAVBAR_ITEM_ACTIVE, 2);
		return "consultant/consultants";
	}

}
