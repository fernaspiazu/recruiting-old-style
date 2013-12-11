package it.f2informatica.webapp.test.controllers;

import it.f2informatica.services.domain.consultant.ConsultantService;
import it.f2informatica.services.model.ConsultantModel;
import it.f2informatica.webapp.controller.ConsultantController;
import it.f2informatica.webapp.controller.helper.MonthHelper;
import it.f2informatica.webapp.controller.resolver.PeriodResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static it.f2informatica.services.model.builder.ConsultantModelBuilder.consultantModel;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class ConsultantControllerTest {

	@Mock
	private MonthHelper monthHelper;

	@Mock
	private PeriodResolver periodResolver;

	@Mock
	private ConsultantService consultantService;

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
	public void consultantRegistrationPage() throws Exception {
		mockMvc.perform(get("/consultant/create"))
				.andExpect(status().isOk())
				.andExpect(view().name("consultant/masterDataRegistration"));
	}

	@Test
	public void registerConsultantMasterData() throws Exception {
		ConsultantModel consMock = consultantModel()
				.withId("5298766a39ef39c7c280b7e5")
				.withFirstName("Mario")
				.withLastName("Rossi")
				.build();
		when(consultantService.registerConsultantMasterData(any(ConsultantModel.class))).thenReturn(consMock);
		mockMvc.perform(post("/consultant/registerMasterData"))
				.andDo(print())
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/consultant/profileDataRegistration/"+consMock.getId()));
	}

	@Test
	public void profileRegistrationPage() throws Exception {
		ConsultantModel consMock = consultantModel()
				.withId("5298766a39ef39c7c280b7e5")
				.withFirstName("Mario")
				.withLastName("Rossi")
				.build();
		when(consultantService.findConsultantById(consMock.getId())).thenReturn(consMock);
		mockMvc.perform(get("/consultant/profileDataRegistration/5298766a39ef39c7c280b7e5"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(model().attribute("consultantFullName", "Rossi Mario"))
				.andExpect(view().name("consultant/profileDataRegistration"));
	}

}
