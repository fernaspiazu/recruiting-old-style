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

import it.f2informatica.core.model.UpdatePasswordModel;

public class UpdatePasswordRequestBuilder {

  private String userId = "52820f6f34bdf55624303fc1";
  private String currentPassword = "currentPassword";
  private String newPassword = "newPassword";
  private String passwordConfirmed = "newPassword";

  public static UpdatePasswordRequestBuilder updatePasswordRequest() {
    return new UpdatePasswordRequestBuilder();
  }

  public UpdatePasswordRequestBuilder withUserId(String userId) {
    this.userId = userId;
    return this;
  }

  public UpdatePasswordRequestBuilder withCurrentPassword(String currentPassword) {
    this.currentPassword = currentPassword;
    return this;
  }

  public UpdatePasswordRequestBuilder withoutNewPassword() {
    return withNewPassword(null);
  }

  public UpdatePasswordRequestBuilder withNewPassword(String newPassword) {
    this.newPassword = newPassword;
    return this;
  }

  public UpdatePasswordRequestBuilder withoutConfirmedPassword() {
    return withConfirmedPassword(null);
  }

  public UpdatePasswordRequestBuilder withConfirmedPassword(String confirmedPassword) {
    this.passwordConfirmed = confirmedPassword;
    return this;
  }

  public UpdatePasswordModel build() {
    UpdatePasswordModel request = new UpdatePasswordModel();
    request.setUserId(userId);
    request.setCurrentPassword(currentPassword);
    request.setNewPassword(newPassword);
    request.setPasswordConfirmed(passwordConfirmed);
    return request;
  }

}
