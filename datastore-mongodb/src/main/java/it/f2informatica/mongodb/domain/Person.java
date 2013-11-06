package it.f2informatica.mongodb.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Document
@CompoundIndexes({
		@CompoundIndex(name = "person_idx_1", def = "{'lastName' : 1, 'firstName' : 1}"),
		@CompoundIndex(name = "person_idx_2", def = "{'lastName' : 1, 'firstName' : 1, 'birthCity' : 1}")
})
public class Person extends Identifiable<String> {

	@Indexed(unique = true)
	private String email;

	@Indexed
	private String lastName;

	private String secondLastName;

	@Indexed
	private String firstName;

	private String secondFirstName;

	private Gender gender;

	@Indexed
	private Date birthDate;

	private String birthCountry;

	@Indexed
	private String birthCity;

	private String nationality;

	@Indexed(unique = true)
	private String fiscalCode;

	private String phoneNumber;

	private String mobileNumber;

	private MaritalStatus maritalStatus;

	@Indexed(unique = true)
	private String identityCardNo;

	@Indexed(unique = true)
	private String passportNo;

	@DBRef
	private Address residence;

	@DBRef
	private Address domicile;

	private String photo;

}
