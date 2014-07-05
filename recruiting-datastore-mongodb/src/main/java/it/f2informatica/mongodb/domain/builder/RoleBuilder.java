/*
 * =============================================================================
 *
 *   Copyright (c) 2014, Fernando Aspiazu
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */
package it.f2informatica.mongodb.domain.builder;

import it.f2informatica.mongodb.domain.Role;

public class RoleBuilder {
	private Role role = new Role();

	public static RoleBuilder role() {
		return new RoleBuilder();
	}

	public Role thatIsAdministrator() {
		this.role.setName("ROLE_ADMIN");
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
