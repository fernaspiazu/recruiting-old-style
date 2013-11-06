package it.f2informatica.test.mongodb.domain;

import it.f2informatica.mongodb.domain.Address;
import it.f2informatica.test.mongodb.constants.AddressConstants;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class AddressTest {

	Address address1;
	Address address2;

	@Before
	public void setUp() {
		address1 = createAddress(
				AddressConstants.ADDRESS_1_STREET,
				AddressConstants.ADDRESS_1_HOUSENO,
				AddressConstants.ADDRESS_1_ZIPCODE,
				AddressConstants.ADDRESS_1_CITY,
				AddressConstants.ADDRESS_1_PROVINCE,
				AddressConstants.ADDRESS_1_REGION,
				AddressConstants.ADDRESS_1_COUNTRY
		);
		address2 = createAddress(
				AddressConstants.ADDRESS_2_STREET,
				AddressConstants.ADDRESS_2_HOUSENO,
				AddressConstants.ADDRESS_2_ZIPCODE,
				AddressConstants.ADDRESS_2_CITY,
				AddressConstants.ADDRESS_2_PROVINCE,
				AddressConstants.ADDRESS_2_REGION,
				AddressConstants.ADDRESS_2_COUNTRY
		);
	}

	@Test
	public void addressZipCode() {
		assertThat(address1.getZipCode()).isEqualTo(AddressConstants.ADDRESS_1_ZIPCODE);
	}

	@Test
	public void addressEquals() {
		Address address = createAddress(
				AddressConstants.ADDRESS_2_STREET,
				AddressConstants.ADDRESS_2_HOUSENO,
				AddressConstants.ADDRESS_2_ZIPCODE,
				AddressConstants.ADDRESS_2_CITY,
				AddressConstants.ADDRESS_2_PROVINCE,
				AddressConstants.ADDRESS_2_REGION,
				AddressConstants.ADDRESS_2_COUNTRY
		);
		assertThat(address).isEqualTo(address2);
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
