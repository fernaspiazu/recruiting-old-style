package it.f2informatica.services.requests.builders;

import it.f2informatica.services.requests.AddressRequest;

public class AddressRequestBuilder {

	private AddressRequest address = new AddressRequest();

	public static AddressRequestBuilder addressRequest() {
		return new AddressRequestBuilder();
	}

	public AddressRequestBuilder withStreet(String street) {
		address.setStreet(street);
		return this;
	}

	public AddressRequestBuilder withHouseNo(String houseNo) {
		address.setHouseNo(houseNo);
		return this;
	}

	public AddressRequestBuilder withZipCode(String zipCode) {
		address.setZipCode(zipCode);
		return this;
	}

	public AddressRequestBuilder withCity(String city) {
		address.setCity(city);
		return this;
	}

	public AddressRequestBuilder withProvince(String province) {
		address.setProvince(province);
		return this;
	}

	public AddressRequestBuilder withRegion(String region) {
		address.setRegion(region);
		return this;
	}

	public AddressRequestBuilder withCountry(String country) {
		address.setCountry(country);
		return this;
	}

	public AddressRequest build() {
		return address;
	}

}
