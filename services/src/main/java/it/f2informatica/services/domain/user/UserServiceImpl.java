package it.f2informatica.services.domain.user;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import it.f2informatica.mongodb.domain.Role;
import it.f2informatica.mongodb.domain.User;
import it.f2informatica.mongodb.repositories.RoleRepository;
import it.f2informatica.mongodb.repositories.UserRepository;
import it.f2informatica.services.model.RoleModel;
import it.f2informatica.services.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import static it.f2informatica.mongodb.domain.builders.UserBuilder.user;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public UserModel findUserById(String userId) {
		return transformUserToUserResponse().apply(userRepository.findOne(userId));
	}

	@Override
	public UserModel findByUsername(String username) {
		return transformUserToUserResponse().apply(userRepository.findByUsername(username));
	}

	@Override
	public UserModel findByUsernameAndPassword(String username, String password) {
		return transformUserToUserResponse().apply(userRepository.findByUsernameAndPassword(username, password));
	}

	@Override
	public Page<UserModel> findAllExcludingCurrentUser(Pageable pageable, String usernameToExclude) {
		return new PageImpl<>(Lists.newArrayList(
				Iterables.transform(
						userRepository.findAllExcludingUser(pageable, usernameToExclude),
						transformUserToUserResponse())
		));
	}

	@Override
	public Iterable<UserModel> findUsersByRoleName(String roleName) {
		return Iterables.transform(userRepository.findByRoleName(roleName), transformUserToUserResponse());
	}

	@Override
	public UserModel saveUser(UserModel userModel) {
		User newUser = userRepository.save(user()
				.withUsername(userModel.getUsername())
				.withPassword(userModel.getPassword())
				.withRole(roleRepository.findOne(userModel.getRole().getRoleId()))
				.thatIsRemovable()
				.build());
		return transformUserToUserResponse().apply(newUser);
	}

	private static Function<User, UserModel> transformUserToUserResponse() {
		return new Function<User, UserModel>() {
			@Override
			public UserModel apply(User user) {
				UserModel model = new UserModel();
				// After saving document "id" field can be of ObjectId type
				model.setUserId(String.valueOf(user.getId()));
				model.setUsername(user.getUsername());
				model.setNotRemovable(user.isNotRemovable());
				model.setRole(transformRoleToRoleResponse().apply(user.getRole()));
				return model;
			}
		};
	}

	@Override
	public boolean updateUser(UserModel userModel) {
		return mongoTemplate.updateFirst(
				query(where("id").is(userModel.getUserId())),
				update("username", userModel.getUsername())
					.set("role", roleRepository.findOne(userModel.getRole().getRoleId())),
				User.class
		).getLastError().ok();
	}

	@Override
	public void deleteUser(String userId) {
		userRepository.deleteRemovableUser(userId);
	}

	@Override
	public Iterable<RoleModel> loadRoles() {
		return Iterables.transform(roleRepository.findAll(), transformRoleToRoleResponse());
	}

	@Override
	public RoleModel findRoleByName(String roleName) {
		return transformRoleToRoleResponse().apply(roleRepository.findByName(roleName));
	}

	private static Function<Role, RoleModel> transformRoleToRoleResponse() {
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
