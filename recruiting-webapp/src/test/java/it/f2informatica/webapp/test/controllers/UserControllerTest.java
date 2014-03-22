package it.f2informatica.webapp.test.controllers;

import it.f2informatica.services.model.RoleModel;
import it.f2informatica.services.model.UserModel;
import it.f2informatica.services.user.PasswordUpdaterService;
import it.f2informatica.services.user.UserService;
import it.f2informatica.services.validator.UserModelValidator;
import it.f2informatica.services.validator.utils.ValidationResponseService;
import it.f2informatica.webapp.controller.UserController;
import it.f2informatica.webapp.utils.SpecialMediaType;
import org.eclipse.jetty.http.HttpHeader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

	@Mock
	private UserService userService;

	@Mock
	private PasswordUpdaterService passwordUpdaterService;

	@Mock
	private UserModelValidator userModelValidator;

	@Mock
	private ValidationResponseService validationResponseService;

	@InjectMocks
	private UserController userController;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders
			.standaloneSetup(userController)
			.setValidator(userModelValidator)
			.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
			.build();
	}

	@Test
	public void editUser() throws Exception {
		UserModel userModel = new UserModel();
		userModel.setUserId("1234567890");
		userModel.setUsername("username");
		userModel.setPassword("password");
		userModel.setRole(new RoleModel("123456", "Administrator"));
		userModel.setFirstName("Username");
		userModel.setLastName("Lastname");
		userModel.setEmail("mario.rossi@tiscali.it");
		when(userService.findUserById("1234567890")).thenReturn(userModel);
		mockMvc.perform(get("/user/edit?userId=1234567890"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(header().string(HttpHeader.CONTENT_TYPE.asString(), SpecialMediaType.JSON_UTF_8));
	}

	@Test
	public void successfulUserRegistration() throws Exception {
		mockMvc.perform(post("/user/save")
			.param("username", "username")
			.param("password", "password")
			.param("role.roleId", "52602b9b92bede6f44752e35")
			.param("role.roleName", "ADMIN"))
			.andDo(print())
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/users"));
	}

	@Test
	public void deleteUser() throws Exception {
		mockMvc.perform(get("/user/delete?userId=1234567890"))
			.andDo(print())
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/users"));
	}

	@Test
	public void updatePassword() throws Exception {
		mockMvc.perform(post("/user/update-password"))
			.andDo(print())
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/users"));
	}

}
