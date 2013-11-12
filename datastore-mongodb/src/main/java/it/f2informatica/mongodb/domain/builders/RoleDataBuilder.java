package it.f2informatica.mongodb.domain.builders;

import it.f2informatica.mongodb.domain.Role;

public class RoleDataBuilder {
	private String id = "52820f3834bdf55624303fbe";
	private String name = "ADMIN";

	public static RoleDataBuilder role() {
		return new RoleDataBuilder();
	}

	public Role thatIsAdministrator() {
		return this.build();
	}

	public RoleDataBuilder withId(String id) {
		this.id = id;
		return this;
	}

	public RoleDataBuilder withAuthorization(String authorization) {
		this.name = authorization;
		return this;
	}

	public Role withoutAuthorization() {
		this.name = null;
		return this.build();
	}

	public Role build() {
		Role role = new Role();
		role.setId(id);
		role.setName(name);
		return role;
	}
}
