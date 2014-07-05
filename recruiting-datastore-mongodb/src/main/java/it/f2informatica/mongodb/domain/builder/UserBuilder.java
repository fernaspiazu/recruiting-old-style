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
