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
import it.f2informatica.core.model.RoleModel;
import it.f2informatica.core.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

  Optional<UserModel> findUserById(String userId);

  Optional<UserModel> findByUsername(String username);

  Optional<UserModel> findByUsernameAndPassword(String username, String password);

  Page<UserModel> findAllExcludingCurrentUser(Pageable pageable, String usernameToExclude);

  Iterable<UserModel> findUsersByRoleName(String roleName);

  UserModel saveUser(UserModel user);

  void updateUser(UserModel userRequest);

  void deleteUser(String userId);

  Iterable<RoleModel> loadRoles();

  Optional<RoleModel> findRoleByName(String roleName);

  UserModel buildEmptyUserModel();
}
