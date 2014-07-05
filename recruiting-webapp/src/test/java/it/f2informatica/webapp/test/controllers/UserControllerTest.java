/*
 * =============================================================================
 *
 *   Copyright (c) 2014, Fernando Aspiazu
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */
package it.f2informatica.webapp.test.controllers;

import com.google.common.base.Optional;
import it.f2informatica.core.model.RoleModel;
import it.f2informatica.core.model.UserModel;
import it.f2informatica.core.services.PasswordUpdaterService;
import it.f2informatica.core.services.UserService;
import it.f2informatica.core.validator.UserModelValidator;
import it.f2informatica.core.validator.utils.ValidationResponseHandler;
import it.f2informatica.webapp.controller.UserController;
import it.f2informatica.webapp.utils.MediaTypeUTF8;
import org.eclipse.jetty.http.HttpHeader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
	public static final String USER_ID = "1234567890";

	@Mock
	private UserService userService;

	@Mock
	private UserModelValidator userModelValidator;

	@Mock
	private PasswordUpdaterService passwordUpdaterService;

	@Mock
	private ValidationResponseHandler validationResponseHandler;

	@InjectMocks
	private UserController userController;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = standaloneSetup(userController)
			.setValidator(userModelValidator)
			.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
			.build();
	}

	@Test
	public void editUser() throws Exception {
		UserModel userModel = getUserModel();
		when(userService.findUserById(USER_ID)).thenReturn(Optional.of(userModel));
		mockMvc.perform(get("/user/edit?userId=1234567890"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(header().string(HttpHeader.CONTENT_TYPE.asString(), MediaTypeUTF8.JSON_UTF_8));
	}

	private UserModel getUserModel() {
		UserModel userModel = new UserModel();
		userModel.setUserId(USER_ID);
		userModel.setUsername("username");
		userModel.setPassword("password");
		userModel.setRole(new RoleModel("123456", "Administrator"));
		userModel.setFirstName("Username");
		userModel.setLastName("Lastname");
		userModel.setEmail("mario.rossi@tiscali.it");
		return userModel;
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
