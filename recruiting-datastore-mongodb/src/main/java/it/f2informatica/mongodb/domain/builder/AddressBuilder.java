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
