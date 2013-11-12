package it.f2informatica.webapp.test.gateway;

import it.f2informatica.services.domain.user.PasswordUpdaterService;
import it.f2informatica.services.requests.UpdatePasswordRequest;
import it.f2informatica.webapp.gateway.PasswordUpdaterServiceGateway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PasswordUpdaterServiceGatewayTest {

	@Mock
	private PasswordUpdaterService pus;

	@InjectMocks
	private PasswordUpdaterServiceGateway pusg;

	@Test
	public void updatePasswordTest() {
		when(pus.updatePassword(any(UpdatePasswordRequest.class))).thenReturn(true);
		assertThat(pusg.updatePassword(new UpdatePasswordRequest())).isTrue();
	}

	@Test
	public void prepareUpdatePasswordRequestTest() {
		UpdatePasswordRequest request = pusg.prepareUpdatePasswordRequest("12345");
		assertThat(request.getUserId()).isEqualTo("12345");
	}

}
