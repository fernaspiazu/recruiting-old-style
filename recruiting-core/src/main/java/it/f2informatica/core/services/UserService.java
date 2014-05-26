package it.f2informatica.core.services;

import com.google.common.base.Optional;
import it.f2informatica.core.model.RoleModel;
import it.f2informatica.core.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

  Optional<UserModel> findUserById(String userId);

  Optional<UserModel> findByUsername(String username);

  Optional<UserModel> findByUsernameAndPassword(String username, String password);

  Page<UserModel> findAllExcludingCurrentUser(Pageable pageable, String usernameToExclude);

  Iterable<UserModel> findUsersByRoleName(String roleName);

  UserModel saveUser(UserModel user);

  void updateUser(UserModel userRequest);

  void deleteUser(String userId);

  Iterable<RoleModel> loadRoles();

  Optional<RoleModel> findRoleByName(String roleName);

  UserModel buildEmptyUserModel();
}
