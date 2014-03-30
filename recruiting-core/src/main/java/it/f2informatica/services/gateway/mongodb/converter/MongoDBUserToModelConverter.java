package it.f2informatica.services.gateway.mongodb.converter;

import it.f2informatica.mongodb.domain.Role;
import it.f2informatica.mongodb.domain.User;
import it.f2informatica.services.gateway.EntityToModelConverter;
import it.f2informatica.services.model.RoleModel;
import it.f2informatica.services.model.UserModel;

import static it.f2informatica.services.model.builder.RoleModelBuilder.roleModel;
import static it.f2informatica.services.model.builder.UserModelBuilder.userModel;

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
