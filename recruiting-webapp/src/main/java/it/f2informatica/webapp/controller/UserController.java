package it.f2informatica.webapp.controller;

import it.f2informatica.services.model.UpdatePasswordModel;
import it.f2informatica.services.model.UserModel;
import it.f2informatica.services.user.PasswordUpdaterService;
import it.f2informatica.services.user.UserService;
import it.f2informatica.services.validator.UpdatePasswordModelValidator;
import it.f2informatica.services.validator.UserModelValidator;
import it.f2informatica.services.validator.utils.ValidationResponse;
import it.f2informatica.services.validator.utils.ValidationResponseService;
import it.f2informatica.webapp.utils.CurrentHttpRequest;
import it.f2informatica.webapp.utils.MediaTypeUTF8;
import org.springframework.beans.factory.annotation.Autowired;
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

	@RequestMapping(value = "/edit", method = RequestMethod.GET, produces = MediaTypeUTF8.JSON_UTF_8)
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

	@RequestMapping(value = "/validate-user", method = RequestMethod.POST, produces = MediaTypeUTF8.JSON_UTF_8)
	public @ResponseBody ValidationResponse validateUser(@ModelAttribute("userModel") UserModel userModel, BindingResult result) {
		userModelValidator.validate(userModel, result);
		if (result.hasErrors()) {
			return validationResponseService.validationFail(result, httpRequest.getLocale());
		}
		return validationResponseService.validationSuccess();
	}

	@RequestMapping(value = "/change-password", method = RequestMethod.GET)
	public String changePasswordForm(@RequestParam("userId") String userId, ModelMap model) {
		UserModel user = userService.findUserById(userId);
		model.addAttribute("readonlyUsername", user.getUsername());
		model.addAttribute("changePasswordModel", passwordUpdaterService.prepareUpdatePasswordModel(userId));
		return "user/changePasswordForm";
	}

	@RequestMapping(value = "/update-password", method = RequestMethod.POST)
	public String updatePassword(@ModelAttribute("changePasswordModel") UpdatePasswordModel updatePasswordModel) {
		passwordUpdaterService.updatePassword(updatePasswordModel);
		return "redirect:/users";
	}

	@RequestMapping(value = "/validate-password-updating", method = RequestMethod.POST, produces = MediaTypeUTF8.JSON_UTF_8)
	public @ResponseBody ValidationResponse validatePasswordUpdating(@ModelAttribute("changePasswordModel") UpdatePasswordModel updatePasswordModel, BindingResult result) {
		updatePasswordModelValidator.validate(updatePasswordModel, result);
		if (result.hasErrors()) {
			return validationResponseService.validationFail(result, httpRequest.getLocale());
		}
		return validationResponseService.validationSuccess();
	}

}
