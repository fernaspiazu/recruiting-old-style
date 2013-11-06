package it.f2informatica.services.user;

import com.google.common.base.Function;
import it.f2informatica.mongodb.domain.Role;
import it.f2informatica.mongodb.domain.User;
import it.f2informatica.services.requests.ChangePasswordRequest;
import it.f2informatica.services.requests.UserRequest;
import it.f2informatica.services.responses.LoginResponse;
import it.f2informatica.services.responses.RoleResponse;
import it.f2informatica.services.responses.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

	LoginResponse processLogin(String username);

	UserResponse findUserById(String userId);

	UserResponse findByUsername(String username);

	UserResponse findByUsernameAndPassword(String username, String password);

	Page<UserResponse> findAll(Pageable pageable);

	Iterable<UserResponse> findUsersByRoleName(String roleName);

	UserResponse saveUser(UserRequest user);

	boolean updateUser(UserRequest userRequest);

	Iterable<RoleResponse> loadRoles();

	RoleResponse findRoleByName(String roleName);

	void deleteUser(String userId);

	boolean changePassword(ChangePasswordRequest request);

	static final Function<User, UserResponse> userToUserResponseFunction = new Function<User, UserResponse>() {
		@Override
		public UserResponse apply(User input) {
			UserResponse userResponse = new UserResponse();
			// After saving document "id" field can be of ObjectId type
			userResponse.setUserId(String.valueOf(input.getId()));
			userResponse.setUsername(input.getUsername());
			userResponse.setRemovable(input.isRemovable());
			userResponse.setAuthorization(input.getRole().getName());
			return userResponse;
		}
	};

	static Function<Role, RoleResponse> roleToRoleResponseFunction = new Function<Role, RoleResponse>() {
		@Override
		public RoleResponse apply(Role input) {
			RoleResponse roleResponse = new RoleResponse();
			roleResponse.setRoleId(input.getId());
			roleResponse.setRoleName(input.getName());
			return roleResponse;
		}
	};

}
