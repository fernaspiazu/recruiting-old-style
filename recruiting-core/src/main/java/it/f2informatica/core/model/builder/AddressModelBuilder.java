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
