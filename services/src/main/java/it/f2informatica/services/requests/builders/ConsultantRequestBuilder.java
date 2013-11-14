package it.f2informatica.services.requests.builders;

import it.f2informatica.services.requests.AddressRequest;
import it.f2informatica.services.requests.ConsultantRequest;
import it.f2informatica.services.requests.ProfileRequest;

import java.util.Date;

public class ConsultantRequestBuilder {

	private ConsultantRequest consultant = new ConsultantRequest();

	public static ConsultantRequestBuilder consultantRequest() {
		return new ConsultantRequestBuilder();
	}

	public ConsultantRequestBuilder withId(String id) {
		consultant.setConsultantId(id);
		return this;
	}

	public ConsultantRequestBuilder withRegistrationDate(Date registrationDate) {
		consultant.setRegistrationDate(registrationDate);
		return this;
	}

	public ConsultantRequestBuilder withConsultantNo(String consultantNo) {
		consultant.setConsultantNo(consultantNo);
		return this;
	}

	public ConsultantRequestBuilder withFiscalCode(String fiscalCode) {
		consultant.setFiscalCode(fiscalCode);
		return this;
	}

	public ConsultantRequestBuilder withEmail(String email) {
		consultant.setEmail(email);
		return this;
	}

	public ConsultantRequestBuilder withFirstName(String firstName) {
		consultant.setFirstName(firstName);
		return this;
	}

	public ConsultantRequestBuilder withLastName(String lastName) {
		consultant.setLastName(lastName);
		return this;
	}

	public ConsultantRequestBuilder withGender(String gender) {
		consultant.setGender(gender);
		return this;
	}

	public ConsultantRequestBuilder withPhoneNumber(String phoneNumber) {
		consultant.setPhoneNumber(phoneNumber);
		return this;
	}

	public ConsultantRequestBuilder withMobileNo(String mobileNo) {
		consultant.setMobileNumber(mobileNo);
		return this;
	}

	public ConsultantRequestBuilder withBirthDate(Date birthDate) {
		consultant.setBirthDate(birthDate);
		return this;
	}

	public ConsultantRequestBuilder withBirthCountry(String birthCountry) {
		consultant.setBirthCountry(birthCountry);
		return this;
	}

	public ConsultantRequestBuilder withBirthCity(String birthCity) {
		consultant.setBirthCity(birthCity);
		return this;
	}

	public ConsultantRequestBuilder withNationality(String nationality) {
		consultant.setNationality(nationality);
		return this;
	}

	public ConsultantRequestBuilder withIdentityCardNo(String identityCardNo) {
		consultant.setIdentityCardNo(identityCardNo);
		return this;
	}

	public ConsultantRequestBuilder withPassportNo(String passportNo) {
		consultant.setPassportNo(passportNo);
		return this;
	}

	public ConsultantRequestBuilder withMaritalStatus(String maritalStatus) {
		consultant.setMaritalStatus(maritalStatus);
		return this;
	}

	public ConsultantRequestBuilder withProfile(ProfileRequestBuilder profile) {
		return withProfile(profile.build());
	}

	public ConsultantRequestBuilder withProfile(ProfileRequest profile) {
		consultant.setProfile(profile);
		return this;
	}

	public ConsultantRequestBuilder withResidence(AddressRequestBuilder residence) {
		return withResidence(residence.build());
	}

	public ConsultantRequestBuilder withResidence(AddressRequest residence) {
		consultant.setResidence(residence);
		return this;
	}

	public ConsultantRequestBuilder withDomicile(AddressRequestBuilder domicile) {
		return withDomicile(domicile.build());
	}

	public ConsultantRequestBuilder withDomicile(AddressRequest domicile) {
		consultant.setDomicile(domicile);
		return this;
	}

	public ConsultantRequest build() {
		return consultant;
	}

}
