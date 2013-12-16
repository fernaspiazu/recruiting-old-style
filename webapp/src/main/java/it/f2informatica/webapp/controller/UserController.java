package it.f2informatica.webapp.controller;

import it.f2informatica.services.domain.user.PasswordUpdaterService;
import it.f2informatica.services.domain.user.UserService;
import it.f2informatica.services.model.RoleModel;
import it.f2informatica.services.model.UserModel;
import it.f2informatica.services.requests.UpdatePasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordUpdaterService passwordUpdaterService;

	@ModelAttribute("roles")
	public Iterable<RoleModel> loadRoles() {
		return userService.loadRoles();
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String createNewUser(ModelMap model) {
		model.addAttribute("userModel", userService.buildEmptyUserModel());
		return "user/createNewUser";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("userModel") UserModel userModel) {
		userService.saveUser(userModel);
		return "redirect:/user";
	}

	@RequestMapping(value = "/edit/{userId}", method = RequestMethod.GET)
	public String editUser(@PathVariable String userId, ModelMap model) {
		model.addAttribute("userModel", userService.findUserById(userId));
		return "user/userEdit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateUser(@ModelAttribute("userModel") UserModel userModel) {
		userService.updateUser(userModel);
		return "redirect:/user";
	}

	@RequestMapping(value = "/delete/{userId}", method = RequestMethod.GET)
	public String deleteUser(@PathVariable String userId) {
		userService.deleteUser(userId);
		return "redirect:/user";
	}

	@RequestMapping(value = "/changePassword/{userId}", method = RequestMethod.GET)
	public String changePasswordForm(@PathVariable String userId, ModelMap model) {
		model.addAttribute("changePasswordModel", passwordUpdaterService.prepareUpdatePasswordRequest(userId));
		return "user/changePasswordForm";
	}

	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	public String updatePassword(@ModelAttribute("changePasswordModel") UpdatePasswordRequest request) {
		passwordUpdaterService.updatePassword(request);
		return "redirect:/user";
	}

}
