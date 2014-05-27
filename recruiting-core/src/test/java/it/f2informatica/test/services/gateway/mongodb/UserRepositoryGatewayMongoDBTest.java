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
package it.f2informatica.test.services.gateway.mongodb;

import com.google.common.collect.Lists;
import com.mongodb.CommandResult;
import com.mongodb.WriteResult;
import it.f2informatica.core.Authority;
import it.f2informatica.core.gateway.EntityToModelConverter;
import it.f2informatica.core.gateway.UserRepositoryGateway;
import it.f2informatica.core.gateway.mongodb.UserRepositoryGatewayMongoDB;
import it.f2informatica.core.model.RoleModel;
import it.f2informatica.core.model.UserModel;
import it.f2informatica.mongodb.domain.Role;
import it.f2informatica.mongodb.domain.User;
import it.f2informatica.mongodb.repositories.RoleRepository;
import it.f2informatica.mongodb.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

import static it.f2informatica.mongodb.domain.builder.RoleBuilder.role;
import static it.f2informatica.mongodb.domain.builder.UserBuilder.user;
import static it.f2informatica.test.services.builder.UserModelDataBuilder.userModel;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryGatewayMongoDBTest {

  @Mock
  private MongoTemplate mongoTemplate;

  @Mock
  private UserRepository userRepository;

  @Mock
  private RoleRepository roleRepository;

  @Mock
  private EntityToModelConverter<User, UserModel> userToModelConverter;

  @InjectMocks
  private UserRepositoryGateway userRepositoryGateway = new UserRepositoryGatewayMongoDB();

  @Test
  public void findUserById() {
    when(userRepository.findOne(anyString())).thenReturn(getUser());
    when(userToModelConverter.convert(getUser())).thenReturn(userModel().build());
    UserModel userResponse = userRepositoryGateway.findUserById("52820f6f34bdf55624303fc1");
    assertThat(userResponse.getUsername()).isEqualTo("jhon_kent77");
  }

  @Test
  public void findByUsername() {
    when(userRepository.findByUsername(anyString())).thenReturn(getUser());
    when(userToModelConverter.convert(getUser())).thenReturn(userModel().build());
    UserModel userResponse = userRepositoryGateway.findByUsername("jhon_kent77");
    assertThat(userResponse.getUsername()).isEqualTo("jhon_kent77");
  }

  @Test
  public void findByUsernameAndPassword() {
    when(userRepository.findByUsernameAndPassword(anyString(), anyString())).thenReturn(getUser());
    when(userToModelConverter.convert(getUser())).thenReturn(userModel().build());
    UserModel userResponse = userRepositoryGateway.findByUsernameAndPassword("jhon_kent77", "okisteralio");
    assertThat(userResponse.getUsername()).isEqualTo("jhon_kent77");
  }

  @Test
  public void saveUser() {
    User user = getUser();
    when(userRepository.save(any(User.class))).thenReturn(user);
    when(userToModelConverter.convert(user)).thenReturn(userModel().build());
    UserModel userModelSaved = userRepositoryGateway.saveUser(userModel().build());
    assertThat(userModelSaved.getUsername()).isEqualTo("jhon_kent77");
  }

  private User getUser() {
    return user()
      .withId("52820f6f34bdf55624303fc1")
      .withUsername("jhon_kent77")
      .withPassword("okisteralio")
      .withRole(role().thatIsAdministrator())
      .build();
  }

  @Test
  public void loadRoles() {
    String userAuthority = Authority.ROLE_USER.toString();
    RoleModel roleModel = new RoleModel();
    roleModel.setRoleName(userAuthority);
    List<Role> roles = Lists.newArrayList(
      role().thatIsAdministrator(),
      role().withAuthorization(userAuthority).build()
    );
    when(roleRepository.findAll()).thenReturn(roles);
    assertThat(userRepositoryGateway.loadRoles()).hasSize(2).contains(roleModel);
  }

  @Test
  public void findRoleByName() {
    String roleAdmin = Authority.ROLE_ADMIN.toString();
    when(roleRepository.findByName(roleAdmin)).thenReturn(role().thatIsAdministrator());
    RoleModel response = userRepositoryGateway.findRoleByName(roleAdmin);
    assertThat(response.getRoleName()).isEqualTo("ROLE_ADMIN");
  }

  @Test
  public void deleteUserByUserId() {
    ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
    userRepositoryGateway.deleteUser("1234567890");
    verify(userRepository).deleteRemovableUser(argument.capture());
    assertThat("1234567890").isEqualTo(argument.getValue());
  }

  @Test
  public void updateUser() {
    stubUpdateSuccess();
    userRepositoryGateway.updateUser(userModel().build());
  }

  private void stubUpdateSuccess() {
    WriteResult writeResultMock = mock(WriteResult.class);
    CommandResult commandResult = mock(CommandResult.class);
    when(writeResultMock.getLastError()).thenReturn(commandResult);
    when(commandResult.ok()).thenReturn(true);
    when(mongoTemplate.updateFirst(
        any(Query.class),
        any(Update.class),
        any(Class.class))
    ).thenReturn(writeResultMock);
  }

}
