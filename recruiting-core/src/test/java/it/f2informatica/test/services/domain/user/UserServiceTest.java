package it.f2informatica.test.services.domain.user;

import com.google.common.collect.Lists;
import it.f2informatica.core.Authority;
import it.f2informatica.core.gateway.UserRepositoryGateway;
import it.f2informatica.core.model.RoleModel;
import it.f2informatica.core.model.UserModel;
import it.f2informatica.core.model.builder.RoleModelBuilder;
import it.f2informatica.core.services.UserService;
import it.f2informatica.core.services.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static it.f2informatica.core.model.builder.RoleModelBuilder.roleModel;
import static it.f2informatica.core.model.builder.UserModelBuilder.userModel;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

  @Mock
  private UserRepositoryGateway userRepositoryGateway;

  @InjectMocks
  private UserService userService = new UserServiceImpl();

  @Test
  public void findUserById() {
    when(userRepositoryGateway.findUserById(anyString())).thenReturn(getUserModel());
    UserModel userResponse = userService.findUserById("1234567890");
    assertThat(userResponse.getUsername()).isEqualTo("jhon");
  }

  @Test
  public void findByUsername() {
    when(userRepositoryGateway.findByUsername(anyString())).thenReturn(getUserModel());
    UserModel userResponse = userService.findByUsername("jhon");
    assertThat(userResponse.getUsername()).isEqualTo("jhon");
  }

  @Test
  public void findByUsernameAndPassword() {
    when(userRepositoryGateway.findByUsernameAndPassword(anyString(), anyString())).thenReturn(getUserModel());
    UserModel userResponse = userService.findByUsernameAndPassword("jhon", "jhon78*");
    assertThat(userResponse.getUsername()).isEqualTo("jhon");
  }

  @Test
  public void findAllWithPageable() {
    PageImpl<UserModel> paginatedResult = new PageImpl<>(getUserList().subList(0, 10));
    when(userRepositoryGateway.findAllExcludingCurrentUser(any(Pageable.class), anyString())).thenReturn(paginatedResult);
    Page<UserModel> users = userService.findAllExcludingCurrentUser(new PageRequest(1, 10), "jhon");
    assertThat(users.getContent()).hasSize(10);
  }

  @Test
  public void findUserByRoleName() {
    String roleAdmin = Authority.ROLE_ADMIN.toString();
    when(userRepositoryGateway.findUsersByRoleName(roleAdmin)).thenReturn(getUserList().subList(0, 2));
    assertThat(userService.findUsersByRoleName(roleAdmin)).hasSize(2);
  }

  @Test
  public void saveUser() {
    when(userRepositoryGateway.saveUser(any(UserModel.class))).thenReturn(getUserModel());
    UserModel userModelSaved = userService.saveUser(userModel().build());
    assertThat(userModelSaved.getUsername()).isEqualTo("jhon");
  }

  @Test
  public void loadRoles() {
    List<RoleModel> roles = Lists.newArrayList(
      roleModel().withId("12345").withAuthorization("Administrator").build(),
      roleModel().withId("12345").withAuthorization("User").build()
    );
    when(userRepositoryGateway.loadRoles()).thenReturn(roles);
    assertThat(userService.loadRoles()).hasSize(2)
      .contains(roleModel().withId("12345").withAuthorization("User").build());
  }

  @Test
  public void findRoleByName() {
    when(userRepositoryGateway.findRoleByName("Administrator"))
      .thenReturn(roleModel().withId("12345").withAuthorization("Administrator").build());
    RoleModel response = userService.findRoleByName("Administrator");
    assertThat(response.getRoleName()).isEqualTo("Administrator");
  }

  @Test
  public void deleteUserByUserId() {
    ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
    userService.deleteUser("1234567890");
    verify(userRepositoryGateway).deleteUser(argument.capture());
    assertThat("1234567890").isEqualTo(argument.getValue());
  }

  private UserModel getUserModel() {
    return userModel()
      .withId("1234567890")
      .withUsername("jhon")
      .withPassword("jhon78*")
      .withRole(roleModel().withId("12345").withAuthorization("User"))
      .build();
  }

  private List<UserModel> getUserList() {
    RoleModelBuilder adminRole = roleModel().withId("12345").withAuthorization("Administrator");
    RoleModelBuilder userRole = roleModel().withId("12345").withAuthorization("User");
    return Lists.newArrayList(
      userModel().withUsername("user1").withPassword("password1").withRole(adminRole).build(),
      userModel().withUsername("user2").withPassword("password2").withRole(adminRole).build(),
      userModel().withUsername("user3").withPassword("password3").withRole(userRole).build(),
      userModel().withUsername("user4").withPassword("password4").withRole(userRole).build(),
      userModel().withUsername("user5").withPassword("password5").withRole(userRole).build(),
      userModel().withUsername("user6").withPassword("password6").withRole(userRole).build(),
      userModel().withUsername("user7").withPassword("password7").withRole(userRole).build(),
      userModel().withUsername("user8").withPassword("password8").withRole(userRole).build(),
      userModel().withUsername("user9").withPassword("password9").withRole(userRole).build(),
      userModel().withUsername("user10").withPassword("password10").withRole(userRole).build(),
      userModel().withUsername("user11").withPassword("password11").withRole(userRole).build(),
      userModel().withUsername("user12").withPassword("password12").withRole(userRole).build(),
      userModel().withUsername("user13").withPassword("password13").withRole(userRole).build(),
      userModel().withUsername("user14").withPassword("password14").withRole(userRole).build(),
      userModel().withUsername("user15").withPassword("password15").withRole(userRole).build()
    );
  }

}
