package it.f2informatica.services.user.impl;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mongodb.WriteResult;
import it.f2informatica.mongodb.domain.User;
import it.f2informatica.mongodb.repositories.RoleRepository;
import it.f2informatica.mongodb.repositories.UserRepository;
import it.f2informatica.services.requests.ChangePasswordRequest;
import it.f2informatica.services.requests.UserRequest;
import it.f2informatica.services.responses.LoginResponse;
import it.f2informatica.services.responses.RoleResponse;
import it.f2informatica.services.responses.UserResponse;
import it.f2informatica.services.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

@Service
public class UserServiceImpl implements UserService {
	private static final Logger log = Logger.getLogger(UserServiceImpl.class);

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public LoginResponse processLogin(String username) {
		User user = userRepository.findByUsername(username);
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setUsername(user.getUsername());
		loginResponse.setPassword(user.getPassword());
		loginResponse.setAuthorization(user.getRole().getName());
		return loginResponse;
	}

	@Override
	public UserResponse findUserById(String userId) {
		return userToUserResponseFunction.apply(userRepository.findOne(userId));
	}

	@Override
	public UserResponse findByUsername(String username) {
		return userToUserResponseFunction.apply(userRepository.findByUsername(username));
	}

	@Override
	public UserResponse findByUsernameAndPassword(String username, String password) {
		return userToUserResponseFunction.apply(userRepository.findByUsernameAndPassword(username, password));
	}

	@Override
	public Page<UserResponse> findAll(Pageable pageable) {
		return new PageImpl<>(Lists.newArrayList(
				Iterables.transform(userRepository.findAll(pageable), userToUserResponseFunction)
		));
	}

	@Override
	public Iterable<UserResponse> findUsersByRoleName(String roleName) {
		return Iterables.transform(userRepository.findByRoleName(roleName), userToUserResponseFunction);
	}

	@Override
	public Iterable<RoleResponse> loadRoles() {
		return Iterables.transform(roleRepository.findAll(), roleToRoleResponseFunction);
	}

	@Override
	public RoleResponse findRoleByName(String roleName) {
		return roleToRoleResponseFunction.apply(roleRepository.findByName(roleName));
	}

	@Override
	public void deleteUser(String userId) {
		log.info("deleting >>> [userID:" + userId + "]");
		userRepository.deleteRemovableUser(userId);
	}

	@Override
	public UserResponse saveUser(UserRequest userRequest) {
		log.info("saving >>> [username:" + userRequest.getUsername() + "]");
		User user = new User();
		user.setUsername(userRequest.getUsername());
		user.setPassword(userRequest.getPassword());
		user.setRemovable(userRequest.isRemovable());
		user.setRole(roleRepository.findOne(userRequest.getRoleId()));
		return userToUserResponseFunction.apply(userRepository.save(user));
	}

	@Override
	public boolean updateUser(UserRequest userRequest) {
		final String userId = userRequest.getUserId();
		log.info("updating >>> [userID:" + userId + "]");
		WriteResult writeResult = mongoTemplate.updateFirst(
				query(where("id").is(userId)),
				update("username", userRequest.getUsername())
					.set("role", roleRepository.findOne(userRequest.getRoleId())),
				User.class
		);
		return writeResult.getLastError().ok();
	}

	@Override
	public boolean changePassword(ChangePasswordRequest request) {
		final String userId = request.getUserId();
		log.info("changing password >>> [userId:" + userId + "]");
		if (isNewPasswordConfirmedCorrectly(request)) {
			WriteResult writeResult = mongoTemplate.updateFirst(
					query(where("id").is(userId).and("password").is(request.getCurrentPassword())),
					update("password", request.getNewPassword()),
					User.class
			);
			return writeResult.getLastError().ok();
		}
		log.warn("password was not changed correctly >>> [userId:" + request + "]");
		return false;
	}

	private boolean isNewPasswordConfirmedCorrectly(ChangePasswordRequest request) {
		return StringUtils.hasText(request.getNewPassword())
				&& StringUtils.hasText(request.getPasswordConfirmed())
				&& request.getNewPassword().equals(request.getPasswordConfirmed());
	}

}
