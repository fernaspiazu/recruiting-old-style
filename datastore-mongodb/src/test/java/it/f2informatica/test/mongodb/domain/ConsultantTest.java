package it.f2informatica.test.mongodb.domain;

import com.google.common.collect.Lists;
import it.f2informatica.mongodb.domain.*;
import it.f2informatica.test.mongodb.constants.AddressConstants;
import it.f2informatica.test.mongodb.constants.ConsultantConstants;
import it.f2informatica.test.mongodb.constants.PersonConstants;
import it.f2informatica.test.mongodb.constants.SkillConstants;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static it.f2informatica.utils.date.DateHelper.dateEquals;
import static org.fest.assertions.Assertions.assertThat;

public class ConsultantTest
		implements ConsultantConstants, PersonConstants,
		AddressConstants, SkillConstants {

	Consultant consultant;

	@Before
	public void setUp() {
		consultant = new Consultant();
		consultant.setConsultantNo(CONSULTANT_1_CONSULTANTNO);
		consultant.setRegistrationDate(CONSULTANT_1_REGISTRATIONDATE);
		consultant.setStartActivityDate(CONSULTANT_1_STARTACTIVITYDATE);
		consultant.setEnabled(CONSULTANT_1_ENABLED);
		consultant.setPerson(createPerson());
		consultant.setProfile(createProfile("Developer", "Software Engineer"));
		consultant.setSkills(createSkills());
	}

	@Test
	public void consultantNo() {
		assertThat(consultant.getConsultantNo())
				.isEqualTo(CONSULTANT_1_CONSULTANTNO);
	}

	@Test
	public void registrationDate() {
		assertThat(dateEquals(consultant.getRegistrationDate(), CONSULTANT_1_REGISTRATIONDATE));
	}

	@Test
	public void startActivityDate() {
		assertThat(dateEquals(consultant.getStartActivityDate(), CONSULTANT_1_STARTACTIVITYDATE));
	}

	@Test
	public void isItEnabled() {
		assertThat(consultant.isEnabled());
	}

	@Test
	public void howManySkillsHasConsultant() {
		assertThat(consultant.getSkills()).hasSize(3);
	}

	@Test
	public void doSkillsContainSomeOtherSkills() {
		assertThat(consultant.getSkills())
				.contains(
						new Skill(SKILL_JAVA, 10),
						new Skill(SKILL_CLOJURE, 8)
				);
	}

	public Person createPerson() {
		Person person = new Person();
		person.setFirstName(PERSON_1_FIRSTNAME);
		person.setLastName(PERSON_1_LASTNAME);
		person.setGender(PERSON_1_GENDER);
		person.setBirthDate(PERSON_1_BIRTHDATE);
		person.setBirthCity(PERSON_1_BIRTHCITY);
		person.setBirthCountry(PERSON_1_BIRTHCOUNTRY);
		person.setMaritalStatus(PERSON_1_MARITALSTATUS);
		person.setResidence(createAddress(
				ADDRESS_3_STREET,
				ADDRESS_3_HOUSENO,
				ADDRESS_3_ZIPCODE,
				ADDRESS_3_CITY,
				ADDRESS_3_PROVINCE,
				ADDRESS_3_REGION,
				ADDRESS_3_COUNTRY
		));
		return person;
	}

	private Address createAddress(
			String street,
			String houseNo,
			String zipCode,
			String city,
			String province,
			String region,
			String country) {
		Address address = new Address();
		address.setStreet(street);
		address.setHouseNo(houseNo);
		address.setZipCode(zipCode);
		address.setCity(city);
		address.setProvince(province);
		address.setRegion(region);
		address.setCountry(country);
		return address;
	}

	private Profile createProfile(String profileName, String title) {
		Profile profile = new Profile();
		profile.setProfileName(profileName);
		profile.setTitle(title);
		return profile;
	}

	private List<Skill> createSkills() {
		return Lists.newArrayList(
				new Skill(SKILL_JAVA, 10),
				new Skill(SKILL_ORACLE, 8),
				new Skill(SKILL_CLOJURE, 8)
		);
	}

}
