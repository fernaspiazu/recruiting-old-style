package it.f2informatica.mongodb.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Document
@CompoundIndexes({
		@CompoundIndex(name = "address_idx_1", def = "{'street' : 1, 'city' : 1}"),
		@CompoundIndex(name = "address_idx_2", def = "{'street' : 1, 'houseNo' : 1, 'city' : 1}"),
		@CompoundIndex(name = "address_idx_3", def = "{'street' : 1, 'houseNo' : 1, 'city' : 1, 'province' : 1}"),
		@CompoundIndex(name = "address_idx_4", def = "{'street' : 1, 'houseNo' : 1, 'city' : 1, 'province' : 1, 'region' : 1}")
})
public class Address extends Identifiable<String> {

	@Indexed
	private String street;

	private String houseNo;

	private String zipCode;

	@Indexed
	private String city;

	@Indexed
	private String province;

	@Indexed
	private String region;

	@Indexed
	private String country;

}
