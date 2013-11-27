package it.f2informatica.services.gateway.mongodb;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import it.f2informatica.mongodb.domain.Role;
import it.f2informatica.mongodb.domain.User;
import it.f2informatica.mongodb.repositories.RoleRepository;
import it.f2informatica.mongodb.repositories.UserRepository;
import it.f2informatica.services.gateway.UserRepositoryGateway;
import it.f2informatica.services.model.RoleModel;
import it.f2informatica.services.model.UserModel;
import it.f2informatica.services.requests.UpdatePasswordRequest;
import it.f2informatica.services.responses.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import static it.f2informatica.mongodb.domain.builder.UserBuilder.user;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

@Service
public class UserRepositoryGatewayMongoDB implements UserRepositoryGateway {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private PasswordUpdater passwordUpdater;

	@Override
	public AuthenticationResponse authenticationByUsername(String username) {
		User user = userRepo.findByUsername(username);
		AuthenticationResponse authenticationResponse = new AuthenticationResponse();
		authenticationResponse.setUsername(user.getUsername());
		authenticationResponse.setPassword(user.getPassword());
		authenticationResponse.setAuthorization(user.getRole().getName());
		return authenticationResponse;
	}

	@Override
	public boolean updatePassword(UpdatePasswordRequest request) {
		return passwordUpdater.updatePassword(
			request.getUserId(),
			request.getCurrentPassword(),
			request.getNewPassword(),
			request.getPasswordConfirmed());
	}

	@Override
	public UserModel findUserById(String userId) {
		return userToUserModel().apply(userRepo.findOne(userId));
	}

	@Override
	public UserModel findByUsername(String username) {
		return userToUserModel().apply(userRepo.findByUsername(username));
	}

	@Override
	public UserModel findByUsernameAndPassword(String username, String password) {
		return userToUserModel().apply(userRepo.findByUsernameAndPassword(username, password));
	}

	@Override
	public Page<UserModel> findAllExcludingCurrentUser(Pageable pageable, String usernameToExclude) {
		return new PageImpl<>(Lists.newArrayList(Iterables.transform(
			userRepo.findAllExcludingUser(pageable, usernameToExclude), userToUserModel()
		)));
	}

	@Override
	public Iterable<UserModel> findUsersByRoleName(String roleName) {
		return Iterables.transform(userRepo.findByRoleName(roleName), userToUserModel());
	}

	@Override
	public UserModel saveUser(UserModel userModel) {
		User newUser = userRepo.save(user()
			.withUsername(userModel.getUsername())
			.withPassword(userModel.getPassword())
			.withRole(roleRepo.findOne(userModel.getRole().getRoleId()))
			.thatIsRemovable()
			.build());
		return userToUserModel().apply(newUser);
	}

	@Override
	public boolean updateUser(UserModel userModel) {
		return mongoTemplate.updateFirst(
			query(where("id").is(userModel.getUserId())),
			update("username", userModel.getUsername())
				.set("role", roleRepo.findOne(userModel.getRole().getRoleId())),
			User.class
		).getLastError().ok();
	}

	@Override
	public void deleteUser(String userId) {
		userRepo.deleteRemovableUser(userId);
	}

	@Override
	public Iterable<RoleModel> loadRoles() {
		return Iterables.transform(roleRepo.findAll(), roleToRoleModel());
	}

	@Override
	public RoleModel findRoleByName(String roleName) {
		return roleToRoleModel().apply(roleRepo.findByName(roleName));
	}

	private Function<User, UserModel> userToUserModel() {
		return new Function<User, UserModel>() {
			@Override
			public UserModel apply(User user) {
				UserModel model = new UserModel();
				// After saving document "id" field can be of ObjectId type
				model.setUserId(String.valueOf(user.getId()));
				model.setUsername(user.getUsername());
				model.setNotRemovable(user.isNotRemovable());
				model.setRole(roleToRoleModel().apply(user.getRole()));
				return model;
			}
		};
	}

	private Function<Role, RoleModel> roleToRoleModel() {
		return new Function<Role, RoleModel>() {
			@Override
			public RoleModel apply(Role role) {
				RoleModel model = new RoleModel();
				model.setRoleId(role.getId());
				model.setRoleName(role.getName());
				return model;
			}
		};
	}

}
