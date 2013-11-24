package it.f2informatica.mongodb.domain.builder;

import it.f2informatica.mongodb.domain.Address;

public class AddressBuilder {

	private Address address = new Address();

	public static AddressBuilder anAddress() {
		return new AddressBuilder();
	}

	public AddressBuilder withStreet(String street) {
		address.setStreet(street);
		return this;
	}

	public AddressBuilder withHouseNo(String houseNo) {
		address.setHouseNo(houseNo);
		return this;
	}

	public AddressBuilder withZipCode(String zipCode) {
		address.setZipCode(zipCode);
		return this;
	}

	public AddressBuilder withCity(String city) {
		address.setCity(city);
		return this;
	}

	public AddressBuilder withProvince(String province) {
		address.setProvince(province);
		return this;
	}

	public AddressBuilder withRegion(String region) {
		address.setRegion(region);
		return this;
	}

	public AddressBuilder withCountry(String country) {
		address.setCountry(country);
		return this;
	}

	public Address build() {
		return address;
	}

}
