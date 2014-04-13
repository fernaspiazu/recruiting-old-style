package it.f2informatica.core.services;

import it.f2informatica.core.gateway.UserRepositoryGateway;
import it.f2informatica.core.model.RoleModel;
import it.f2informatica.core.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static it.f2informatica.core.model.builder.UserModelBuilder.userModel;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepositoryGateway userRepositoryGateway;

	@Override
	public UserModel findUserById(String userId) {
		return userRepositoryGateway.findUserById(userId);
	}

	@Override
	public UserModel findByUsername(String username) {
		return userRepositoryGateway.findByUsername(username);
	}

	@Override
	public UserModel findByUsernameAndPassword(String username, String password) {
		return userRepositoryGateway.findByUsernameAndPassword(username, password);
	}

	@Override
	public Page<UserModel> findAllExcludingCurrentUser(Pageable pageable, String usernameToExclude) {
		return userRepositoryGateway.findAllExcludingCurrentUser(pageable, usernameToExclude);
	}

	@Override
	public Iterable<UserModel> findUsersByRoleName(String roleName) {
		return userRepositoryGateway.findUsersByRoleName(roleName);
	}

	@Override
	public UserModel saveUser(UserModel userModel) {
		return userRepositoryGateway.saveUser(userModel);
	}

	@Override
	public boolean updateUser(UserModel userModel) {
		return userRepositoryGateway.updateUser(userModel);
	}

	@Override
	public void deleteUser(String userId) {
		userRepositoryGateway.deleteUser(userId);
	}

	@Override
	public Iterable<RoleModel> loadRoles() {
		return userRepositoryGateway.loadRoles();
	}

	@Override
	public RoleModel findRoleByName(String roleName) {
		return userRepositoryGateway.findRoleByName(roleName);
	}

	@Override
	public UserModel buildEmptyUserModel() {
		return userModel().build();
	}

}
