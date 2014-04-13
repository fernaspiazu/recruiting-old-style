package it.f2informatica.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthenticationController {

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String loginPage() {
    return "login/login";
  }

  @RequestMapping(value = "/login_failed", method = RequestMethod.GET)
  public String loginFailed(ModelMap modelMap) {
    modelMap.addAttribute("hasErrors", true);
    return "login/login";
  }

}
