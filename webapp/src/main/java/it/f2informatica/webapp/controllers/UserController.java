package it.f2informatica.webapp.controllers;

import it.f2informatica.mongodb.MongoDataEvent;
import it.f2informatica.services.requests.ChangePasswordRequest;
import it.f2informatica.services.requests.UserRequest;
import it.f2informatica.services.responses.RoleResponse;
import it.f2informatica.webapp.gateway.UserServiceGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserController {

	private UserServiceGateway userServiceGateway;

	@Autowired
	public void setUserServiceGateway(UserServiceGateway userServiceGateway) {
		this.userServiceGateway = userServiceGateway;
	}

	@ModelAttribute("roles")
	public Iterable<RoleResponse> roles() {
		return userServiceGateway.loadRoles();
	}

	@RequestMapping(value = "/loadUsers", method = RequestMethod.GET)
	public String loadUsers(ModelMap modelMap, Pageable pageable) {
		modelMap.addAttribute("users", userServiceGateway.findAllUsers(pageable));
		return "user/users";
	}

	@RequestMapping(value = "/findUser/{userId}", method = RequestMethod.GET)
	public String findUser(@PathVariable String userId, ModelMap modelMap) {
		modelMap.addAttribute("readOnly", true)
						.addAttribute("operation", MongoDataEvent.DETAIL.toString())
						.addAttribute("userModel", userServiceGateway.findUserById(userId));
		return "user/userDetails";
	}

	@RequestMapping(value = "/createNewUser", method = RequestMethod.GET)
	public String createNewUser(ModelMap modelMap) {
		modelMap.addAttribute("operation", MongoDataEvent.SAVE.toString())
						.addAttribute("userModel", userServiceGateway.prepareNewUserToSave());
		return "user/createNewUser";
	}

	@RequestMapping(value = "/editUser/{userId}", method = RequestMethod.GET)
	public String editUser(@PathVariable String userId, ModelMap modelMap) {
		modelMap.addAttribute("operation", MongoDataEvent.EDIT.toString())
						.addAttribute("userModel", userServiceGateway.prepareUserToUpdate(userId));
		return "user/userEdit";
	}

	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("userModel") UserRequest userRequest) {
		userServiceGateway.saveUser(userRequest);
		return "redirect:/user/loadUsers";
	}

	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public String updateUser(@ModelAttribute("userModel") UserRequest userRequest) {
		userServiceGateway.updateUser(userRequest);
		return "redirect:/user/loadUsers";
	}

	@RequestMapping(value = "/deleteUser/{userId}", method = RequestMethod.GET)
	public String deleteUser(@PathVariable String userId) {
		userServiceGateway.deleteUser(userId);
		return "redirect:/user/loadUsers";
	}

	@RequestMapping(value = "/changePassword/{userId}", method = RequestMethod.GET)
	public String changePasswordForm(@PathVariable String userId, ModelMap modelMap) {
		modelMap.addAttribute("changePasswordModel", userServiceGateway.prepareChangePasswordRequest(userId));
		return "user/changePasswordForm";
	}

	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	public String updatePassword(@ModelAttribute("changePasswordModel") ChangePasswordRequest request) {
		userServiceGateway.updatePassword(request);
		return "redirect:/user/loadUsers";
	}

}
