package it.f2informatica.services.model.builder;

import it.f2informatica.mongodb.domain.constants.Gender;
import it.f2informatica.mongodb.domain.constants.MaritalStatus;
import it.f2informatica.services.model.AddressModel;
import it.f2informatica.services.model.ConsultantModel;
import it.f2informatica.services.model.ProfileModel;

import java.util.Date;

public class ConsultantModelBuilder {

	private ConsultantModel consultant = new ConsultantModel();

	public static ConsultantModelBuilder aConsultant() {
		return new ConsultantModelBuilder();
	}

	public ConsultantModelBuilder withId(String id) {
		consultant.setId(id);
		return this;
	}

	public ConsultantModelBuilder withRegistrationDate(Date registrationDate) {
		consultant.setRegistrationDate(registrationDate);
		return this;
	}

	public ConsultantModelBuilder withConsultantNo(String consultantNo) {
		consultant.setConsultantNo(consultantNo);
		return this;
	}

	public ConsultantModelBuilder withFiscalCode(String fiscalCode) {
		consultant.setFiscalCode(fiscalCode);
		return this;
	}

	public ConsultantModelBuilder withEmail(String email) {
		consultant.setEmail(email);
		return this;
	}

	public ConsultantModelBuilder withFirstName(String firstName) {
		consultant.setFirstName(firstName);
		return this;
	}

	public ConsultantModelBuilder withLastName(String lastName) {
		consultant.setLastName(lastName);
		return this;
	}

	public ConsultantModelBuilder withGender(Gender gender) {
		consultant.setGender(gender);
		return this;
	}

	public ConsultantModelBuilder withPhoneNumber(String phoneNumber) {
		consultant.setPhoneNumber(phoneNumber);
		return this;
	}

	public ConsultantModelBuilder withMobileNo(String mobileNo) {
		consultant.setMobileNumber(mobileNo);
		return this;
	}

	public ConsultantModelBuilder withBirthDate(Date birthDate) {
		consultant.setBirthDate(birthDate);
		return this;
	}

	public ConsultantModelBuilder withBirthCountry(String birthCountry) {
		consultant.setBirthCountry(birthCountry);
		return this;
	}

	public ConsultantModelBuilder withBirthCity(String birthCity) {
		consultant.setBirthCity(birthCity);
		return this;
	}

	public ConsultantModelBuilder withNationality(String nationality) {
		consultant.setNationality(nationality);
		return this;
	}

	public ConsultantModelBuilder withIdentityCardNo(String identityCardNo) {
		consultant.setIdentityCardNo(identityCardNo);
		return this;
	}

	public ConsultantModelBuilder withPassportNo(String passportNo) {
		consultant.setPassportNo(passportNo);
		return this;
	}

	public ConsultantModelBuilder withMaritalStatus(MaritalStatus maritalStatus) {
		consultant.setMaritalStatus(maritalStatus);
		return this;
	}

	public ConsultantModelBuilder withProfile(ProfileModelBuilder profile) {
		return withProfile(profile.build());
	}

	public ConsultantModelBuilder withProfile(ProfileModel profile) {
		consultant.setProfile(profile);
		return this;
	}

	public ConsultantModelBuilder withResidence(AddressModelBuilder residence) {
		return withResidence(residence.build());
	}

	public ConsultantModelBuilder withResidence(AddressModel residence) {
		consultant.setResidence(residence);
		return this;
	}

	public ConsultantModelBuilder withDomicile(AddressModelBuilder domicile) {
		return withDomicile(domicile.build());
	}

	public ConsultantModelBuilder withDomicile(AddressModel domicile) {
		consultant.setDomicile(domicile);
		return this;
	}

	public ConsultantModel build() {
		return consultant;
	}

}
