package it.f2informatica.test.mongodb.domain;

import it.f2informatica.mongodb.domain.Gender;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class GenderTest {

	@Test
	public void genderMaleId() {
		assertThat(Gender.MALE.getId()).isEqualTo(1);
	}

	@Test
	public void genderFemaleId() {
		assertThat(Gender.FEMALE.getId()).isEqualTo(2);
	}

	@Test
	public void genderMaleName() {
		assertThat(Gender.MALE.getName()).isEqualTo("Male");
	}

	@Test
	public void genderFemaleName() {
		assertThat(Gender.FEMALE.getName()).isEqualTo("Female");
	}

	@Test
	public void genderMaleAbbreviation() {
		assertThat(Gender.MALE.getAbbreviation()).isEqualTo("M");
	}

	@Test
	public void genderFemaleAbbreviation() {
		assertThat(Gender.FEMALE.getAbbreviation()).isEqualTo("F");
	}

}
