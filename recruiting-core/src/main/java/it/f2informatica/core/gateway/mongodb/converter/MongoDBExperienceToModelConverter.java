package it.f2informatica.core.gateway.mongodb.converter;

import it.f2informatica.core.gateway.EntityToModelConverter;
import it.f2informatica.core.model.ExperienceModel;
import it.f2informatica.mongodb.domain.Experience;
import org.springframework.stereotype.Component;

import static it.f2informatica.core.model.builder.ExperienceModelBuilder.experienceModel;

@Component("experienceToModelConverter")
public class MongoDBExperienceToModelConverter
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
