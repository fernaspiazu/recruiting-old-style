package it.f2informatica.test.services.domain.user;

import it.f2informatica.services.model.UpdatePasswordModel;
import it.f2informatica.services.user.PasswordUpdaterService;
import it.f2informatica.services.user.PasswordUpdaterServiceImpl;
import it.f2informatica.services.gateway.UserRepositoryGateway;
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
		UpdatePasswordModel updatePasswordModel = updatePasswordRequest().build();
		when(userRepositoryGateway.updatePassword(updatePasswordModel)).thenReturn(true);
		assertThat(passwordUpdaterService.updatePassword(updatePasswordModel)).isTrue();
	}

}
