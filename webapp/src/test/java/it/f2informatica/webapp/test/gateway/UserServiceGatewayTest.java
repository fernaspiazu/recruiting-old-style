package it.f2informatica.webapp.test.gateway;

import com.google.common.collect.Lists;
import it.f2informatica.services.domain.user.UserService;
import it.f2informatica.services.model.RoleModel;
import it.f2informatica.services.model.UserModel;
import it.f2informatica.webapp.gateway.UserServiceGateway;
import it.f2informatica.webapp.security.SecurityAccessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceGatewayTest {

	@Mock
	private UserService userService;

	@Mock
	private SecurityAccessor securityAccessor;

	@InjectMocks
	private UserServiceGateway userServiceGateway = new UserServiceGateway();

	@Test
	public void getAuthenticatedUser() {
		when(userService.findByUsername("username")).thenReturn(createUserModel());
		UserModel response = userServiceGateway.getAuthenticatedUser("username");
		assertThat(response).isEqualTo(createUserModel());
		assertThat(response.getUsername()).isEqualTo("username");
	}

	@Test
	public void loadRoles() {
		when(userService.loadRoles()).thenReturn(
				Lists.newArrayList(createRoleModel("admin"), createRoleModel("user"))
		);
		assertThat(userServiceGateway.loadRoles()).hasSize(2).contains(createRoleModel("admin"));
	}

	@Test
	public void findAllUsers() {
		when(userService.findAllExcludingCurrentUser(any(Pageable.class), anyString()))
				.thenReturn(new PageImpl<>(Lists.newArrayList(createUserModel())));
		Page<UserModel> pageResponse = userServiceGateway.findAllUsers(new PageRequest(1, 10));
		assertThat(pageResponse).hasSize(1);
	}

	@Test
	public void findUserById() {
		when(userService.findUserById("1234567890")).thenReturn(createUserModel());
		UserModel response = userServiceGateway.findUserById("1234567890");
		assertThat(response.getUsername()).isEqualTo("username");
	}

	@Test
	public void	saveUser() {
		when(userService.saveUser(any(UserModel.class))).thenReturn(createUserModel());
		UserModel response = userServiceGateway.saveUser(createUserModel());
		assertThat(response).isNotNull();
	}

	@Test
	public void	updateUser() {
		when(userService.updateUser(any(UserModel.class))).thenReturn(true);
		boolean success = userServiceGateway.updateUser(createUserModel());
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
	public void prepareNewUserToSave() {
		assertThat(userServiceGateway.prepareNewUserModel().isNotRemovable()).isFalse();
	}

	@Test
	public void prepareUserToUpdate() {
		when(userService.findUserById("12345")).thenReturn(createUserModel());
		when(userService.findRoleByName("Administrator")).thenReturn(createRoleModel("Administrator"));
		UserModel userModel = userServiceGateway.prepareUpdatingUserModel("12345");
		assertThat(userModel.getRole().getRoleId()).isEqualTo("555");
	}

	private UserModel createUserModel() {
		UserModel userModel = new UserModel();
		userModel.setUserId("1234567890");
		userModel.setUsername("username");
		userModel.setNotRemovable(false);
		RoleModel role = new RoleModel();
		role.setRoleId("123456");
		role.setRoleName("Administrator");
		userModel.setRole(role);
		return userModel;
	}

	private RoleModel createRoleModel(String roleName) {
		RoleModel roleResponse = new RoleModel();
		roleResponse.setRoleId("555");
		roleResponse.setRoleName(roleName);
		return roleResponse;
	}
}
