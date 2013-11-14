package it.f2informatica.services.domain.consultant.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import it.f2informatica.mongodb.domain.*;
import it.f2informatica.mongodb.domain.constants.Gender;
import it.f2informatica.mongodb.domain.constants.MaritalStatus;
import it.f2informatica.services.requests.*;
import org.springframework.util.StringUtils;

import java.util.List;

import static it.f2informatica.mongodb.domain.builders.AddressBuilder.anAddress;
import static it.f2informatica.mongodb.domain.builders.ConsultantBuilder.aConsultant;
import static it.f2informatica.mongodb.domain.builders.EducationBuilder.education;
import static it.f2informatica.mongodb.domain.builders.ExperienceBuilder.experience;
import static it.f2informatica.mongodb.domain.builders.LanguageBuilder.language;
import static it.f2informatica.mongodb.domain.builders.ProfileBuilder.profile;

class ConsultantRequestToConsultantTransformer
		implements Function<ConsultantRequest, Consultant> {

	@Override
	public Consultant apply(ConsultantRequest input) {
		return aConsultant()
				.withConsultantNo(input.getConsultantNo())
				.withRegistrationDate(input.getRegistrationDate())
				.withFiscalCode(input.getFiscalCode())
				.withEmail(input.getEmail())
				.withFirstName(input.getFirstName())
				.withLastName(input.getLastName())
				.withGender(getGenderValue(input.getGender()))
				.withPhoneNumber(input.getPhoneNumber())
				.withMobileNo(input.getMobileNumber())
				.withBirthDate(input.getBirthDate())
				.withBirthCity(input.getBirthCity())
				.withBirthCountry(input.getBirthCountry())
				.withNationality(input.getNationality())
				.withIdentityCardNo(input.getIdentityCardNo())
				.withPassportNo(input.getPassportNo())
				.withMaritalStatus(getMaritalStatusValue(input.getMaritalStatus()))
				.withProfile(profile()
						.withExperiencesIn(getExperiences(input.getProfile().getExperiences()))
						.withEducationIn(getEducationList(input.getProfile().getEducationList()))
						.speakingLanguages(getLanguageList(input.getProfile().getLanguages()))
						.withSkills(input.getProfile().getSkills())
						.withInterestsIn(input.getProfile().getInterests()))
				.withResidence(getAddress(input.getResidence()))
				.withDomicile(getAddress(input.getDomicile()))
				.build();
	}

	private Gender getGenderValue(String gender) {
		return StringUtils.hasText(gender) ? Gender.valueOf(gender.toUpperCase()) : null;
	}

	private MaritalStatus getMaritalStatusValue(String maritalStatus) {
		return StringUtils.hasText(maritalStatus) ? MaritalStatus.valueOf(maritalStatus.toUpperCase()) : null;
	}

	private List<Language> getLanguageList(List<LanguageRequest> languages) {
		return Lists.transform(languages, new Function<LanguageRequest, Language>() {
			@Override
			public Language apply(LanguageRequest input) {
				return language(input.getLanguage())
						.withProficiency(input.getProficiency())
						.build();
			}
		});
	}

	private List<Experience> getExperiences(List<ExperienceRequest> experiences) {
		return Lists.transform(experiences, new Function<ExperienceRequest, Experience>() {
			@Override
			public Experience apply(ExperienceRequest input) {
				return experience()
						.inCompany(input.getCompanyName())
						.inFunctionOf(input.getFunction())
						.locatedAt(input.getLocation())
						.fromPeriod(input.getPeriodFrom())
						.toPeriod(input.getPeriodTo())
						.isThisTheCurrentJob(input.isCurrent())
						.withDescription(input.getDescription())
						.build();
			}
		});
	}

	private List<Education> getEducationList(List<EducationRequest> educationList) {
		return Lists.transform(educationList, new Function<EducationRequest, Education>() {
			@Override
			public Education apply(EducationRequest input) {
				return education()
						.inSchool(input.getSchool())
						.startedInYear(input.getStartYear())
						.finishedInYear(input.getEndYear())
						.withDegreeIn(input.getSchoolDegree())
						.fieldOfStudyIn(input.getSchoolFieldOfStudy())
						.withGrade(input.getSchoolGrade())
						.withActivitiesIn(input.getSchoolActivities())
						.isInProgress(input.isCurrent())
						.withDescription(input.getDescription())
						.build();
			}
		});
	}

	private Address getAddress(AddressRequest addressRequest) {
		return anAddress()
				.withStreet(addressRequest.getStreet())
				.withHouseNo(addressRequest.getHouseNo())
				.withZipCode(addressRequest.getZipCode())
				.withCity(addressRequest.getCity())
				.withProvince(addressRequest.getProvince())
				.withRegion(addressRequest.getRegion())
				.withCountry(addressRequest.getCountry())
				.build();
	}

}
