package it.f2informatica.test.services.domain.user;

import it.f2informatica.services.domain.user.PasswordUpdaterService;
import it.f2informatica.services.domain.user.PasswordUpdaterServiceImpl;
import it.f2informatica.services.gateway.UserRepositoryGateway;
import it.f2informatica.services.requests.UpdatePasswordRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static it.f2informatica.test.services.builder.UpdatePasswordRequestBuilder.updatePasswordRequest;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PasswordUpdaterServiceTest {

	@Mock
	private UserRepositoryGateway userRepositoryGateway;

	@InjectMocks
	private PasswordUpdaterService passwordUpdaterService = new PasswordUpdaterServiceImpl();

	@Test
	public void updatePasswordTest() {
		UpdatePasswordRequest updatePasswordRequest = updatePasswordRequest().build();
		when(userRepositoryGateway.updatePassword(updatePasswordRequest)).thenReturn(true);
		assertThat(passwordUpdaterService.updatePassword(updatePasswordRequest)).isTrue();
	}

}
