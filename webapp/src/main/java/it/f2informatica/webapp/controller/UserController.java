package it.f2informatica.webapp.controller;

import it.f2informatica.services.model.RoleModel;
import it.f2informatica.services.model.UserModel;
import it.f2informatica.services.requests.UpdatePasswordRequest;
import it.f2informatica.webapp.gateway.PasswordUpdaterServiceGateway;
import it.f2informatica.webapp.gateway.UserServiceGateway;
import it.f2informatica.webapp.validator.RegistrationUserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserServiceGateway userServiceGateway;

	@Autowired
	private PasswordUpdaterServiceGateway passwordUpdaterServiceGateway;

	@Autowired
	private RegistrationUserValidator userValidator;

	@ModelAttribute("roles")
	public Iterable<RoleModel> loadRoles() {
		return userServiceGateway.loadRoles();
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String createNewUser(ModelMap modelMap) {
		modelMap.addAttribute("userModel", userServiceGateway.prepareNewUserModel());
		return "user/createNewUser";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("userModel") UserModel userModel, BindingResult bindingResult) {
		if (hasUserModelErrors(userModel, bindingResult)) {
			return "user/createNewUser";
		}
		userServiceGateway.saveUser(userModel);
		return "redirect:/user";
	}

	@RequestMapping(value = "/edit/{userId}", method = RequestMethod.GET)
	public String editUser(@PathVariable String userId, ModelMap modelMap) {
		modelMap.addAttribute("userModel", userServiceGateway.findUserById(userId));
		return "user/userEdit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateUser(@ModelAttribute("userModel") UserModel userModel, BindingResult bindingResult) {
		if (hasUserModelErrors(userModel, bindingResult)) {
			return "user/userEdit";
		}
		userServiceGateway.updateUser(userModel);
		return "redirect:/user";
	}

	@RequestMapping(value = "/delete/{userId}", method = RequestMethod.GET)
	public String deleteUser(@PathVariable String userId) {
		userServiceGateway.deleteUser(userId);
		return "redirect:/user";
	}

	@RequestMapping(value = "/changePassword/{userId}", method = RequestMethod.GET)
	public String changePasswordForm(@PathVariable String userId, ModelMap modelMap) {
		modelMap.addAttribute("changePasswordModel", passwordUpdaterServiceGateway.prepareUpdatePasswordRequest(userId));
		return "user/changePasswordForm";
	}

	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	public String updatePassword(@ModelAttribute("changePasswordModel") UpdatePasswordRequest request) {
		passwordUpdaterServiceGateway.updatePassword(request);
		return "redirect:/user";
	}

	private boolean hasUserModelErrors(UserModel userModel, BindingResult bindingResult) {
		userValidator.validate(userModel, bindingResult);
		return bindingResult.hasErrors();
	}

}
