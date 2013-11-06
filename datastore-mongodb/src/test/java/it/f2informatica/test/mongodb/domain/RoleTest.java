package it.f2informatica.test.mongodb.domain;

import it.f2informatica.mongodb.domain.Role;
import it.f2informatica.test.mongodb.constants.RoleConstants;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class RoleTest implements RoleConstants {

	Role role;
	Role emptyRole;
	Role copyRole;

	@Before
	public void setUp() {
		emptyRole = new Role();
		role = createRole();
		copyRole = createRole();
	}

	private Role createRole() {
		Role _role = new Role();
		_role.setId(ROLE_1_ID);
		_role.setName(ROLE_1_NAME);
		return _role;
	}

	@Test
	public void isRoleNameNull() {
		assertThat(emptyRole.getName()).isNull();
	}

	@Test
	public void roleId() {
		assertThat(role.getId()).isEqualTo(ROLE_1_ID);
	}

	@Test
	public void roleName() {
		assertThat(role.getName()).isEqualTo(ROLE_1_NAME);
	}

	@Test
	public void roleEquality() {
		assertThat(role).isEqualTo(copyRole);
	}

	@Test
	public void testRoleInequality() {
		Role differentRole = new Role();
		differentRole.setId(ROLE_2_ID);
		differentRole.setName(ROLE_2_NAME);
		assertThat(differentRole).isNotEqualTo(role);
	}

}
