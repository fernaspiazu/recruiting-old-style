package it.f2informatica.services.model;

import it.f2informatica.datastore.model.DataModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class AddressModel implements DataModel {
	private static final long serialVersionUID = 8584553310786911492L;

	private String street;

	private String houseNo;

	private String zipCode;

	private String city;

	private String province;

	private String region;

	private String country;

}
