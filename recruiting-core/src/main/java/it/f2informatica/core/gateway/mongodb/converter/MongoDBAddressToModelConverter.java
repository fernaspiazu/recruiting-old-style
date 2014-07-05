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
package it.f2informatica.core.gateway.mongodb.converter;

import it.f2informatica.core.gateway.EntityToModelConverter;
import it.f2informatica.core.model.AddressModel;
import it.f2informatica.mongodb.domain.Address;
import org.springframework.stereotype.Component;

import static it.f2informatica.core.model.builder.AddressModelBuilder.addressModel;

@Component("addressToModelConverter")
public class MongoDBAddressToModelConverter
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
