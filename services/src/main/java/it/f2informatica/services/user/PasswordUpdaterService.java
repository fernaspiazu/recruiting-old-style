package it.f2informatica.services.user;

import it.f2informatica.services.requests.UpdatePasswordRequest;

public interface PasswordUpdaterService {

	boolean updatePassword(UpdatePasswordRequest request);

	UpdatePasswordRequest prepareUpdatePasswordRequest(String userId);

	boolean isCurrentPasswordValid(String userId, String currentPwd);
}
