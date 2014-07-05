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
package it.f2informatica.core.gateway.mysql.converter;

import it.f2informatica.core.gateway.EntityToModelConverter;
import it.f2informatica.core.model.RoleModel;
import it.f2informatica.core.model.UserModel;
import it.f2informatica.mysql.domain.Role;
import it.f2informatica.mysql.domain.User;
import org.springframework.stereotype.Component;

import static it.f2informatica.core.model.builder.RoleModelBuilder.roleModel;
import static it.f2informatica.core.model.builder.UserModelBuilder.userModel;

@Component("mysqlUserToModelConverter")
public class MySQLUserToModelConverter extends EntityToModelConverter<User, UserModel> {

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
				.isNotRemovable(false)
				.build();
	}

	private RoleModel buildRole(Role role) {
		return (role == null) ? null :
			roleModel()
				.withId(String.valueOf(role.getId()))
				.withAuthorization(role.getName())
				.build();
	}

}
