package it.f2informatica.services.model;

import it.f2informatica.mongodb.domain.constants.Gender;
import it.f2informatica.mongodb.domain.constants.MaritalStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@EqualsAndHashCode
@ToString
public class ConsultantModel {

	private String id;

	private String consultantNo;

	private Date registrationDate;

	private String fiscalCode;

	private String email;

	private String firstName;

	private String lastName;

	private Gender gender;

	private String phoneNumber;

	private String mobileNumber;

	private Date birthDate;

	private String birthCity;

	private String birthCountry;

	private String nationality;

	private String identityCardNo;

	private String passportNo;

	private MaritalStatus maritalStatus;

	private ProfileModel profile;

	private AddressModel residence;

	private AddressModel domicile;

	private String curriculum; // TODO: GridFSFile

	private String photo; // TODO: GridFSFile

}
