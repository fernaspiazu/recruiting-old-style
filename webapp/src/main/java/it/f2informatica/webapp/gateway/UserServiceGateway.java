package it.f2informatica.webapp.gateway;

import it.f2informatica.services.domain.user.UserService;
import it.f2informatica.services.model.RoleModel;
import it.f2informatica.services.model.UserModel;
import it.f2informatica.webapp.security.SecurityAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static it.f2informatica.services.model.builder.UserModelBuilder.userModel;

@Service
public class UserServiceGateway {

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityAccessor securityAccessor;

	public UserModel getAuthenticatedUser(String username) {
		return userService.findByUsername(username);
	}

	public Iterable<RoleModel> loadRoles() {
		return userService.loadRoles();
	}

	public Page<UserModel> findAllUsers(Pageable pageable) {
		return userService.findAllExcludingCurrentUser(pageable, securityAccessor.getCurrentUsername());
	}

	public UserModel findUserById(String userId) {
		return userService.findUserById(userId);
	}

	public UserModel saveUser(UserModel userModel) {
		return userService.saveUser(userModel);
	}

	public boolean updateUser(UserModel userModel) {
		return userService.updateUser(userModel);
	}

	public void deleteUser(String userId) {
		userService.deleteUser(userId);
	}

	public UserModel prepareNewUserModel() {
		return userModel().build();
	}

	public UserModel prepareUpdatingUserModel(String userId) {
		UserModel userModel = userService.findUserById(userId);
		RoleModel role = userService.findRoleByName(userModel.getRole().getRoleName());
		return userModel()
				.withId(userModel.getUserId())
				.withUsername(userModel.getUsername())
				.withRole(role)
				.build();
	}

}
