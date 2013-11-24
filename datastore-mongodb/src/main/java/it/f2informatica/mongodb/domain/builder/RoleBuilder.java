package it.f2informatica.mongodb.domain.builder;

import it.f2informatica.mongodb.domain.Role;
import it.f2informatica.mongodb.domain.constants.Authority;

public class RoleBuilder {
	private Role role = new Role();

	public static RoleBuilder role() {
		return new RoleBuilder();
	}

	public Role thatIsAdministrator() {
		this.role.setName(Authority.ROLE_ADMIN.toString());
		return this.build();
	}

	public RoleBuilder withId(String id) {
		this.role.setId(id);
		return this;
	}

	public RoleBuilder withAuthorization(String authorization) {
		this.role.setName(authorization);
		return this;
	}

	public Role build() {
		return role;
	}
}
