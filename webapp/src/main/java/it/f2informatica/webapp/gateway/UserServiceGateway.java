package it.f2informatica.webapp.gateway;

import it.f2informatica.services.domain.user.UserService;
import it.f2informatica.services.requests.UserRequest;
import it.f2informatica.services.responses.RoleResponse;
import it.f2informatica.services.responses.UserResponse;
import it.f2informatica.webapp.security.SecurityAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static it.f2informatica.services.requests.builders.UserRequestBuilder.userRequest;

@Service
public class UserServiceGateway {

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityAccessor securityAccessor;

	public UserResponse getAuthenticatedUser(String username) {
		return userService.findByUsername(username);
	}

	public Iterable<RoleResponse> loadRoles() {
		return userService.loadRoles();
	}

	public Page<UserResponse> findAllUsers(Pageable pageable) {
		return userService.findAllExcludingCurrentUser(pageable, securityAccessor.getCurrentUsername());
	}

	public UserResponse findUserById(String userId) {
		return userService.findUserById(userId);
	}

	public UserResponse saveUser(UserRequest request) {
		return userService.saveUser(request);
	}

	public boolean updateUser(UserRequest request) {
		return userService.updateUser(request);
	}

	public void deleteUser(String userId) {
		userService.deleteUser(userId);
	}

	public UserRequest prepareNewUserToSave() {
		return userRequest().build();
	}

	public UserRequest prepareUserToUpdate(String userId) {
		UserResponse userResponse = userService.findUserById(userId);
		RoleResponse role = userService.findRoleByName(userResponse.getAuthorization());
		return userRequest()
				.withUserId(userResponse.getUserId())
				.withUsername(userResponse.getUsername())
				.withRoleId(role.getRoleId())
				.build();
	}

}
