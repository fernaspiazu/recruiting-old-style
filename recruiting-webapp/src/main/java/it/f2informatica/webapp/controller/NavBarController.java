package it.f2informatica.webapp.controller;

import it.f2informatica.core.model.ConsultantModel;
import it.f2informatica.core.model.query.ConsultantSearchCriteria;
import it.f2informatica.core.services.ConsultantService;
import it.f2informatica.core.services.UserService;
import it.f2informatica.webapp.security.SecurityAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({SessionAttribute.NAVBAR_ITEM_ACTIVE, SessionAttribute.ROLES})
public class NavBarController {

  @Autowired
  private UserService userService;

  @Autowired
  private SecurityAccessor securityAccessor;

  @Autowired
  private ConsultantService consultantService;

  @RequestMapping(value = "/home", method = RequestMethod.GET)
  public String homePage(ModelMap model) {
    model.addAttribute(SessionAttribute.NAVBAR_ITEM_ACTIVE, 0);
    return "homePage";
  }

  @RequestMapping(value = {"/user", "/users"}, method = RequestMethod.GET)
  public String userManagementPage(ModelMap model, Pageable pageable) {
    String currentUser = securityAccessor.getCurrentUsername();
    model.addAttribute(SessionAttribute.NAVBAR_ITEM_ACTIVE, 1);
    model.addAttribute(SessionAttribute.ROLES, userService.loadRoles());
    model.addAttribute("users", userService.findAllExcludingCurrentUser(pageable, currentUser));
    model.addAttribute("userModel", userService.buildEmptyUserModel());
    return "user/users";
  }

  @RequestMapping(value = {"/consultant", "/consultants"}, method = RequestMethod.GET)
  public String consultantManagementPage(ModelMap model, Pageable pageable) {
    Pageable pageRequest = new PageRequest(pageable.getPageNumber(), 5, Sort.Direction.DESC, "registrationDate");
    model.addAttribute("page", consultantService.paginateConsultants(pageRequest));
    model.addAttribute("searchCriteria", new ConsultantSearchCriteria());
    model.addAttribute(SessionAttribute.NAVBAR_ITEM_ACTIVE, 2);
    return "consultant/consultants";
  }

  @RequestMapping(value = "/consultant/new-consultant", method = RequestMethod.GET)
  public String createConsultant(ModelMap model) {
    model.addAttribute(SessionAttribute.NAVBAR_ITEM_ACTIVE, 2);
    model.addAttribute("consultantModel", consultantService.buildNewConsultantModel());
    return "consultant/consultantForm";
  }

}
