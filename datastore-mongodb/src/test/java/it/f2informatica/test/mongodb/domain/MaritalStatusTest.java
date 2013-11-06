package it.f2informatica.test.mongodb.domain;

import it.f2informatica.mongodb.domain.MaritalStatus;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class MaritalStatusTest {

	@Test
	public void singleId() {
		assertThat(MaritalStatus.SINGLE.getId()).isEqualTo(1);
	}

	@Test
	public void singleName() {
		assertThat(MaritalStatus.SINGLE.getName()).isEqualTo("Single");
	}

	@Test
	public void engagedId() {
		assertThat(MaritalStatus.ENGAGED.getId()).isEqualTo(2);
	}

	@Test
	public void engagedName() {
		assertThat(MaritalStatus.ENGAGED.getName()).isEqualTo("Engaged");
	}

	@Test
	public void marriedId() {
		assertThat(MaritalStatus.MARRIED.getId()).isEqualTo(3);
	}

	@Test
	public void marriedName() {
		assertThat(MaritalStatus.MARRIED.getName()).isEqualTo("Married");
	}

	@Test
	public void divorcedId() {
		assertThat(MaritalStatus.DIVORCED.getId()).isEqualTo(4);
	}

	@Test
	public void divorcedName() {
		assertThat(MaritalStatus.DIVORCED.getName()).isEqualTo("Divorced");
	}

	@Test
	public void widowedId() {
		assertThat(MaritalStatus.WIDOWED.getId()).isEqualTo(5);
	}

	@Test
	public void widowedName() {
		assertThat(MaritalStatus.WIDOWED.getName()).isEqualTo("Widowed");
	}

}
