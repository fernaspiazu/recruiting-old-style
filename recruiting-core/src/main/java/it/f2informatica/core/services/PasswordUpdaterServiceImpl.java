package it.f2informatica.core.services;

import it.f2informatica.core.gateway.UserRepositoryGateway;
import it.f2informatica.core.model.UpdatePasswordModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordUpdaterServiceImpl implements PasswordUpdaterService {

  @Autowired
  private UserRepositoryGateway userRepositoryGateway;

  @Override
  public void updatePassword(UpdatePasswordModel request) {
    userRepositoryGateway.updatePassword(request);
  }

  @Override
  public boolean isCurrentPasswordValid(String userId, String currentPwd) {
    return userRepositoryGateway.isCurrentPasswordValid(userId, currentPwd);
  }

  @Override
  public UpdatePasswordModel prepareUpdatePasswordModel(String userId) {
    UpdatePasswordModel request = new UpdatePasswordModel();
    request.setUserId(userId);
    return request;
  }

}
