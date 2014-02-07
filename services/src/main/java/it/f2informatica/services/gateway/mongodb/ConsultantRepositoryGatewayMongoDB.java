package it.f2informatica.services.gateway.mongodb;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import it.f2informatica.mongodb.domain.Consultant;
import it.f2informatica.mongodb.domain.Experience;
import it.f2informatica.mongodb.domain.Language;
import it.f2informatica.mongodb.domain.Profile;
import it.f2informatica.mongodb.domain.builder.ExperienceBuilder;
import it.f2informatica.mongodb.domain.builder.LanguageBuilder;
import it.f2informatica.mongodb.repositories.ConsultantRepository;
import it.f2informatica.services.gateway.ConsultantRepositoryGateway;
import it.f2informatica.services.gateway.EntityToModelConverter;
import it.f2informatica.services.model.ConsultantModel;
import it.f2informatica.services.model.ExperienceModel;
import it.f2informatica.services.model.LanguageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static it.f2informatica.mongodb.domain.builder.ConsultantBuilder.consultant;
import static it.f2informatica.mongodb.domain.builder.ExperienceBuilder.*;
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
	public ConsultantModel saveMasterData(ConsultantModel consultantModel) {
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
			.build();
		Consultant consultantRegistered = consultantRepository.save(consultant);
		return consultantToModelConverter.convert(consultantRegistered);
	}

	@Override
	public ConsultantModel findConsultantById(String consultantId) {
		Consultant consultant = consultantRepository.findOne(consultantId);
		return consultantToModelConverter.convert(consultant);
	}

	@Override
	public boolean addConsultantExperience(ExperienceModel experienceModel, String consultantId) {
		Experience experience = experience()
			.withId(UUID.randomUUID().toString())
			.inCompany(experienceModel.getCompanyName())
			.inFunctionOf(experienceModel.getFunction())
			.locatedAt(experienceModel.getLocation())
			.fromPeriod(experienceModel.getPeriodFrom())
			.toPeriod(experienceModel.getPeriodTo())
			.isThisTheCurrentJob(experienceModel.isCurrent())
			.withDescription(experienceModel.getDescription())
			.build();
		return consultantRepository.addExperience(experience, consultantId);
	}

	@Override
	public boolean updateConsultantExperience(ExperienceModel experienceModel, String consultantId) {
		Experience experience = experience()
				.withId(experienceModel.getId())
				.inCompany(experienceModel.getCompanyName())
				.inFunctionOf(experienceModel.getFunction())
				.locatedAt(experienceModel.getLocation())
				.fromPeriod(experienceModel.getPeriodFrom())
				.toPeriod(experienceModel.getPeriodTo())
				.isThisTheCurrentJob(experienceModel.isCurrent())
				.withDescription(experienceModel.getDescription())
				.build();
		return consultantRepository.updateExperience(experience, consultantId);
	}

	@Override
	public List<ExperienceModel> findExperiencesByConsultantId(String consultantId) {
		Profile profile = consultantRepository.findOne(consultantId).getProfile();
		return (profile != null)
				? experienceToModelConverter.convertList(profile.getExperiences())
				: Lists.<ExperienceModel>newArrayList();
	}

	@Override
	public List<ExperienceModel> findMinimalExperiences(String consultantId) {
		List<ExperienceModel> experiences = findExperiencesByConsultantId(consultantId);
		if (experiences.size() > 3) {
			return experiences.subList(0, 3);
		}
		return experiences;
	}

	@Override
	public ExperienceModel findExperienceByConsultantIdAndExperienceId(String consultantId, String experienceId) {
		Experience experience = consultantRepository.findExperience(consultantId, experienceId);
		return experienceToModelConverter.convert(experience);
	}

	@Override
	public boolean addLanguage(LanguageModel languageModel, String consultantId) {
		Language language = LanguageBuilder.language(languageModel.getLanguage())
				.withProficiency(languageModel.getProficiency()).build();
		return consultantRepository.addLanguage(language, consultantId);
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

}
