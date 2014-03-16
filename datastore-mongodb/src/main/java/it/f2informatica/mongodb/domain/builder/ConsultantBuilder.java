package it.f2informatica.mongodb.domain.builder;

import it.f2informatica.mongodb.domain.*;

import java.util.Date;
import java.util.List;

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

	public ConsultantBuilder withGender(String gender) {
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

	public ConsultantBuilder withMaritalStatus(String maritalStatus) {
		consultant.setMaritalStatus(maritalStatus);
		return this;
	}

	public ConsultantBuilder withExperienceIn(ExperienceBuilder experience) {
		return withExperienceIn(experience.build());
	}

	public ConsultantBuilder withExperienceIn(Experience experience) {
		consultant.getExperiences().add(experience);
		return this;
	}

	public ConsultantBuilder withExperiencesIn(List<Experience> experiences) {
		consultant.getExperiences().addAll(experiences);
		return this;
	}

	public ConsultantBuilder withEducationIn(EducationBuilder training) {
		return withEducationIn(training.build());
	}

	public ConsultantBuilder withEducationIn(Education education) {
		consultant.getEducationList().add(education);
		return this;
	}

	public ConsultantBuilder withEducationIn(List<Education> educations) {
		consultant.getEducationList().addAll(educations);
		return this;
	}

	public ConsultantBuilder withSkill(String skill) {
		consultant.getSkills().add(skill);
		return this;
	}

	public ConsultantBuilder withSkills(List<String> skills) {
		consultant.getSkills().addAll(skills);
		return this;
	}

	public ConsultantBuilder speaking(LanguageBuilder language) {
		return speaking(language.build());
	}

	public ConsultantBuilder speaking(Language language) {
		consultant.getLanguages().add(language);
		return this;
	}

	public ConsultantBuilder speakingLanguages(List<Language> languages) {
		consultant.getLanguages().addAll(languages);
		return this;
	}

	public ConsultantBuilder withInterestsIn(String interestsIn) {
		consultant.setInterests(interestsIn);
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
