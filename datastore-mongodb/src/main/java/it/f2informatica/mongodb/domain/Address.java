package it.f2informatica.mongodb.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class Address {

	private String street;

	private String houseNo;

	private String zipCode;

	private String city;

	private String province;

	private String region;

	private String country;

}
