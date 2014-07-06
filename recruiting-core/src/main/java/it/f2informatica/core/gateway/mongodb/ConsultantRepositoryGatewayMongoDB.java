/*
 * =============================================================================
 *
 *   Copyright (c) 2014, Fernando Aspiazu
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */
package it.f2informatica.core.gateway.mongodb;

import com.google.common.collect.Lists;
import it.f2informatica.core.gateway.ConsultantRepositoryGateway;
import it.f2informatica.core.gateway.EntityToModelConverter;
import it.f2informatica.core.model.*;
import it.f2informatica.core.model.query.ConsultantSearchCriteria;
import it.f2informatica.mongodb.MongoDB;
import it.f2informatica.mongodb.domain.*;
import it.f2informatica.mongodb.domain.builder.LanguageBuilder;
import it.f2informatica.mongodb.repositories.ConsultantRepository;
import it.f2informatica.pagination.repository.mongodb.MongoQueryPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

import static it.f2informatica.mongodb.domain.builder.AddressBuilder.anAddress;
import static it.f2informatica.mongodb.domain.builder.ConsultantBuilder.consultant;
import static it.f2informatica.mongodb.domain.builder.EducationBuilder.education;
import static it.f2informatica.mongodb.domain.builder.ExperienceBuilder.experience;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@MongoDB
@Service
public class ConsultantRepositoryGatewayMongoDB implements ConsultantRepositoryGateway {

	@Autowired
	private ConsultantRepository consultantRepository;

	@Autowired
	@Qualifier("consultantToModelConverter")
	private EntityToModelConverter<Consultant, ConsultantModel> consultantToModelConverter;

	@Autowired
	@Qualifier("experienceToModelConverter")
	private EntityToModelConverter<Experience, ExperienceModel> experienceToModelConverter;

	@Autowired
	@Qualifier("educationToModelConverter")
	private EntityToModelConverter<Education, EducationModel> educationToModelConverter;

	@Override
	public ConsultantModel findOneConsultant(String consultantId) {
		Consultant consultant = consultantRepository.findOne(consultantId);
		return consultantToModelConverter.convert(consultant);
	}

	@Override
	public Page<ConsultantModel> findAllConsultants(Pageable pageable) {
		Page<Consultant> consultantPage = consultantRepository.findAll(pageable);
		return new PageImpl<>(consultantToModelConverter.convertList(consultantPage.getContent()), pageable, consultantPage.getTotalElements());
	}

	@Override
	public Page<ConsultantModel> paginateConsultants(final ConsultantSearchCriteria searchCriteria, Pageable pageable) {
		MongoQueryPredicate<Consultant> queryPredicate = new MongoQueryPredicate<Consultant>(Consultant.class) {
			@Override
			public Query queryPredicate() {
				final Query query = new Query();
				if (StringUtils.hasText(searchCriteria.getName())) {
					query.addCriteria(where("firstName").regex(searchCriteria.getName(), "i"));
				}
				if (StringUtils.hasText(searchCriteria.getLastName())) {
					query.addCriteria(where("lastName").regex(searchCriteria.getLastName(), "i"));
				}
				if (StringUtils.hasText(searchCriteria.getSkills())) {
					query.addCriteria(where("skills").in(searchCriteria.getSkills().split(",")));
				}
				return query;
			}
		};
		Page<Consultant> consultantPage = consultantRepository.findAll(queryPredicate, pageable);
		return new PageImpl<>(consultantToModelConverter.convertList(consultantPage.getContent()), pageable, consultantPage.getTotalElements());
	}

	@Override
	public void updatePersonalDetails(ConsultantModel consultantModel, String consultantId) {
		Update update = new Update()
			.set("fiscalCode", consultantModel.getFiscalCode())
			.set("email", consultantModel.getEmail())
			.set("firstName", consultantModel.getFirstName())
			.set("lastName", consultantModel.getLastName())
			.set("gender", consultantModel.getGender())
			.set("phoneNumber", consultantModel.getPhoneNumber())
			.set("mobileNumber", consultantModel.getMobileNumber())
			.set("birthDate", consultantModel.getBirthDate())
			.set("birthCity", consultantModel.getBirthCity())
			.set("birthCountry", consultantModel.getBirthCountry())
			.set("identityCardNo", consultantModel.getIdentityCardNo())
			.set("passportNo", consultantModel.getPassportNo())
			.set("interests", consultantModel.getInterests());
		mapAddressData(update, consultantModel.getResidence(), "residence");
		mapAddressData(update, consultantModel.getDomicile(), "domicile");
		consultantRepository.updateConsultantsPersonalDetails(update, consultantId);
	}

	private void mapAddressData(Update update, AddressModel address, String field) {
		update.set(field + ".street", address.getStreet());
		update.set(field + ".houseNo", address.getHouseNo());
		update.set(field + ".zipCode", address.getZipCode());
		update.set(field + ".city", address.getCity());
		update.set(field + ".province", address.getProvince());
		update.set(field + ".region", address.getRegion());
		update.set(field + ".country", address.getCountry());
	}

	@Override
	public ConsultantModel savePersonalDetails(ConsultantModel consultantModel) {
		Consultant consultant = consultant()
			.withConsultantNo(consultantModel.getConsultantNo())
			.withRegistrationDate(consultantModel.getRegistrationDate())
			.withFirstName(consultantModel.getFirstName())
			.withLastName(consultantModel.getLastName())
			.withGender(consultantModel.getGender())
			.withEmail(consultantModel.getEmail())
			.withFiscalCode(consultantModel.getFiscalCode())
			.withBirthDate(consultantModel.getBirthDate())
			.withBirthCity(consultantModel.getBirthCity())
			.withBirthCountry(consultantModel.getBirthCountry())
			.withPhoneNumber(consultantModel.getPhoneNumber())
			.withMobileNo(consultantModel.getMobileNumber())
			.withResidence(buildAddress(consultantModel.getResidence()))
			.withDomicile(buildAddress(consultantModel.getDomicile()))
			.build();
		Consultant consultantRegistered = consultantRepository.save(consultant);
		return consultantToModelConverter.convert(consultantRegistered);
	}

	private Address buildAddress(AddressModel addressModel) {
		return (addressModel == null) ? null : anAddress()
			.withStreet(addressModel.getStreet())
			.withHouseNo(addressModel.getHouseNo())
			.withZipCode(addressModel.getZipCode())
			.withCity(addressModel.getCity())
			.withProvince(addressModel.getProvince())
			.withRegion(addressModel.getRegion())
			.withCountry(addressModel.getCountry())
			.build();
	}

	@Override
	public void addExperience(ExperienceModel experienceModel, String consultantId) {
		Experience experience = experience()
			.withId(UUID.randomUUID().toString())
			.inCompany(experienceModel.getCompanyName())
			.withPosition(experienceModel.getPosition())
			.locatedAt(experienceModel.getLocality())
			.fromPeriod(experienceModel.getPeriodFrom())
			.toPeriod(experienceModel.getPeriodTo())
			.isThisTheCurrentJob(experienceModel.isCurrent())
			.withDescription(experienceModel.getDescription())
			.build();
		consultantRepository.addExperience(experience, consultantId);
	}

	@Override
	public void updateExperience(ExperienceModel experienceModel, String consultantId) {
		Experience experience = consultantRepository.findExperience(consultantId, experienceModel.getId());
		experience.setCompanyName(experienceModel.getCompanyName());
		experience.setPosition(experienceModel.getPosition());
		experience.setLocation(experienceModel.getLocality());
		experience.setPeriodFrom(experienceModel.getPeriodFrom());
		experience.setPeriodTo(experienceModel.getPeriodTo());
		experience.setCurrent(experienceModel.isCurrent());
		experience.setDescription(experienceModel.getDescription());
		consultantRepository.updateExperience(experience, consultantId);
	}

	@Override
	public void removeExperience(String consultantId, String experienceId) {
		consultantRepository.removeExperience(consultantId, experienceId);
	}

	@Override
	public ExperienceModel findOneExperience(String consultantId, String experienceId) {
		Experience experience = consultantRepository.findExperience(consultantId, experienceId);
		return experienceToModelConverter.convert(experience);
	}

	@Override
	public void addLanguages(LanguageModel[] languageModelArray, String consultantId) {
		List<Language> languages = Lists.newArrayList();
		for (LanguageModel languageModel : languageModelArray) {
			Language language = LanguageBuilder.language(languageModel.getLanguage())
				.withProficiency(languageModel.getProficiency()).build();
			languages.add(language);
		}
		consultantRepository.addLanguages(languages, consultantId);
	}

	@Override
	public void addSkills(String[] skills, String consultantId) {
		consultantRepository.addSkills(skills, consultantId);
	}

	@Override
	public EducationModel findOneEducation(String consultantId, String educationId) {
		Education education = consultantRepository.findEducation(consultantId, educationId);
		return educationToModelConverter.convert(education);
	}

	@Override
	public void addEducation(EducationModel educationModel, String consultantId) {
		Education education = education()
			.withId(UUID.randomUUID().toString())
			.inSchool(educationModel.getSchool())
			.startedInYear(educationModel.getStartYear())
			.finishedInYear(educationModel.getEndYear())
			.withDegreeIn(educationModel.getSchoolDegree())
			.fieldOfStudyIn(educationModel.getSchoolFieldOfStudy())
			.withGrade(educationModel.getSchoolGrade())
			.withActivitiesIn(educationModel.getSchoolActivities())
			.isInProgress(educationModel.isCurrent())
			.withDescription(educationModel.getDescription())
			.build();
		consultantRepository.addEducation(education, consultantId);
	}

	@Override
	public void updateEducation(EducationModel educationModel, String consultantId) {
		Education education = consultantRepository.findEducation(consultantId, educationModel.getId());
		education.setSchool(educationModel.getSchool());
		education.setStartYear(educationModel.getStartYear());
		education.setEndYear(educationModel.getEndYear());
		education.setSchoolDegree(educationModel.getSchoolDegree());
		education.setSchoolFieldOfStudy(educationModel.getSchoolFieldOfStudy());
		education.setSchoolGrade(educationModel.getSchoolGrade());
		education.setSchoolActivities(educationModel.getSchoolActivities());
		education.setCurrent(educationModel.isCurrent());
		education.setDescription(educationModel.getDescription());
		consultantRepository.updateEducation(education, consultantId);
	}

	@Override
	public void removeEducation(String consultantId, String educationId) {
		consultantRepository.removeEducation(consultantId, educationId);
	}

}
