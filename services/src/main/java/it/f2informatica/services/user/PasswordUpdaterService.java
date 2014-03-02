package it.f2informatica.services.user;

import it.f2informatica.services.model.UpdatePasswordModel;

public interface PasswordUpdaterService {

	boolean updatePassword(UpdatePasswordModel request);

	UpdatePasswordModel prepareUpdatePasswordModel(String userId);

	boolean isCurrentPasswordValid(String userId, String currentPwd);
}
