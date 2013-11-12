package it.f2informatica.test.mongodb.domain;

import it.f2informatica.mongodb.domain.User;
import org.junit.Test;

import static it.f2informatica.test.mongodb.builders.RoleDataBuilder.role;
import static it.f2informatica.test.mongodb.builders.UserDataBuilder.user;
import static org.fest.assertions.Assertions.assertThat;

public class UserTest {

	@Test
	public void usernameTest() {
		User user = user().build();
		assertThat(user.getUsername()).isEqualTo("jhon_kent77");
	}

	@Test
	public void passwordTest() {
		User user = user().withPassword("123456").build();
		assertThat(user.getPassword()).isEqualTo("123456");
	}

	@Test
	public void firstNameTest() {
		User user = user().withFirstName("Maria").build();
		assertThat("Maria").isEqualTo(user.getFirstName());
	}

	@Test
	public void lastNameTest() {
		User user = user().withLastName("Lambda").build();
		assertThat("Lambda").isEqualTo(user.getLastName());
	}

	@Test
	public void emailTest() {
		User user = user().withEmail("karmaliu_77@aol.com").build();
		assertThat("karmaliu_77@aol.com").isEqualTo(user.getEmail());
	}

	@Test
	public void roleTest() {
		User user = user().withRole(role().thatIsAdministrator()).build();
		assertThat(user.getRole().getName()).isEqualTo("ADMIN");
	}

	@Test
	public void areUsersEquals() {
		User user = user().build();
		assertThat(user).isEqualTo(user().build());
	}

	@Test
	public void areUserNotEquals() {
		User user = user().withUsername("fabian").build();
		User differentUser = user().withUsername("francesco").build();
		assertThat(user).isNotEqualTo(differentUser);
	}

	@Test
	public void isNotRemovable() {
		User user = user().thatIsNotRemovable().build();
		assertThat(user.isNotRemovable()).isTrue();
	}

	@Test
	public void isRemovable() {
		User user = user().thatIsRemovable().build();
		assertThat(user.isNotRemovable()).isFalse();
	}

}
