package it.f2informatica.core.user;

import it.f2informatica.core.model.RoleModel;
import it.f2informatica.core.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

	UserModel findUserById(String userId);

	UserModel findByUsername(String username);

	UserModel findByUsernameAndPassword(String username, String password);

	Page<UserModel> findAllExcludingCurrentUser(Pageable pageable, String usernameToExclude);

	Iterable<UserModel> findUsersByRoleName(String roleName);

	UserModel saveUser(UserModel user);

	boolean updateUser(UserModel userRequest);

	void deleteUser(String userId);

	Iterable<RoleModel> loadRoles();

	RoleModel findRoleByName(String roleName);

	UserModel buildEmptyUserModel();
}
