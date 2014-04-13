package it.f2informatica.test.services.domain.authentication;

import it.f2informatica.core.authentication.AuthenticationService;
import it.f2informatica.core.authentication.AuthenticationServiceImpl;
import it.f2informatica.core.gateway.UserRepositoryGateway;
import it.f2informatica.core.responses.AuthenticationResponse;
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
		when(userRepositoryGateway.authenticationByUsername("jhon")).thenReturn(response());
		AuthenticationResponse response = authenticationService.processLogin("jhon");
		assertThat(response.getUsername()).isEqualTo("jhon");
	}

	private AuthenticationResponse response() {
		AuthenticationResponse response = new AuthenticationResponse();
		response.setUsername("jhon");
		response.setPassword("jhon85*");
		response.setAuthorization("Administrator");
		return response;
	}

}
