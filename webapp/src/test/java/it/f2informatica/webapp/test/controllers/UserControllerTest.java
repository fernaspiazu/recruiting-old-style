package it.f2informatica.webapp.test.controllers;

import it.f2informatica.webapp.controller.UserController;
import it.f2informatica.webapp.gateway.PasswordUpdaterServiceGateway;
import it.f2informatica.webapp.gateway.UserServiceGateway;
import it.f2informatica.webapp.validator.RegistrationUserValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

	@Mock
	private UserServiceGateway userServiceGateway;

	@Mock
	private PasswordUpdaterServiceGateway passwordUpdaterServiceGateway;

	@Mock
	private RegistrationUserValidator registrationUserValidator;

	@InjectMocks
	private UserController userController;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders
				.standaloneSetup(userController)
				.setValidator(new RegistrationUserValidator())
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
				.build();
	}

	@Test
	public void createNewUser() throws Exception {
		mockMvc.perform(get("/user/createNewUser"))
				.andExpect(status().isOk())
				.andExpect(view().name("user/createNewUser"));
	}

	@Test
	public void loadUsers() throws Exception {
		mockMvc.perform(get("/user/loadUsers"))
				.andExpect(status().isOk())
				.andExpect(view().name("user/users"));
	}

	@Test
	public void getUser() throws Exception {
		mockMvc.perform(get("/user/findUser/1234567890"))
				.andExpect(request().attribute("userId", "1234567890"))
				.andExpect(status().isOk())
				.andExpect(view().name("user/userDetails"));
	}

	@Test
	public void editUser() throws Exception {
		mockMvc.perform(get("/user/editUser/1234567890"))
				.andExpect(status().isOk())
				.andExpect(view().name("user/userEdit"));
	}

	@Test
	public void failingUserRegistration() throws Exception {
		mockMvc.perform(post("/user/saveUser"))
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(model().errorCount(3))
				.andExpect(view().name("user/createNewUser"));
	}

	@Test
	public void successfulUserRegistration() throws Exception {
		mockMvc.perform(post("/user/saveUser")
					.param("username", "username")
					.param("password", "password")
					.param("role.roleId", "52602b9b92bede6f44752e35")
					.param("role.roleName", "ADMIN"))
				.andDo(print())
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/user/loadUsers"));
	}

	@Test
	public void updateUser() throws Exception {
		mockMvc.perform(post("/user/updateUser"))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/user/loadUsers"));
	}

	@Test
	public void deleteUser() throws Exception {
		mockMvc.perform(get("/user/deleteUser/1234567890"))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/user/loadUsers"));
	}

	@Test
	public void changePasswordForm() throws Exception {
		mockMvc.perform(get("/user/changePassword/1234567890"))
				.andExpect(request().attribute("userId", "1234567890"))
				.andExpect(status().isOk())
				.andExpect(view().name("user/changePasswordForm"));
	}

	@Test
	public void updatePassword() throws Exception {
		mockMvc.perform(post("/user/updatePassword"))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/user/loadUsers"));
	}

}
