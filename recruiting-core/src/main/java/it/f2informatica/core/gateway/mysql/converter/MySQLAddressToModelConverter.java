package it.f2informatica.core.gateway.mysql.converter;

import it.f2informatica.mysql.domain.Address;
import it.f2informatica.core.gateway.EntityToModelConverter;
import it.f2informatica.core.model.AddressModel;
import org.springframework.stereotype.Component;

import static it.f2informatica.core.model.builder.AddressModelBuilder.addressModel;

@Component("mysqlAddressToModelConverter")
public class MySQLAddressToModelConverter extends EntityToModelConverter<Address, AddressModel> {

  @Override
  public AddressModel convert(Address address) {
    return (address == null) ? null :
      addressModel()
        .withStreet(address.getStreet())
        .withHouseNo(address.getHouseNo())
        .withZipCode(address.getZipCode())
        .withCity(address.getCity())
        .withProvince(address.getProvince())
        .withRegion(address.getRegion())
        .withCountry(address.getCountry())
        .build();
  }

}
