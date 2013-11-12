package it.f2informatica.test.mongodb.domain;

import it.f2informatica.mongodb.domain.Role;
import org.junit.Test;

import static it.f2informatica.test.mongodb.builders.RoleDataBuilder.role;
import static org.fest.assertions.Assertions.assertThat;

public class RoleTest {

	@Test
	public void isRoleNameNull() {
		Role roleWithoutRoleName = role().withoutAuthorization();
		assertThat(roleWithoutRoleName.getName()).isNull();
	}

	@Test
	public void roleId() {
		Role administrator = role().thatIsAdministrator();
		assertThat(administrator.getId()).isEqualTo("527b49ae92bea464ab0d7g23");
	}

	@Test
	public void roleName() {
		Role administrator = role().thatIsAdministrator();
		assertThat(administrator.getName()).isEqualTo("ADMIN");
	}

	@Test
	public void roleEquality() {
		Role administrator = role().thatIsAdministrator();
		Role similarAdministrator = role().withAuthorization("ADMIN").build();
		assertThat(administrator).isEqualTo(similarAdministrator);
	}

	@Test
	public void testRoleInequality() {
		Role administrator = role().thatIsAdministrator();
		Role user = role().withAuthorization("USER").build();
		assertThat(administrator).isNotEqualTo(user);
	}

}
