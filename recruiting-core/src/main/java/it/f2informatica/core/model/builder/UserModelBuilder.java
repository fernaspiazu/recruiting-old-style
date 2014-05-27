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
package it.f2informatica.core.model.builder;

import it.f2informatica.core.model.RoleModel;
import it.f2informatica.core.model.UserModel;

public class UserModelBuilder {

  private UserModel user = new UserModel();

  public static UserModelBuilder userModel() {
    return new UserModelBuilder();
  }

  public UserModelBuilder withId(String id) {
    this.user.setUserId(id);
    return this;
  }

  public UserModelBuilder withUsername(String username) {
    this.user.setUsername(username);
    return this;
  }

  public UserModelBuilder withPassword(String password) {
    this.user.setPassword(password);
    return this;
  }

  public UserModelBuilder withEmail(String email) {
    this.user.setEmail(email);
    return this;
  }

  public UserModelBuilder withFirstName(String firstName) {
    this.user.setFirstName(firstName);
    return this;
  }

  public UserModelBuilder withLastName(String lastName) {
    this.user.setLastName(lastName);
    return this;
  }

  public UserModelBuilder withRole(RoleModelBuilder role) {
    return withRole(role.build());
  }

  public UserModelBuilder withRole(RoleModel role) {
    this.user.setRole(role);
    return this;
  }

  public UserModelBuilder isNotRemovable(boolean isNotRemovable) {
    if (isNotRemovable) {
      return thatIsNotRemovable();
    }
    return thatIsRemovable();
  }

  public UserModelBuilder thatIsNotRemovable() {
    this.user.setNotRemovable(true);
    return this;
  }

  public UserModelBuilder thatIsRemovable() {
    this.user.setNotRemovable(false);
    return this;
  }

  public UserModel build() {
    return user;
  }

}
