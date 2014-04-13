package it.f2informatica.core.user;

import it.f2informatica.core.model.UpdatePasswordModel;

public interface PasswordUpdaterService {

	boolean updatePassword(UpdatePasswordModel request);

	UpdatePasswordModel prepareUpdatePasswordModel(String userId);

	boolean isCurrentPasswordValid(String userId, String currentPwd);
}