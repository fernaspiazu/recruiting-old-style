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

import it.f2informatica.core.model.ConsultantModel;
import it.f2informatica.core.services.ConsultantService;
import it.f2informatica.core.validator.ConsultantEducationValidator;
import it.f2informatica.core.validator.ConsultantExperienceValidator;
import it.f2informatica.core.validator.ConsultantPersonalDetailsValidator;
import it.f2informatica.core.validator.utils.ValidationResponseHandler;
import it.f2informatica.webapp.controller.ConsultantController;
import it.f2informatica.webapp.test.context.GsonFactory;
import it.f2informatica.webapp.utils.HttpRequest;
import it.f2informatica.webapp.utils.MonthHelper;
import it.f2informatica.webapp.utils.PeriodParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ConsultantControllerTest {

	@Mock
	private MonthHelper monthHelper;

	@Mock
	private PeriodParser periodParser;

	@Mock
	private HttpRequest httpRequest;

	@Mock
	private ConsultantService consultantService;

	@Mock
	private ConsultantEducationValidator educationValidator;

	@Mock
	private ConsultantExperienceValidator experienceValidator;

	@Mock
	private ValidationResponseHandler validationResponseHandler;

	@Mock
	private ConsultantPersonalDetailsValidator personalDetailsValidator;

	@InjectMocks
	private ConsultantController consultantController = new ConsultantController();

	private MockMvc mockMvc;

	public ConsultantControllerTest() {
		ReflectionTestUtils.setField(consultantController, "gson", GsonFactory.gson());
	}

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(consultantController)
			.setValidator(personalDetailsValidator)
			.setValidator(experienceValidator)
			.setValidator(educationValidator)
			.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
			.build();
	}

	@Test
	public void savePersonalDetailsTest() throws Exception {
		when(consultantService.savePersonalDetails(any(ConsultantModel.class))).thenReturn(new ConsultantModel());
		mockMvc.perform(post("/consultant/save-personal-details"))
			.andDo(print())
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/consultants"));
		verify(consultantService, times(1)).savePersonalDetails(new ConsultantModel());
	}

}
