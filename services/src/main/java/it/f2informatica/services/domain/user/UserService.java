package it.f2informatica.services.domain.user;

import it.f2informatica.services.requests.UserRequest;
import it.f2informatica.services.responses.RoleResponse;
import it.f2informatica.services.responses.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

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

}
