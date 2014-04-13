package it.f2informatica.core.gateway.mongodb.converter;

import it.f2informatica.mongodb.domain.Role;
import it.f2informatica.mongodb.domain.User;
import it.f2informatica.core.gateway.EntityToModelConverter;
import it.f2informatica.core.model.RoleModel;
import it.f2informatica.core.model.UserModel;
import org.springframework.stereotype.Component;

import static it.f2informatica.core.model.builder.RoleModelBuilder.roleModel;
import static it.f2informatica.core.model.builder.UserModelBuilder.userModel;

@Component("userToModelConverter")
public class MongoDBUserToModelConverter extends EntityToModelConverter<User, UserModel> {

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
				.isNotRemovable(user.isNotRemovable())
				.build();
	}

	private RoleModel buildRole(Role role) {
		return (role == null) ? null :
			roleModel()
				.withId(role.getId())
				.withAuthorization(role.getName())
				.build();
	}

}
