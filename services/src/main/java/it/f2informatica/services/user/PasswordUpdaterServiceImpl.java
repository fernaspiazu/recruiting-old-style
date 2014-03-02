package it.f2informatica.services.user;

import it.f2informatica.services.gateway.UserRepositoryGateway;
import it.f2informatica.services.model.UpdatePasswordModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordUpdaterServiceImpl implements PasswordUpdaterService {

	@Autowired
	private UserRepositoryGateway userRepositoryGateway;

	@Override
	public boolean updatePassword(UpdatePasswordModel request) {
		return userRepositoryGateway.updatePassword(request);
	}

	@Override
	public UpdatePasswordModel prepareUpdatePasswordModel(String userId) {
		UpdatePasswordModel request = new UpdatePasswordModel();
		request.setUserId(userId);
		return request;
	}

	@Override
	public boolean isCurrentPasswordValid(String userId, String currentPwd) {
		return userRepositoryGateway.isCurrentPasswordValid(userId, currentPwd);
	}

}
