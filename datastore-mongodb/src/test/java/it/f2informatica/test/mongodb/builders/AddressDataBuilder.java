package it.f2informatica.test.mongodb.builders;

import it.f2informatica.mongodb.domain.Address;

public class AddressDataBuilder {

	private String street = "Via Ancona";
	private String houseNo = "10";
	private String zipCode = "45100";
	private String city = "Rovigo";
	private String province = "Rovigo";
	private String region = "Reggio Emilia";
	private String country = "Italy";

	public static AddressDataBuilder address() {
		return new AddressDataBuilder();
	}

	public AddressDataBuilder withStreet(String street) {
		this.street = street;
		return this;
	}

	public AddressDataBuilder withHouseNo(String houseNo) {
		this.houseNo = houseNo;
		return this;
	}

	public AddressDataBuilder withZipCode(String zipCode) {
		this.zipCode = zipCode;
		return this;
	}

	public AddressDataBuilder withCity(String city) {
		this.city = city;
		return this;
	}

	public AddressDataBuilder withProvince(String province) {
		this.province = province;
		return this;
	}

	public AddressDataBuilder withRegion(String region) {
		this.region = region;
		return this;
	}

	public AddressDataBuilder withCountry(String country) {
		this.country = country;
		return this;
	}

	public Address build() {
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
