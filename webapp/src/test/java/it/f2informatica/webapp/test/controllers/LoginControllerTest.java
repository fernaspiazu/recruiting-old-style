package it.f2informatica.webapp.test.controllers;

import it.f2informatica.webapp.controller.LoginController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private LoginController loginController;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders
				.standaloneSetup(loginController)
				.build();
	}

	@Test
	public void testLoginStatus() throws Exception {
		mockMvc.perform(get("/login"))
				.andExpect(status().isOk());
	}

	@Test
	public void testLoginView() throws Exception {
		mockMvc.perform(get("/login"))
				.andExpect(view().name("login/login"));
	}

}
