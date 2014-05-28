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

import it.f2informatica.webapp.controller.AuthenticationController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationControllerTest {

  private MockMvc mockMvc;

  @InjectMocks
  private AuthenticationController authenticationController;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders
      .standaloneSetup(authenticationController)
      .build();
  }

  @Test
  public void testLogin() throws Exception {
    mockMvc.perform(get("/login"))
      .andExpect(status().isOk())
      .andExpect(view().name("login/login"));
  }

  @Test
  public void testLoginFailed() throws Exception {
    mockMvc.perform(get("/login_failed"))
      .andExpect(model().attribute("hasErrors", true))
      .andExpect(view().name("login/login"));
  }

}
