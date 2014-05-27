/*
 * =============================================================================
 *
 *   Copyright (c) 2014, Fernando Aspiazu
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */
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
