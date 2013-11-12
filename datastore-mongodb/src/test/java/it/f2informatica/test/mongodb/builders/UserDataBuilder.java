package it.f2informatica.test.mongodb.builders;

import it.f2informatica.mongodb.domain.Role;
import it.f2informatica.mongodb.domain.User;

public class UserDataBuilder {
	private String id = "52820f6f34bdf55624303fc1";
	private String username = "jhon_kent77";
	private String password = "okisteralio";
	private String firstName = "Jhon";
	private String lastName = "Kent";
	private String email = "jhon_kent77@aol.com";
	private boolean notRemovable = false;
	private Role role = new RoleDataBuilder().build();

	public static UserDataBuilder user() {
		return new UserDataBuilder();
	}

	public UserDataBuilder withId(String id) {
		this.id = id;
		return this;
	}

	public UserDataBuilder withUsername(String username) {
		this.username = username;
		return this;
	}

	public UserDataBuilder withPassword(String password) {
		this.password = password;
		return this;
	}

	public UserDataBuilder withEmail(String email) {
		this.email = email;
		return this;
	}

	public UserDataBuilder withFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public UserDataBuilder withLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public UserDataBuilder withRole(Role role) {
		this.role = role;
		return this;
	}

	public UserDataBuilder withRole(RoleDataBuilder role) {
		this.role = role.build();
		return this;
	}

	public UserDataBuilder thatIsNotRemovable() {
		this.notRemovable = true;
		return this;
	}

	public UserDataBuilder thatIsRemovable() {
		this.notRemovable = false;
		return this;
	}

	public User build() {
		User user = new User();
		user.setId(id);
		user.setUsername(username);
		user.setPassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setNotRemovable(notRemovable);
		user.setRole(role);
		return user;
	}

}
