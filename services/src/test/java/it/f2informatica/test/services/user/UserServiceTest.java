package it.f2informatica.test.services.user;

import com.google.common.collect.Lists;
import com.mongodb.CommandResult;
import com.mongodb.WriteResult;
import it.f2informatica.mongodb.domain.Role;
import it.f2informatica.mongodb.domain.User;
import it.f2informatica.mongodb.repositories.RoleRepository;
import it.f2informatica.mongodb.repositories.UserRepository;
import it.f2informatica.services.requests.ChangePasswordRequest;
import it.f2informatica.services.requests.UserRequest;
import it.f2informatica.services.responses.LoginResponse;
import it.f2informatica.services.responses.RoleResponse;
import it.f2informatica.services.responses.UserResponse;
import it.f2informatica.services.user.UserService;
import it.f2informatica.services.user.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@Mock
	private MongoTemplate mongoTemplate;

	@Mock
	private UserRepository userRepository;

	@Mock
	private RoleRepository roleRepository;

	@Mock
	private WriteResult writeResultMock;

	@Mock
	private CommandResult commandResult;

	private UserService userService;

	@Before
	public void setUp() {
		userService = new UserServiceImpl();
		ReflectionTestUtils.setField(userService, "mongoTemplate", mongoTemplate);
		ReflectionTestUtils.setField(userService, "userRepository", userRepository);
		ReflectionTestUtils.setField(userService, "roleRepository", roleRepository);
	}

	@Test
	public void processLogin() {
		when(userRepository.findByUsername("kent82")).thenReturn(createUser("kent82", "kent82", "Admin"));
		LoginResponse userLogged = userService.processLogin("kent82");
		assertThat(userLogged.getUsername()).isEqualTo("kent82");
	}

	@Test
	public void findUserById() {
		when(userRepository.findOne(anyString())).thenReturn(createUser("username2", "password2", "role_admin"));
		UserResponse userResponse = userService.findUserById("1234567890");
		assertThat(userResponse.getUsername()).isEqualTo("username2");
	}

	@Test
	public void findByUsername() {
		when(userRepository.findByUsername(anyString()))
				.thenReturn(createUser("username1", "password1", "role_admin"));
		UserResponse userResponse = userService.findByUsername("username1");
		assertThat(userResponse.getUsername()).isEqualTo("username1");
	}

	@Test
	public void findByUsernameAndPassword() {
		when(userRepository.findByUsernameAndPassword(anyString(), anyString()))
				.thenReturn(createUser("username1", "password1", "role_admin"));
		UserResponse userResponse = userService.findByUsernameAndPassword("username1", "password1");
		assertThat(userResponse.getUsername()).isEqualTo("username1");
	}

	@Test
	public void findAllWithPageable() {
		when(userRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(createUserList().subList(0, 10)));
		Page<UserResponse> users = userService.findAll(new PageRequest(1, 10));
		assertThat(users.getContent()).hasSize(10);
	}

	@Test
	@SuppressWarnings("ConstantConditions")
	public void findUserByRoleName() {
		when(userRepository.findByRoleName("role_admin")).thenReturn(createUserList().subList(0, 2));
		Iterable<UserResponse> users = userService.findUsersByRoleName("role_admin");
		assertThat(users).hasSize(2);
	}

	@Test
	public void saveUser() {
		when(userRepository.save(any(User.class))).thenReturn(createUser("username2", "password2", "role_admin"));
		assertThat(userService.saveUser(new UserRequest()).getUsername()).isEqualTo("username2");
	}

	@Test
	public void loadRoles() {
		RoleResponse response = new RoleResponse();
		response.setRoleName("role_user");
		when(roleRepository.findAll()).thenReturn(Lists.newArrayList(createRole("role_admin"), createRole("role_user")));
		assertThat(userService.loadRoles()).hasSize(2).contains(response);
	}

	@Test
	public void findRoleByName() {
		when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(createRole("ROLE_ADMIN"));
		RoleResponse response = userService.findRoleByName("ROLE_ADMIN");
		assertThat(response.getRoleName()).isEqualTo("ROLE_ADMIN");
	}

	@Test
	public void deleteUserByUserId() {
		ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
		userService.deleteUser("1234567890");
		verify(userRepository).deleteRemovableUser(argument.capture());
		assertThat("1234567890").isEqualTo(argument.getValue());
	}

	@Test
	public void updateUser() {
		stubUpdateSuccess();
		boolean recordUpdated = userService.updateUser(new UserRequest());
		assertThat(recordUpdated).isTrue();
	}

	@Test
	public void changePasswordSuccessful() {
		stubUpdateSuccess();
		ChangePasswordRequest request = new ChangePasswordRequest();
		request.setUserId("1234567890");
		request.setCurrentPassword("old password");
		request.setNewPassword("new password");
		request.setPasswordConfirmed("new password");
		boolean success = userService.changePassword(request);
		assertThat(success).isTrue();
	}

	@Test
	public void changePasswordFailingWithNewPasswordEmpty() {
		ChangePasswordRequest request = new ChangePasswordRequest();
		request.setUserId("1234567890");
		request.setCurrentPassword("old password");
		request.setNewPassword(null);
		request.setPasswordConfirmed("wrong password");
		boolean success = userService.changePassword(request);
		assertThat(success).isFalse();
	}

	@Test
	public void changePasswordFailingWithConfirmedNewPasswordEmpty() {
		ChangePasswordRequest request = new ChangePasswordRequest();
		request.setUserId("1234567890");
		request.setCurrentPassword("old password");
		request.setNewPassword("new password");
		request.setPasswordConfirmed(null);
		boolean success = userService.changePassword(request);
		assertThat(success).isFalse();
	}

	@Test
	public void changePasswordFailWithWrongConfirmationPassword() {
		ChangePasswordRequest request = new ChangePasswordRequest();
		request.setUserId("1234567890");
		request.setCurrentPassword("old password");
		request.setNewPassword("new password");
		request.setPasswordConfirmed("wrong password");
		boolean success = userService.changePassword(request);
		assertThat(success).isFalse();
	}

	private void stubUpdateSuccess() {
		when(writeResultMock.getLastError()).thenReturn(commandResult);
		when(commandResult.ok()).thenReturn(true);
		when(mongoTemplate.updateFirst(
				any(Query.class), any(Update.class), any(Class.class))
		).thenReturn(writeResultMock);
	}

	private List<User> createUserList() {
		return Lists.newArrayList(
				createUser("username1", "password1", "role_admin"),
				createUser("username2", "password2", "role_admin"),
				createUser("username3", "password3", "role_user"),
				createUser("username4", "password4", "role_user"),
				createUser("username5", "password5", "role_user"),
				createUser("username6", "password6", "role_user"),
				createUser("username7", "password7", "role_user"),
				createUser("username8", "password8", "role_user"),
				createUser("username9", "password9", "role_user"),
				createUser("username10", "password10", "role_user"),
				createUser("username11", "password11", "role_user"),
				createUser("username12", "password12", "role_user"),
				createUser("username13", "password13", "role_user"),
				createUser("username14", "password14", "role_user"),
				createUser("username15", "password15", "role_user")
		);
	}

	private User createUser(String username, String password, String roleName) {
		User user = new User();
		Role role = createRole(roleName);
		user.setId("1234567890");
		user.setUsername(username);
		user.setPassword(password);
		user.setRemovable(true);
		user.setRole(role);
		return user;
	}

	private Role createRole(String roleName) {
		Role role = new Role();
		role.setName(roleName);
		return role;
	}

}
