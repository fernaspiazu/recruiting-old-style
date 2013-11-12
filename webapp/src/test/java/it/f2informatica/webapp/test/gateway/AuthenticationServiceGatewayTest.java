package it.f2informatica.webapp.test.gateway;

import it.f2informatica.services.domain.authentication.AuthenticationService;
import it.f2informatica.services.responses.AuthenticationResponse;
import it.f2informatica.webapp.gateway.AuthenticationServiceGateway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceGatewayTest {

	@Mock
	private AuthenticationService authenticationService;

	@InjectMocks
	private AuthenticationServiceGateway loginServiceGateway;

	@Test
	public void processLogin() {
		when(authenticationService.processLogin("username")).thenReturn(createLoginResponse());
		AuthenticationResponse authenticationResponse = loginServiceGateway.processLogin("username");
		assertThat(authenticationResponse).isEqualTo(createLoginResponse());
		assertThat(authenticationResponse.getUsername()).isEqualTo("username");
	}

	private AuthenticationResponse createLoginResponse() {
		AuthenticationResponse authenticationResponse = new AuthenticationResponse();
		authenticationResponse.setUsername("username");
		authenticationResponse.setPassword("password");
		authenticationResponse.setAuthorization("Administrator");
		return authenticationResponse;
	}

}
