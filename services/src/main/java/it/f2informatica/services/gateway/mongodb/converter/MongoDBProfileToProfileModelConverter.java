package it.f2informatica.services.gateway.mongodb.converter;

import it.f2informatica.mongodb.domain.Education;
import it.f2informatica.mongodb.domain.Experience;
import it.f2informatica.mongodb.domain.Language;
import it.f2informatica.mongodb.domain.Profile;
import it.f2informatica.services.gateway.EntityToModelConverter;
import it.f2informatica.services.model.EducationModel;
import it.f2informatica.services.model.ExperienceModel;
import it.f2informatica.services.model.LanguageModel;
import it.f2informatica.services.model.ProfileModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import static it.f2informatica.services.model.builder.ProfileModelBuilder.profileModel;

public class MongoDBProfileToProfileModelConverter
		extends EntityToModelConverter<Profile, ProfileModel> {

	@Autowired
	@Qualifier("experienceToModelConverter")
	private EntityToModelConverter<Experience, ExperienceModel> experienceToModelConverter;

	@Autowired
	@Qualifier("educationToModelConverter")
	private EntityToModelConverter<Education, EducationModel> educationToModelConverter;

	@Autowired
	@Qualifier("languageToModelConverter")
	private EntityToModelConverter<Language, LanguageModel> languageToModelConverter;

	@Override
	public ProfileModel convert(Profile profile) {
		return (profile == null) ? null :
			profileModel()
				.withExperiencesIn(experienceToModelConverter.convertList(profile.getExperiences()))
				.withEducationIn(educationToModelConverter.convertList(profile.getEducationList()))
				.speakingLanguages(languageToModelConverter.convertList(profile.getLanguages()))
				.withSkills(profile.getSkills())
				.withInterestsIn(profile.getInterests())
				.build();
	}

}
