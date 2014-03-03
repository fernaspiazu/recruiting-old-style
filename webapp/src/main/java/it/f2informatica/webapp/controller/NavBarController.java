package it.f2informatica.webapp.controller;

import it.f2informatica.services.consultant.ConsultantService;
import it.f2informatica.services.user.UserService;
import it.f2informatica.webapp.security.SecurityAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"navbarItemActive", "roles"})
public class NavBarController {
	public static final String NAVBAR_ITEM_ACTIVE = "navbarItemActive";

	@Autowired
	private SecurityAccessor securityAccessor;

	@Autowired
	private UserService userService;

	@Autowired
	private ConsultantService consultantService;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String homePage(ModelMap model) {
		model.addAttribute(NAVBAR_ITEM_ACTIVE, 0);
		return "homePage";
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String userManagementPage(ModelMap modelMap, Pageable pageable) {
		String currentUser = securityAccessor.getCurrentUsername();
		modelMap.addAttribute(NAVBAR_ITEM_ACTIVE, 1);
		modelMap.addAttribute("roles", userService.loadRoles());
		modelMap.addAttribute("users", userService.findAllExcludingCurrentUser(pageable, currentUser));
		modelMap.addAttribute("userModel", userService.buildEmptyUserModel());
		return "user/users";
	}

	@RequestMapping(value = "/consultants", method = RequestMethod.GET)
	public String consultantManagementPage(ModelMap modelMap, Pageable pageable) {
		Pageable customPageableRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.ASC, "lastName", "firstName");
		modelMap.addAttribute(NAVBAR_ITEM_ACTIVE, 2);
		modelMap.addAttribute("consultants", consultantService.showAllConsultants(customPageableRequest));
		return "consultant/consultants";
	}

}
