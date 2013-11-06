package it.f2informatica.webapp.test.gateway;

import com.google.common.collect.Lists;
import it.f2informatica.services.requests.ChangePasswordRequest;
import it.f2informatica.services.requests.UserRequest;
import it.f2informatica.services.responses.RoleResponse;
import it.f2informatica.services.responses.UserResponse;
import it.f2informatica.services.user.UserService;
import it.f2informatica.webapp.gateway.UserServiceGateway;
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

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceGatewayTest {

	@Mock
	private UserService userService;

	private UserServiceGateway userServiceGateway;

	@Before
	public void setUp() {
		userServiceGateway = new UserServiceGateway();
		userServiceGateway.setUserService(userService);
	}

	@Test
	public void getAuthenticatedUser() {
		when(userService.findByUsername("username")).thenReturn(createUserResponse());
		UserResponse response = userServiceGateway.getAuthenticatedUser("username");
		assertThat(response).isEqualTo(createUserResponse());
		assertThat(response.getUsername()).isEqualTo("username");
	}

	@Test
	public void loadRoles() {
		when(userService.loadRoles()).thenReturn(
				Lists.newArrayList(createRoleResponse("admin"), createRoleResponse("user"))
		);
		assertThat(userServiceGateway.loadRoles()).hasSize(2).contains(createRoleResponse("admin"));
	}

	@Test
	public void findAllUsers() {
		when(userService.findAll(any(Pageable.class)))
				.thenReturn(new PageImpl<>(Lists.newArrayList(createUserResponse())));
		Page<UserResponse> pageResponse = userServiceGateway.findAllUsers(new PageRequest(1, 10));
		assertThat(pageResponse).hasSize(1);
	}

	@Test
	public void findUserById() {
		when(userService.findUserById("1234567890")).thenReturn(createUserResponse());
		UserResponse response = userServiceGateway.findUserById("1234567890");
		assertThat(response.getUsername()).isEqualTo("username");
	}

	@Test
	public void	saveUser() {
		when(userService.saveUser(any(UserRequest.class))).thenReturn(createUserResponse());
		UserResponse response = userServiceGateway.saveUser(createUserRequest());
		assertThat(response).isNotNull();
	}

	@Test
	public void	updateUser() {
		when(userService.updateUser(any(UserRequest.class))).thenReturn(true);
		boolean success = userServiceGateway.updateUser(createUserRequest());
		assertThat(success).isTrue();
	}

	@Test
	public void	deleteUser() {
		ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
		userServiceGateway.deleteUser("12345");
		verify(userService).deleteUser(argument.capture());
		assertThat("12345").isEqualTo(argument.getValue());
	}

	@Test
	public void updatePassword() {
		ChangePasswordRequest request = new ChangePasswordRequest();
		request.setUserId("1234567890");
		request.setCurrentPassword("old password");
		request.setNewPassword("new password");
		request.setPasswordConfirmed("new password");
		when(userService.changePassword(any(ChangePasswordRequest.class))).thenReturn(true);
		assertThat(userServiceGateway.updatePassword(request)).isTrue();
	}

	@Test
	public void prepareNewUserToSave() {
		assertThat(userServiceGateway.prepareNewUserToSave().isRemovable()).isTrue();
	}

	@Test
	public void prepareUserToUpdate() {
		when(userService.findUserById("12345")).thenReturn(createUserResponse());
		when(userService.findRoleByName("Administrator")).thenReturn(createRoleResponse("Administrator"));
		UserRequest request = userServiceGateway.prepareUserToUpdate("12345");
		assertThat(request.getRoleId()).isEqualTo("555");
	}

	@Test
	public void prepareChangePasswordRequest() {
		ChangePasswordRequest request = userServiceGateway.prepareChangePasswordRequest("12345");
		assertThat(request.getUserId()).isEqualTo("12345");
	}

	private UserResponse createUserResponse() {
		UserResponse userResponse = new UserResponse();
		userResponse.setUserId("1234567890");
		userResponse.setUsername("username");
		userResponse.setRemovable(true);
		userResponse.setAuthorization("Administrator");
		return userResponse;
	}

	private UserRequest createUserRequest() {
		UserRequest userRequest = new UserRequest();
		userRequest.setUserId("1234567890");
		userRequest.setUsername("username");
		userRequest.setPassword("password");
		userRequest.setRemovable(true);
		userRequest.setRoleId("555");
		return userRequest;
	}

	private RoleResponse createRoleResponse(String roleName) {
		RoleResponse roleResponse = new RoleResponse();
		roleResponse.setRoleId("555");
		roleResponse.setRoleName(roleName);
		return roleResponse;
	}
}
