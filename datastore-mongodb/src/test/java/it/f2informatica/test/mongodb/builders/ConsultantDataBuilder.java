package it.f2informatica.test.mongodb.builders;

import it.f2informatica.mongodb.domain.*;
import it.f2informatica.mongodb.domain.constants.Gender;
import it.f2informatica.mongodb.domain.constants.LanguageProficiency;
import it.f2informatica.mongodb.domain.constants.MaritalStatus;

import java.util.Date;
import java.util.GregorianCalendar;

import static it.f2informatica.test.mongodb.builders.AddressDataBuilder.address;
import static it.f2informatica.test.mongodb.builders.ExperienceDataBuilder.experience;
import static it.f2informatica.test.mongodb.builders.LanguageDataBuilder.english;
import static it.f2informatica.test.mongodb.builders.LanguageDataBuilder.italian;
import static it.f2informatica.test.mongodb.builders.ProfileDataBuilder.profile;
import static it.f2informatica.test.mongodb.builders.EducationDataBuilder.education;

public class ConsultantDataBuilder {

	private String id = "52822b8834bdf55624303fc2";
	private String consultantNo = "2013AF1234567890";
	private Date registrationDate = new GregorianCalendar().getTime();
	private String fiscalCode = "RSSMRA75C10F205G";
	private String email = "mario.rossi@gmail.com";
	private String firstName = "Mario";
	private String lastName = "Rossi";
	private Gender gender = Gender.MALE;
	private String phoneNumber = "02-69328884";
	private String mobileNumber = "340-1246559";
	private Date birthDate = new GregorianCalendar(1975, 3, 10).getTime();
	private String birthCountry = "Italy";
	private String birthCity = "Milano";
	private String identityCardNo = "AO652R";
	private String passportNo = "0653214555";
	private MaritalStatus maritalStatus = MaritalStatus.SINGLE;
	private Profile profile;
	private Address residence;
	private Address domicile;
	private String curriculum = "No CV";
	private String photo = "No Photo";

	public static ConsultantDataBuilder consultant() {
		return new ConsultantDataBuilder();
	}

	public ConsultantDataBuilder withId(String id) {
		this.id = id;
		return this;
	}

	public ConsultantDataBuilder withRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
		return this;
	}

	public ConsultantDataBuilder withConsultantNo(String consultantNo) {
		this.consultantNo = consultantNo;
		return this;
	}

	public ConsultantDataBuilder withFiscalCode(String fiscalCode) {
		this.fiscalCode = fiscalCode;
		return this;
	}

	public ConsultantDataBuilder withEmail(String email) {
		this.email = email;
		return this;
	}

	public ConsultantDataBuilder withFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public ConsultantDataBuilder withLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public ConsultantDataBuilder withGender(Gender gender) {
		this.gender = gender;
		return this;
	}

	public ConsultantDataBuilder withPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}

	public ConsultantDataBuilder withMobileNo(String mobileNo) {
		this.mobileNumber = mobileNo;
		return this;
	}

	public ConsultantDataBuilder withBirthDate(Date birthDate) {
		this.birthDate = birthDate;
		return this;
	}

	public ConsultantDataBuilder withBirthCountry(String birthCountry) {
		this.birthCountry = birthCountry;
		return this;
	}

	public ConsultantDataBuilder withBirthCity(String birthCity) {
		this.birthCity = birthCity;
		return this;
	}

	public ConsultantDataBuilder withIdentityCardNo(String identityCardNo) {
		this.identityCardNo = identityCardNo;
		return this;
	}

	public ConsultantDataBuilder withPassportNo(String passportNo) {
		this.passportNo = passportNo;
		return this;
	}

	public ConsultantDataBuilder withMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
		return this;
	}

	public ConsultantDataBuilder withProfile(ProfileDataBuilder profile) {
		return withProfile(profile.build());
	}

	public ConsultantDataBuilder withProfile(Profile profile) {
		this.profile = profile;
		return this;
	}

	public ConsultantDataBuilder withResidence(AddressDataBuilder residence) {
		return withResidence(residence.build());
	}

	public ConsultantDataBuilder withResidence(Address residence) {
		this.residence = residence;
		return this;
	}

	public ConsultantDataBuilder withDomicile(AddressDataBuilder domicile) {
		return withDomicile(domicile.build());
	}

	public ConsultantDataBuilder withDomicile(Address domicile) {
		this.domicile = domicile;
		return this;
	}

	public Consultant build() {
		Consultant consultant = new Consultant();
		consultant.setId(id);
		consultant.setRegistrationDate(registrationDate);
		consultant.setConsultantNo(consultantNo);
		consultant.setFiscalCode(fiscalCode);
		consultant.setEmail(email);
		consultant.setFirstName(firstName);
		consultant.setLastName(lastName);
		consultant.setGender(gender);
		consultant.setPhoneNumber(phoneNumber);
		consultant.setMobileNumber(mobileNumber);
		consultant.setBirthDate(birthDate);
		consultant.setBirthCity(birthCity);
		consultant.setBirthCountry(birthCountry);
		consultant.setIdentityCardNo(identityCardNo);
		consultant.setPassportNo(passportNo);
		consultant.setMaritalStatus(maritalStatus);
		consultant.setProfile(profile);
		consultant.setResidence(residence);
		consultant.setDomicile(domicile);
		consultant.setCurriculum(curriculum);
		consultant.setPhoto(photo);
		return consultant;
	}

	private ConsultantDataBuilder() {
		profile = profile()
				.withExperienceIn(experience().thisIsTheCurrentJob())
				.withSkill("Functional methods")
				.withSkill("Java Programming")
				.withTrainingIn(education())
				.withTrainingIn(education()
						.inSchool("Harvard")
						.startedInYear(2000)
						.notYetFinished()
						.withActivitiesIn("Sciences")
						.withDegreeIn("Information Technology"))
				.speaking(english()
						.withProficiency(LanguageProficiency.PROFESSIONAL_WORKING))
				.speaking(italian()
						.withProficiency(LanguageProficiency.NATIVE_OR_BILINGUAL))
				.withInterestsIn("Travels")
				.build();

		residence = address().build();
		domicile = address().build();
	}

}
