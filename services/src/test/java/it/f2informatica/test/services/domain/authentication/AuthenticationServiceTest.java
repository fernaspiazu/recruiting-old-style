package it.f2informatica.test.services.domain.authentication;

import it.f2informatica.mongodb.domain.User;
import it.f2informatica.mongodb.repositories.UserRepository;
import it.f2informatica.services.domain.authentication.AuthenticationService;
import it.f2informatica.services.domain.authentication.AuthenticationServiceImpl;
import it.f2informatica.services.responses.AuthenticationResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static it.f2informatica.mongodb.domain.builder.RoleBuilder.role;
import static it.f2informatica.mongodb.domain.builder.UserBuilder.user;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private AuthenticationService authenticationService = new AuthenticationServiceImpl();

	@Test
	public void processLoginTest() {
		when(userRepository.findByUsername("jhon")).thenReturn(getUser());
		AuthenticationResponse response = authenticationService.processLogin("jhon");
		assertThat(response.getUsername()).isEqualTo("jhon");
	}

	public User getUser() {
		return user()
				.withUsername("jhon")
				.withPassword("jhon85*")
				.withRole(role().thatIsAdministrator())
				.build();
	}
}
