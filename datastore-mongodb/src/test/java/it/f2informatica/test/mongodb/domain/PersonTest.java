package it.f2informatica.test.mongodb.domain;

import it.f2informatica.mongodb.domain.Address;
import it.f2informatica.mongodb.domain.Person;
import it.f2informatica.test.mongodb.constants.AddressConstants;
import it.f2informatica.test.mongodb.constants.PersonConstants;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class PersonTest implements PersonConstants, AddressConstants {

	Person person;

	@Before
	public void setUp() {
		person = new Person();
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
	}

	@Test
	public void firstName() {
		assertThat(person.getFirstName()).isEqualTo(PERSON_1_FIRSTNAME);
	}

	@Test
	public void lastName() {
		assertThat(person.getLastName()).isEqualTo(PERSON_1_LASTNAME);
	}

	@Test
	public void gender() {
		assertThat(person.getGender()).isEqualTo(PERSON_1_GENDER);
	}

	@Test
	public void maritalStatus() {
		assertThat(person.getMaritalStatus()).isEqualTo(PERSON_1_MARITALSTATUS);
	}

	@Test
	public void residenceStreet() {
		assertThat(person.getResidence().getStreet()).isEqualTo(ADDRESS_3_STREET);
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

}
