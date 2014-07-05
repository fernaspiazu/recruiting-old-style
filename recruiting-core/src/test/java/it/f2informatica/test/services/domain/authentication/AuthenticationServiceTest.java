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
package it.f2informatica.test.services.domain.authentication;

import com.google.common.base.Optional;
import it.f2informatica.core.gateway.UserRepositoryGateway;
import it.f2informatica.core.model.AuthenticationModel;
import it.f2informatica.core.services.AuthenticationService;
import it.f2informatica.core.services.AuthenticationServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTest {

	@Mock
	private UserRepositoryGateway userRepositoryGateway;

	@InjectMocks
	private AuthenticationService authenticationService = new AuthenticationServiceImpl();

	@Test
	public void processLoginTest() {
		when(userRepositoryGateway.authenticationByUsername("jhon")).thenReturn(Optional.of(response()));
		Optional<AuthenticationModel> response = authenticationService.processLogin("jhon");
		assertThat(response.get().getUsername()).isEqualTo("jhon");
	}

	private AuthenticationModel response() {
		AuthenticationModel response = new AuthenticationModel();
		response.setUsername("jhon");
		response.setPassword("jhon85*");
		response.setAuthorization("Administrator");
		return response;
	}

}
