package it.f2informatica.test.services.domain.user;

import com.mongodb.CommandResult;
import com.mongodb.WriteResult;
import it.f2informatica.services.domain.user.PasswordUpdaterService;
import it.f2informatica.services.domain.user.impl.PasswordUpdaterServiceImpl;
import it.f2informatica.services.requests.UpdatePasswordRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import static it.f2informatica.test.services.builder.UpdatePasswordRequestBuilder.updatePasswordRequest;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PasswordUpdaterServiceTest {

	@Mock
	private WriteResult writeResultMock;

	@Mock
	private CommandResult commandResult;

	@Mock
	private MongoTemplate mongoTemplate;

	@InjectMocks
	private PasswordUpdaterService passwordUpdaterService = new PasswordUpdaterServiceImpl();

	@Test
	public void assertThatPasswordIsUpdatedSuccessfully() {
		stubUpdateSuccess();
		UpdatePasswordRequest correctRequest = updatePasswordRequest().build();
		boolean success = passwordUpdaterService.updatePassword(correctRequest);
		assertThat(success).isTrue();
	}

	@Test
	public void assertThatPasswordIsNotUpdatedBecauseNewPasswordIsEmpty() {
		UpdatePasswordRequest requestWithoutNewPassword =
				updatePasswordRequest()
						.withoutNewPassword()
						.build();
		boolean success = passwordUpdaterService.updatePassword(requestWithoutNewPassword);
		assertThat(success).isFalse();
	}

	@Test
	public void assertThatPasswordIsNotUpdatedBecauseConfirmedPasswordIsEmpty() {
		UpdatePasswordRequest requestWithoutNewPassword =
				updatePasswordRequest()
						.withoutConfirmedPassword()
						.build();
		boolean success = passwordUpdaterService.updatePassword(requestWithoutNewPassword);
		assertThat(success).isFalse();
	}

	@Test
	public void assertThatPasswordIsNotUpdatedBecauseBothNewPasswordAndConfirmedPasswordAreEmpty() {
		UpdatePasswordRequest requestWithoutNewPassword =
				updatePasswordRequest()
						.withoutNewPassword()
						.withoutConfirmedPassword()
						.build();
		boolean success = passwordUpdaterService.updatePassword(requestWithoutNewPassword);
		assertThat(success).isFalse();
	}

	@Test
	public void assertThatPasswordIsNotUpdatedBecauseNewPasswordAndConfirmedPasswordAreNotEquals() {
		UpdatePasswordRequest requestWithoutNewPassword =
				updatePasswordRequest()
						.withNewPassword("correct password")
						.withConfirmedPassword("other password")
						.build();
		boolean success = passwordUpdaterService.updatePassword(requestWithoutNewPassword);
		assertThat(success).isFalse();
	}

	@Test
	public void assertThatPasswordIsNotUpdatedBecausePasswordIsNotFound() {
		stubUpdateFail();
		UpdatePasswordRequest requestWithoutNewPassword =
				updatePasswordRequest()
						.withCurrentPassword("wrong password")
						.build();
		boolean success = passwordUpdaterService.updatePassword(requestWithoutNewPassword);
		assertThat(success).isFalse();
	}

	private void stubUpdateSuccess() {
		when(writeResultMock.getLastError()).thenReturn(commandResult);
		when(commandResult.ok()).thenReturn(true);
		when(mongoTemplate.updateFirst(
				any(Query.class),
				any(Update.class),
				any(Class.class))
		).thenReturn(writeResultMock);
	}

	private void stubUpdateFail() {
		when(writeResultMock.getLastError()).thenReturn(commandResult);
		when(commandResult.ok()).thenReturn(false);
		when(mongoTemplate.updateFirst(
				any(Query.class),
				any(Update.class),
				any(Class.class))
		).thenReturn(writeResultMock);
	}

}
