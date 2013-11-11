package it.f2informatica.webapp.test.controllers;

import it.f2informatica.webapp.controllers.ConsultantController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class ConsultantControllerTest {

	@InjectMocks
	private ConsultantController consultantController;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders
				.standaloneSetup(consultantController)
				.build();
	}

	@Test
	public void goToConsultantManagementPage() throws Exception {
		mockMvc.perform(get("/consultant/consultant-management"))
				.andExpect(status().isOk())
				.andExpect(view().name("consultant/consultants"));
	}

}
