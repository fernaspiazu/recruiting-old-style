package it.f2informatica.webapp.test.gateway;

import it.f2informatica.services.responses.LoginResponse;
import it.f2informatica.services.user.UserService;
import it.f2informatica.webapp.gateway.LoginServiceGateway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceGatewayTest {

	@Mock
	private UserService userService;

	private LoginServiceGateway loginServiceGateway;

	@Before
	public void setUp() {
		loginServiceGateway = new LoginServiceGateway();
		loginServiceGateway.setUserService(userService);
	}

	@Test
	public void processLogin() {
		when(userService.processLogin("username")).thenReturn(createLoginResponse());
		LoginResponse loginResponse = loginServiceGateway.processLogin("username");
		assertThat(loginResponse).isEqualTo(createLoginResponse());
		assertThat(loginResponse.getUsername()).isEqualTo("username");
	}

	private LoginResponse createLoginResponse() {
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setUsername("username");
		loginResponse.setPassword("password");
		loginResponse.setAuthorization("Administrator");
		return loginResponse;
	}

}
