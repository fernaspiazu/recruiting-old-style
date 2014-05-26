package it.f2informatica.core.services;

import it.f2informatica.core.model.UpdatePasswordModel;

public interface PasswordUpdaterService {

  void updatePassword(UpdatePasswordModel request);

  boolean isCurrentPasswordValid(String userId, String currentPwd);

  UpdatePasswordModel prepareUpdatePasswordModel(String userId);
}
