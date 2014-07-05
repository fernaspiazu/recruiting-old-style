/*
 * =============================================================================
 *
 *   Copyright (c) 2014, Fernando Aspiazu
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */
package it.f2informatica.core.model.builder;

import it.f2informatica.core.model.AddressModel;

public class AddressModelBuilder {

	private AddressModel address = new AddressModel();

	public static AddressModelBuilder addressModel() {
		return new AddressModelBuilder();
	}

	public AddressModelBuilder withStreet(String street) {
		address.setStreet(street);
		return this;
	}

	public AddressModelBuilder withHouseNo(String houseNo) {
		address.setHouseNo(houseNo);
		return this;
	}

	public AddressModelBuilder withZipCode(String zipCode) {
		address.setZipCode(zipCode);
		return this;
	}

	public AddressModelBuilder withCity(String city) {
		address.setCity(city);
		return this;
	}

	public AddressModelBuilder withProvince(String province) {
		address.setProvince(province);
		return this;
	}

	public AddressModelBuilder withRegion(String region) {
		address.setRegion(region);
		return this;
	}

	public AddressModelBuilder withCountry(String country) {
		address.setCountry(country);
		return this;
	}

	public AddressModel build() {
		return address;
	}

}
