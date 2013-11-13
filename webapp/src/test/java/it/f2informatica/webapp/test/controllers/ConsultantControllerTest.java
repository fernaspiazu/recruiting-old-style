package it.f2informatica.webapp.test.controllers;

import it.f2informatica.webapp.controllers.ConsultantController;
import it.f2informatica.webapp.gateway.ConsultantServiceGateway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class ConsultantControllerTest {

	@Mock
	private ConsultantServiceGateway consultantServiceGateway;

	@InjectMocks
	private ConsultantController consultantController;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders
				.standaloneSetup(consultantController)
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
				.build();
	}

	@Test
	public void goToConsultantManagementPage() throws Exception {
		mockMvc.perform(get("/consultant/consultant-management"))
				.andExpect(status().isOk())
				.andExpect(view().name("consultant/consultants"));
	}

}
