package it.f2informatica.services.gateway;

import it.f2informatica.services.model.RoleModel;
import it.f2informatica.services.model.UpdatePasswordModel;
import it.f2informatica.services.model.UserModel;
import it.f2informatica.services.responses.AuthenticationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryGateway {

	AuthenticationResponse authenticationByUsername(String username);

	boolean updatePassword(UpdatePasswordModel request);

	UserModel findUserById(String userId);

	UserModel findByUsername(String username);

	UserModel findByUsernameAndPassword(String username, String password);

	Page<UserModel> findAllExcludingCurrentUser(Pageable pageable, String usernameToExclude);

	Iterable<UserModel> findUsersByRoleName(String roleName);

	UserModel saveUser(UserModel userModel);

	boolean updateUser(UserModel userModel);

	void deleteUser(String userId);

	Iterable<RoleModel> loadRoles();

	RoleModel findRoleByName(String roleName);

	boolean isCurrentPasswordValid(String userId, String currentPwd);
}
