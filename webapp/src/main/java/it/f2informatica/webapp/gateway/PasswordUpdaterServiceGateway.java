package it.f2informatica.webapp.gateway;

import it.f2informatica.services.domain.user.PasswordUpdaterService;
import it.f2informatica.services.requests.UpdatePasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordUpdaterServiceGateway {

	@Autowired
	private PasswordUpdaterService passwordUpdaterService;

	public boolean updatePassword(UpdatePasswordRequest request) {
		return passwordUpdaterService.updatePassword(request);
	}

	public UpdatePasswordRequest prepareUpdatePasswordRequest(String userId) {
		UpdatePasswordRequest request = new UpdatePasswordRequest();
		request.setUserId(userId);
		return request;
	}

}
