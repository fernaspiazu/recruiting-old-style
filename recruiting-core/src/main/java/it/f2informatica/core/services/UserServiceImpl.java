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

import com.google.common.base.Optional;
import it.f2informatica.core.gateway.UserRepositoryGateway;
import it.f2informatica.core.model.RoleModel;
import it.f2informatica.core.model.UserModel;
import it.f2informatica.pagination.services.QueryParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static it.f2informatica.core.model.builder.UserModelBuilder.userModel;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepositoryGateway userRepositoryGateway;

  @Override
  public Optional<UserModel> findUserById(String userId) {
    return Optional.fromNullable(userRepositoryGateway.findUserById(userId));
  }

  @Override
  public Optional<UserModel> findByUsername(String username) {
    return Optional.fromNullable(userRepositoryGateway.findByUsername(username));
  }

  @Override
  public Optional<UserModel> findByUsernameAndPassword(String username, String password) {
    return Optional.fromNullable(userRepositoryGateway.findByUsernameAndPassword(username, password));
  }

  @Override
  public String getAllUsersPaginated(QueryParameters parameters, String currentUsername) {
    return userRepositoryGateway.getAllUsersPaginated(parameters, currentUsername);
  }

  @Override
  public Page<UserModel> findAllExcludingCurrentUser(Pageable pageable, String usernameToExclude) {
    return userRepositoryGateway.findAllExcludingCurrentUser(pageable, usernameToExclude);
  }

  @Override
  public Iterable<UserModel> findUsersByRoleName(String roleName) {
    return userRepositoryGateway.findUsersByRoleName(roleName);
  }

  @Override
  public UserModel saveUser(UserModel userModel) {
    return userRepositoryGateway.saveUser(userModel);
  }

  @Override
  public void updateUser(UserModel userModel) {
    userRepositoryGateway.updateUser(userModel);
  }

  @Override
  public void deleteUser(String userId) {
    userRepositoryGateway.deleteUser(userId);
  }

  @Override
  public Iterable<RoleModel> loadRoles() {
    return userRepositoryGateway.loadRoles();
  }

  @Override
  public Optional<RoleModel> findRoleByName(String roleName) {
    return Optional.fromNullable(userRepositoryGateway.findRoleByName(roleName));
  }

  @Override
  public UserModel buildEmptyUserModel() {
    return userModel().build();
  }

}
