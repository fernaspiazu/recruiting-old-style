package it.f2informatica.services.model;

import it.f2informatica.datastore.constant.Gender;
import it.f2informatica.datastore.constant.MaritalStatus;
import it.f2informatica.datastore.model.DataModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@EqualsAndHashCode
@ToString
public class ConsultantModel implements DataModel {

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

	private int age;

	private String birthCity;

	private String birthCountry;

	private String nationality;

	private String identityCardNo;

	private String passportNo;

	private MaritalStatus maritalStatus;

	private ProfileModel profile = new ProfileModel();

	private AddressModel residence = new AddressModel();

	private AddressModel domicile = new AddressModel();

	private String curriculum; // TODO: GridFSFile

	private String photo; // TODO: GridFSFile

	public String getConsultantFullName() {
		return lastName + " " + firstName;
	}

}
