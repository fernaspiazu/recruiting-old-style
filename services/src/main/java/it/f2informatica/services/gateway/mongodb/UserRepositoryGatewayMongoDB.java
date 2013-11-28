package it.f2informatica.services.gateway.mongodb;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import it.f2informatica.mongodb.domain.Role;
import it.f2informatica.mongodb.domain.User;
import it.f2informatica.mongodb.repositories.RoleRepository;
import it.f2informatica.mongodb.repositories.UserRepository;
import it.f2informatica.services.gateway.EntityToModelConverter;
import it.f2informatica.services.gateway.UserRepositoryGateway;
import it.f2informatica.services.model.RoleModel;
import it.f2informatica.services.model.UserModel;
import it.f2informatica.services.requests.UpdatePasswordRequest;
import it.f2informatica.services.responses.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

	@Autowired
	@Qualifier("userToModelConverter")
	private EntityToModelConverter<User, UserModel> userToModelConverter;

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
		return userToModelConverter.convert(userRepo.findOne(userId));
	}

	@Override
	public UserModel findByUsername(String username) {
		return userToModelConverter.convert(userRepo.findByUsername(username));
	}

	@Override
	public UserModel findByUsernameAndPassword(String username, String password) {
		return userToModelConverter.convert(userRepo.findByUsernameAndPassword(username, password));
	}

	@Override
	public Page<UserModel> findAllExcludingCurrentUser(Pageable pageable, String usernameToExclude) {
		return new PageImpl<>(Lists.newArrayList(
			userToModelConverter.convertIterable(userRepo.findAllExcludingUser(pageable, usernameToExclude))
		));
	}

	@Override
	public Iterable<UserModel> findUsersByRoleName(String roleName) {
		return userToModelConverter.convertIterable(userRepo.findByRoleName(roleName));
	}

	@Override
	public UserModel saveUser(UserModel userModel) {
		User newUser = userRepo.save(user()
			.withUsername(userModel.getUsername())
			.withPassword(userModel.getPassword())
			.withRole(roleRepo.findOne(userModel.getRole().getRoleId()))
			.thatIsRemovable()
			.build());
		return userToModelConverter.convert(newUser);
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
