package it.f2informatica.services.requests;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@EqualsAndHashCode
@ToString
public class ConsultantRequest {

	private String consultantId;

	private String consultantNo;

	private Date registrationDate;

	private String fiscalCode;

	private String email;

	private String firstName;

	private String lastName;

	private String gender;

	private String phoneNumber;

	private String mobileNumber;

	private Date birthDate;

	private String birthCity;

	private String birthCountry;

	private String nationality;

	private String identityCardNo;

	private String passportNo;

	private String maritalStatus;

	private ProfileRequest profile;

	private AddressRequest residence;

	private AddressRequest domicile;

}
