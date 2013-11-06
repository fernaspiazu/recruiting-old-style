package it.f2informatica.test.mongodb.domain;

import it.f2informatica.mongodb.domain.Role;
import it.f2informatica.mongodb.domain.User;
import it.f2informatica.test.mongodb.constants.RoleConstants;
import it.f2informatica.test.mongodb.constants.UserConstants;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class UserTest implements UserConstants, RoleConstants {

	User user;
	User user1;
	User user2;
	User user3;

	@Before
	public void setUp() {
		user = createUser(USER_1_USERNAME, USER_1_PASSWORD, null);
		user1 = createUser(USER_2_USERNAME, USER_2_PASSWORD, ROLE_1_NAME);
		user2 = createUser(USER_3_USERNAME, USER_3_PASSWORD, ROLE_2_NAME);
		user3 = createUser(USER_3_USERNAME, USER_3_PASSWORD, ROLE_2_NAME);
	}

	@Test
	public void username() {
		assertThat(user.getUsername()).isEqualTo(USER_1_USERNAME);
	}

	@Test
	public void password() {
		assertThat(user.getPassword()).isEqualTo(USER_1_PASSWORD);
	}

	@Test
	public void areUsersEquals() {
		assertThat(user2).isEqualTo(user3);
	}

	@Test
	public void areUserNotEquals() {
		assertThat(user1).isNotEqualTo(user2);
	}

	@Test
	public void isRemovable() {
		assertThat(user.isRemovable()).isTrue();
		user.setRemovable(false);
		assertThat(user.isRemovable()).isFalse();
	}

	private User createUser(String username, String password, String roleName) {
		User user = new User();
		Role role = new Role();
		role.setName(roleName);
		user.setUsername(username);
		user.setPassword(password);
		user.setRole(role);
		return user;
	}

}
