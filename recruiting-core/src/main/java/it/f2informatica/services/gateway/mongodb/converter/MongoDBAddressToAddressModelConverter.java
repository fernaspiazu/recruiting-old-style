package it.f2informatica.services.gateway.mongodb.converter;

import it.f2informatica.mongodb.domain.Address;
import it.f2informatica.services.gateway.EntityToModelConverter;
import it.f2informatica.services.model.AddressModel;

import static it.f2informatica.services.model.builder.AddressModelBuilder.addressModel;

public class MongoDBAddressToAddressModelConverter
		extends EntityToModelConverter<Address, AddressModel> {

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
