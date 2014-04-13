package it.f2informatica.webapp.test.controllers;

import it.f2informatica.core.services.ConsultantService;
import it.f2informatica.webapp.controller.ConsultantController;
import it.f2informatica.webapp.utils.MonthHelper;
import it.f2informatica.webapp.utils.PeriodParser;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)
public class ConsultantControllerTest {

  @Mock
  private MonthHelper monthHelper;

  @Mock
  private PeriodParser periodParser;

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
    mockMvc.perform(get("/consultant/new-consultant"))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(view().name("consultant/consultantForm"));
  }

}
