package it.f2informatica.mongodb.domain.builder;

import it.f2informatica.mongodb.domain.Address;
import it.f2informatica.mongodb.domain.Consultant;
import it.f2informatica.mongodb.domain.Profile;
import it.f2informatica.mongodb.domain.constants.Gender;
import it.f2informatica.mongodb.domain.constants.MaritalStatus;

import java.util.Date;

public class ConsultantBuilder {

	private Consultant consultant = new Consultant();

	public static ConsultantBuilder consultant() {
		return new ConsultantBuilder();
	}

	public ConsultantBuilder withId(String id) {
		consultant.setId(id);
		return this;
	}

	public ConsultantBuilder withRegistrationDate(Date registrationDate) {
		consultant.setRegistrationDate(registrationDate);
		return this;
	}

	public ConsultantBuilder withConsultantNo(String consultantNo) {
		consultant.setConsultantNo(consultantNo);
		return this;
	}

	public ConsultantBuilder withFiscalCode(String fiscalCode) {
		consultant.setFiscalCode(fiscalCode);
		return this;
	}

	public ConsultantBuilder withEmail(String email) {
		consultant.setEmail(email);
		return this;
	}

	public ConsultantBuilder withFirstName(String firstName) {
		consultant.setFirstName(firstName);
		return this;
	}

	public ConsultantBuilder withLastName(String lastName) {
		consultant.setLastName(lastName);
		return this;
	}

	public ConsultantBuilder withGender(Gender gender) {
		consultant.setGender(gender);
		return this;
	}

	public ConsultantBuilder withPhoneNumber(String phoneNumber) {
		consultant.setPhoneNumber(phoneNumber);
		return this;
	}

	public ConsultantBuilder withMobileNo(String mobileNo) {
		consultant.setMobileNumber(mobileNo);
		return this;
	}

	public ConsultantBuilder withBirthDate(Date birthDate) {
		consultant.setBirthDate(birthDate);
		return this;
	}

	public ConsultantBuilder withBirthCountry(String birthCountry) {
		consultant.setBirthCountry(birthCountry);
		return this;
	}

	public ConsultantBuilder withBirthCity(String birthCity) {
		consultant.setBirthCity(birthCity);
		return this;
	}

	public ConsultantBuilder withIdentityCardNo(String identityCardNo) {
		consultant.setIdentityCardNo(identityCardNo);
		return this;
	}

	public ConsultantBuilder withPassportNo(String passportNo) {
		consultant.setPassportNo(passportNo);
		return this;
	}

	public ConsultantBuilder withMaritalStatus(MaritalStatus maritalStatus) {
		consultant.setMaritalStatus(maritalStatus);
		return this;
	}

	public ConsultantBuilder withProfile(ProfileBuilder profile) {
		return withProfile(profile.build());
	}

	public ConsultantBuilder withProfile(Profile profile) {
		consultant.setProfile(profile);
		return this;
	}

	public ConsultantBuilder withResidence(AddressBuilder residence) {
		return withResidence(residence.build());
	}

	public ConsultantBuilder withResidence(Address residence) {
		consultant.setResidence(residence);
		return this;
	}

	public ConsultantBuilder withDomicile(AddressBuilder domicile) {
		return withDomicile(domicile.build());
	}

	public ConsultantBuilder withDomicile(Address domicile) {
		consultant.setDomicile(domicile);
		return this;
	}

	public Consultant build() {
		return consultant;
	}

}
