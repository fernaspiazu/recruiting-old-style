package it.f2informatica.webapp.controller;

import it.f2informatica.services.model.UserModel;
import it.f2informatica.services.model.UpdatePasswordModel;
import it.f2informatica.services.user.PasswordUpdaterService;
import it.f2informatica.services.user.UserService;
import it.f2informatica.services.validator.UpdatePasswordModelValidator;
import it.f2informatica.services.validator.UserModelValidator;
import it.f2informatica.services.validator.utils.ValidationResponse;
import it.f2informatica.services.validator.utils.ValidationResponseService;
import it.f2informatica.webapp.utils.CurrentHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private CurrentHttpRequest httpRequest;

	@Autowired
	private UserModelValidator userModelValidator;

	@Autowired
	private PasswordUpdaterService passwordUpdaterService;

	@Autowired
	private ValidationResponseService validationResponseService;

	@Autowired
	private UpdatePasswordModelValidator updatePasswordModelValidator;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("userModel") UserModel userModel) {
		userService.saveUser(userModel);
		return "redirect:/users";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody UserModel editUser(@RequestParam String userId) {
		return userService.findUserById(userId);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateUser(@ModelAttribute("userModel") UserModel userModel) {
		userService.updateUser(userModel);
		return "redirect:/users";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deleteUser(@RequestParam String userId) {
		userService.deleteUser(userId);
		return "redirect:/users";
	}

	@RequestMapping(value = "/validateUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ValidationResponse validateUser(@ModelAttribute("userModel") UserModel userModel, BindingResult result) {
		userModelValidator.validate(userModel, result);
		if (result.hasErrors()) {
			return validationResponseService.validationFail(result, httpRequest.getLocale());
		}
		return validationResponseService.validationSuccess();
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public String changePasswordForm(@RequestParam("userId") String userId, ModelMap model) {
		UserModel user = userService.findUserById(userId);
		model.addAttribute("readonlyUsername", user.getUsername());
		model.addAttribute("changePasswordModel", passwordUpdaterService.prepareUpdatePasswordModel(userId));
		return "user/changePasswordForm";
	}

	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	public String updatePassword(@ModelAttribute("changePasswordModel") UpdatePasswordModel updatePasswordModel) {
		passwordUpdaterService.updatePassword(updatePasswordModel);
		return "redirect:/users";
	}

	@RequestMapping(value = "/validatePasswordUpdating", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ValidationResponse validatePasswordUpdating(@ModelAttribute("changePasswordModel") UpdatePasswordModel updatePasswordModel, BindingResult result) {
		updatePasswordModelValidator.validate(updatePasswordModel, result);
		if (result.hasErrors()) {
			return validationResponseService.validationFail(result, httpRequest.getLocale());
		}
		return validationResponseService.validationSuccess();
	}

}
