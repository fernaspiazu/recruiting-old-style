package it.f2informatica.services.domain.user;

import it.f2informatica.services.gateway.UserRepositoryGateway;
import it.f2informatica.services.requests.UpdatePasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordUpdaterServiceImpl implements PasswordUpdaterService {

	@Autowired
	private UserRepositoryGateway userRepositoryGateway;

	@Override
	public boolean updatePassword(UpdatePasswordRequest request) {
		return userRepositoryGateway.updatePassword(request);
	}

	@Override
	public UpdatePasswordRequest prepareUpdatePasswordRequest(String userId) {
		UpdatePasswordRequest request = new UpdatePasswordRequest();
		request.setUserId(userId);
		return request;
	}

}
