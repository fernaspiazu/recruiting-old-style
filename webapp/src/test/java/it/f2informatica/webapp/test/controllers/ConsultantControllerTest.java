package it.f2informatica.webapp.test.controllers;

import it.f2informatica.webapp.controllers.ConsultantController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)
public class ConsultantControllerTest {

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
