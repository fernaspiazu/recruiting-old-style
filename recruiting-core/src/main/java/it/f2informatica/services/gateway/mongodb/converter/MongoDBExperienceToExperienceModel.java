package it.f2informatica.services.gateway.mongodb.converter;

import it.f2informatica.mongodb.domain.Experience;
import it.f2informatica.services.gateway.EntityToModelConverter;
import it.f2informatica.services.model.ExperienceModel;

import static it.f2informatica.services.model.builder.ExperienceModelBuilder.experienceModel;

public class MongoDBExperienceToExperienceModel
		extends EntityToModelConverter<Experience, ExperienceModel> {

	@Override
	public ExperienceModel convert(Experience experience) {
		return (experience == null) ? null :
			experienceModel()
				.withId(experience.getId())
				.inCompany(experience.getCompanyName())
				.withPosition(experience.getPosition())
				.locatedAt(experience.getLocation())
				.fromPeriod(experience.getPeriodFrom())
				.toPeriod(experience.getPeriodTo())
				.isThisTheCurrentJob(experience.isCurrent())
				.withDescription(experience.getDescription())
				.build();
	}

}
