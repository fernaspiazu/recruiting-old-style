package it.f2informatica.webapp.test.controllers;

import it.f2informatica.webapp.controllers.UserController;
import it.f2informatica.webapp.gateway.PasswordUpdaterServiceGateway;
import it.f2informatica.webapp.gateway.UserServiceGateway;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

	@Mock
	private UserServiceGateway userServiceGateway;

	@Mock
	private PasswordUpdaterServiceGateway passwordUpdaterServiceGateway;

	@InjectMocks
	private UserController userController;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders
				.standaloneSetup(userController)
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
	public void saveUser() throws Exception {
		mockMvc.perform(post("/user/saveUser"))
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
