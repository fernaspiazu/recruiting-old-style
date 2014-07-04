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
package it.f2informatica.core.gateway;

import it.f2informatica.core.model.AuthenticationModel;
import it.f2informatica.core.model.RoleModel;
import it.f2informatica.core.model.UpdatePasswordModel;
import it.f2informatica.core.model.UserModel;
import it.f2informatica.pagination.services.QueryParameters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryGateway {

  AuthenticationModel authenticationByUsername(String username);

  void updatePassword(UpdatePasswordModel request);

  UserModel findUserById(String userId);

  UserModel findByUsername(String username);

  UserModel findByUsernameAndPassword(String username, String password);

  Page<UserModel> findAllExcludingCurrentUser(Pageable pageable, String usernameToExclude);

  String getAllUsersPaginated(QueryParameters parameters, String currentUsername);

  UserModel saveUser(UserModel userModel);

  void updateUser(UserModel userModel);

  void deleteUser(String userId);

  Iterable<RoleModel> loadRoles();

  RoleModel findRoleByName(String roleName);

  boolean isCurrentPasswordValid(String userId, String currentPwd);
}
