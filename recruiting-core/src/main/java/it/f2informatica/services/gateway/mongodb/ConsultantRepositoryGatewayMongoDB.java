package it.f2informatica.services.gateway.mongodb;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import it.f2informatica.mongodb.domain.*;
import it.f2informatica.mongodb.domain.builder.LanguageBuilder;
import it.f2informatica.mongodb.repositories.ConsultantRepository;
import it.f2informatica.services.gateway.ConsultantRepositoryGateway;
import it.f2informatica.services.gateway.EntityToModelConverter;
import it.f2informatica.services.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static it.f2informatica.mongodb.domain.builder.AddressBuilder.anAddress;
import static it.f2informatica.mongodb.domain.builder.ConsultantBuilder.consultant;
import static it.f2informatica.mongodb.domain.builder.EducationBuilder.education;
import static it.f2informatica.mongodb.domain.builder.ExperienceBuilder.experience;
import static it.f2informatica.services.model.builder.ConsultantModelBuilder.consultantModel;

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
		return new PageImpl<>(Lists.newArrayList(Iterables.transform(consultantPage,
			new Function<Consultant, ConsultantModel>() {
				@Override
				public ConsultantModel apply(Consultant consultant) {
					return consultantModel()
						.withId(consultant.getId())
						.withConsultantNo(consultant.getConsultantNo())
						.withRegistrationDate(consultant.getRegistrationDate())
						.withFirstName(consultant.getFirstName())
						.withLastName(consultant.getLastName())
						.withBirthDate(consultant.getBirthDate())
						.build();
				}
			}
		)));
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
	public boolean addExperience(ExperienceModel experienceModel, String consultantId) {
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
		return consultantRepository.addExperience(experience, consultantId);
	}

	@Override
	public boolean updateExperience(ExperienceModel experienceModel, String consultantId) {
		Experience experience = consultantRepository.findExperience(consultantId, experienceModel.getId());
		experience.setCompanyName(experienceModel.getCompanyName());
		experience.setPosition(experienceModel.getPosition());
		experience.setLocation(experienceModel.getLocality());
		experience.setPeriodFrom(experienceModel.getPeriodFrom());
		experience.setPeriodTo(experienceModel.getPeriodTo());
		experience.setCurrent(experienceModel.isCurrent());
		experience.setDescription(experienceModel.getDescription());
		return consultantRepository.updateExperience(experience, consultantId);
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
	public boolean addLanguages(LanguageModel[] languageModelArray, String consultantId) {
		List<Language> languages = Lists.newArrayList();
		for (LanguageModel languageModel : languageModelArray) {
			Language language = LanguageBuilder.language(languageModel.getLanguage())
				.withProficiency(languageModel.getProficiency()).build();
			languages.add(language);
		}
		return consultantRepository.addLanguages(languages, consultantId);
	}

	@Override
	public boolean addSkills(String[] skills, String consultantId) {
		return consultantRepository.addSkills(skills, consultantId);
	}

	@Override
	public EducationModel findOneEducation(String consultantId, String educationId) {
		Education education = consultantRepository.findEducation(consultantId, educationId);
		return educationToModelConverter.convert(education);
	}

	@Override
	public boolean addEducation(EducationModel educationModel, String consultantId) {
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
		return consultantRepository.addEducation(education, consultantId);
	}

	@Override
	public boolean updateEducation(EducationModel educationModel, String consultantId) {
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
		return consultantRepository.updateEducation(education, consultantId);
	}

	@Override
	public void removeEducation(String consultantId, String educationId) {
		consultantRepository.removeEducation(consultantId, educationId);
	}

}
