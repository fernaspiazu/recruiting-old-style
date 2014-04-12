package it.f2informatica.services.gateway.mysql.converter;

import it.f2informatica.mysql.domain.Role;
import it.f2informatica.mysql.domain.User;
import it.f2informatica.services.gateway.EntityToModelConverter;
import it.f2informatica.services.model.RoleModel;
import it.f2informatica.services.model.UserModel;
import org.springframework.stereotype.Component;

import static it.f2informatica.services.model.builder.RoleModelBuilder.roleModel;
import static it.f2informatica.services.model.builder.UserModelBuilder.userModel;

@Component("mysqlUserToModelConverter")
public class MySQLUserToModelConverter extends EntityToModelConverter<User, UserModel> {

  @Override
  public UserModel convert(User user) {
    return (user == null) ? null :
      userModel()
        .withId(String.valueOf(user.getId()))
        .withUsername(user.getUsername())
        .withPassword(user.getPassword())
        .withFirstName(user.getFirstName())
        .withLastName(user.getLastName())
        .withEmail(user.getEmail())
        .withRole(buildRole(user.getRole()))
        .isNotRemovable(false)
        .build();
  }

  private RoleModel buildRole(Role role) {
    return (role == null) ? null :
      roleModel()
        .withId(String.valueOf(role.getId()))
        .withAuthorization(role.getName())
        .build();
  }

}
