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
package it.f2informatica.test.services.domain.user;

import it.f2informatica.core.gateway.UserRepositoryGateway;
import it.f2informatica.core.model.UpdatePasswordModel;
import it.f2informatica.core.services.PasswordUpdaterService;
import it.f2informatica.core.services.PasswordUpdaterServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static it.f2informatica.test.services.builder.UpdatePasswordRequestBuilder.updatePasswordRequest;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PasswordUpdaterServiceTest {

  @Mock
  private UserRepositoryGateway userRepositoryGateway;

  @InjectMocks
  private PasswordUpdaterService passwordUpdaterService = new PasswordUpdaterServiceImpl();

  @Test
  public void updatePasswordTest() {
    UpdatePasswordModel updatePasswordModel = updatePasswordRequest().build();
    userRepositoryGateway.updatePassword(updatePasswordModel);
    passwordUpdaterService.updatePassword(updatePasswordModel);
  }

}
