package it.f2informatica.mongodb.domain.builder;

import it.f2informatica.mongodb.domain.Role;
import it.f2informatica.mongodb.domain.User;

public class UserBuilder {

	private User user = new User();

	public static UserBuilder user() {
		return new UserBuilder();
	}

	public UserBuilder withId(String id) {
		this.user.setId(id);
		return this;
	}

	public UserBuilder withUsername(String username) {
		this.user.setUsername(username);
		return this;
	}

	public UserBuilder withPassword(String password) {
		this.user.setPassword(password);
		return this;
	}

	public UserBuilder withEmail(String email) {
		this.user.setEmail(email);
		return this;
	}

	public UserBuilder withFirstName(String firstName) {
		this.user.setFirstName(firstName);
		return this;
	}

	public UserBuilder withLastName(String lastName) {
		this.user.setLastName(lastName);
		return this;
	}

	public UserBuilder withRole(RoleBuilder role) {
		return withRole(role.build());
	}

	public UserBuilder withRole(Role role) {
		this.user.setRole(role);
		return this;
	}

	public UserBuilder thatIsNotRemovable() {
		this.user.setNotRemovable(true);
		return this;
	}

	public UserBuilder thatIsRemovable() {
		this.user.setNotRemovable(false);
		return this;
	}

	public User build() {
		return user;
	}

}
