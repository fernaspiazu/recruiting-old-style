package it.f2informatica.services.domain.user.impl;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import it.f2informatica.mongodb.domain.Role;
import it.f2informatica.mongodb.domain.User;
import it.f2informatica.mongodb.repositories.RoleRepository;
import it.f2informatica.mongodb.repositories.UserRepository;
import it.f2informatica.services.domain.user.UserService;
import it.f2informatica.services.requests.UserRequest;
import it.f2informatica.services.responses.RoleResponse;
import it.f2informatica.services.responses.UserResponse;
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
	public UserResponse findUserById(String userId) {
		return transformUserToUserResponse().apply(userRepository.findOne(userId));
	}

	@Override
	public UserResponse findByUsername(String username) {
		return transformUserToUserResponse().apply(userRepository.findByUsername(username));
	}

	@Override
	public UserResponse findByUsernameAndPassword(String username, String password) {
		return transformUserToUserResponse().apply(userRepository.findByUsernameAndPassword(username, password));
	}

	@Override
	public Page<UserResponse> findAll(Pageable pageable) {
		return new PageImpl<>(Lists.newArrayList(
				Iterables.transform(userRepository.findAll(pageable), transformUserToUserResponse())
		));
	}

	@Override
	public Iterable<UserResponse> findUsersByRoleName(String roleName) {
		return Iterables.transform(userRepository.findByRoleName(roleName), transformUserToUserResponse());
	}

	@Override
	public UserResponse saveUser(UserRequest request) {
		User newUser = userRepository.save(user()
				.withUsername(request.getUsername())
				.withPassword(request.getPassword())
				.withRole(roleRepository.findOne(request.getRoleId()))
				.thatIsRemovable()
				.build());
		return transformUserToUserResponse().apply(newUser);
	}

	private static Function<User, UserResponse> transformUserToUserResponse() {
		return new Function<User, UserResponse>() {
			@Override
			public UserResponse apply(User input) {
				UserResponse userResponse = new UserResponse();
				// After saving document "id" field can be of ObjectId type
				userResponse.setUserId(String.valueOf(input.getId()));
				userResponse.setUsername(input.getUsername());
				userResponse.setNotRemovable(input.isNotRemovable());
				userResponse.setAuthorization(input.getRole().getName());
				return userResponse;
			}
		};
	}

	@Override
	public boolean updateUser(UserRequest request) {
		return mongoTemplate.updateFirst(
				query(where("id").is(request.getUserId())),
				update("username", request.getUsername())
					.set("role", roleRepository.findOne(request.getRoleId())),
				User.class
		).getLastError().ok();
	}

	@Override
	public void deleteUser(String userId) {
		userRepository.deleteRemovableUser(userId);
	}

	@Override
	public Iterable<RoleResponse> loadRoles() {
		return Iterables.transform(roleRepository.findAll(), transformRoleToRoleResponse());
	}

	@Override
	public RoleResponse findRoleByName(String roleName) {
		return transformRoleToRoleResponse().apply(roleRepository.findByName(roleName));
	}

	private static Function<Role, RoleResponse> transformRoleToRoleResponse() {
		return new Function<Role, RoleResponse>() {
			@Override
			public RoleResponse apply(Role input) {
				RoleResponse roleResponse = new RoleResponse();
				roleResponse.setRoleId(input.getId());
				roleResponse.setRoleName(input.getName());
				return roleResponse;
			}
		};
	}

}
