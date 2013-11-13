package it.f2informatica.webapp.gateway;

import it.f2informatica.services.domain.user.PasswordUpdaterService;
import it.f2informatica.services.requests.UpdatePasswordRequest;
import it.f2informatica.services.requests.builders.UpdatePasswordRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static it.f2informatica.services.requests.builders.UpdatePasswordRequestBuilder.*;

@Service
public class PasswordUpdaterServiceGateway {

	private PasswordUpdaterService passwordUpdaterService;

	@Autowired
	public void setPasswordUpdaterService(PasswordUpdaterService passwordUpdaterService) {
		this.passwordUpdaterService = passwordUpdaterService;
	}

	public boolean updatePassword(UpdatePasswordRequest request) {
		return passwordUpdaterService.updatePassword(request);
	}

	public UpdatePasswordRequest prepareUpdatePasswordRequest(String userId) {
		return updatePasswordRequest()
				.userIdToFind(userId)
				.build();
	}

}
