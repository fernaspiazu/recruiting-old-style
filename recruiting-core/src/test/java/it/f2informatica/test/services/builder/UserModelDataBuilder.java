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
package it.f2informatica.test.services.builder;

import it.f2informatica.core.model.RoleModel;
import it.f2informatica.core.model.UserModel;

public class UserModelDataBuilder {

  private String id = "52820f6f34bdf55624303fc1";
  private String username = "jhon_kent77";
  private String password = "okisteralio";
  private String firstName = "Jhon";
  private String lastName = "Kent";
  private String email = "jhon_kent77@aol.com";
  private boolean notRemovable = false;
  private RoleModel role = new RoleModel();

  public static UserModelDataBuilder userModel() {
    return new UserModelDataBuilder();
  }

  public UserModelDataBuilder withId(String id) {
    this.id = id;
    return this;
  }

  public UserModelDataBuilder withUsername(String username) {
    this.username = username;
    return this;
  }

  public UserModelDataBuilder withPassword(String password) {
    this.password = password;
    return this;
  }

  public UserModelDataBuilder withEmail(String email) {
    this.email = email;
    return this;
  }

  public UserModelDataBuilder withFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public UserModelDataBuilder withLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public UserModelDataBuilder withRoleId(String roleId) {
    role.setRoleId(roleId);
    return this;
  }

  public UserModelDataBuilder withRoleName(String roleName) {
    role.setRoleName(roleName);
    return this;
  }

  public UserModelDataBuilder thatIsNotRemovable() {
    this.notRemovable = true;
    return this;
  }

  public UserModelDataBuilder thatIsRemovable() {
    this.notRemovable = false;
    return this;
  }

  public UserModel build() {
    UserModel user = new UserModel();
    user.setUserId(id);
    user.setUsername(username);
    user.setPassword(password);
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setEmail(email);
    user.setNotRemovable(notRemovable);
    user.setRole(role);
    return user;
  }

}
