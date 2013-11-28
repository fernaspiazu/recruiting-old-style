package it.f2informatica.services.model.builder;

import it.f2informatica.services.model.RoleModel;
import it.f2informatica.services.model.UserModel;

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
