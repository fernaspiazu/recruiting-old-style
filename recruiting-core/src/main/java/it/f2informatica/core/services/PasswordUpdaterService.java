package it.f2informatica.core.services;

import it.f2informatica.core.model.UpdatePasswordModel;

public interface PasswordUpdaterService {

  void updatePassword(UpdatePasswordModel request);

  UpdatePasswordModel prepareUpdatePasswordModel(String userId);

  boolean isCurrentPasswordValid(String userId, String currentPwd);
}
