package it.f2informatica.services.model.builder;

import it.f2informatica.services.model.RoleModel;

public class RoleModelBuilder {
	private RoleModel role = new RoleModel();

	public static RoleModelBuilder roleModel() {
		return new RoleModelBuilder();
	}

	public RoleModelBuilder withId(String id) {
		this.role.setRoleId(id);
		return this;
	}

	public RoleModelBuilder withAuthorization(String authorization) {
		this.role.setRoleName(authorization);
		return this;
	}

	public RoleModel build() {
		return role;
	}
}
