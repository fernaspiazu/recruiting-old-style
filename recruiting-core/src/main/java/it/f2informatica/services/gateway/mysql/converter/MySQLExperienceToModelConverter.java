package it.f2informatica.services.gateway.mysql.converter;

import it.f2informatica.mysql.domain.Experience;
import it.f2informatica.services.gateway.EntityToModelConverter;
import it.f2informatica.services.model.ExperienceModel;

import static it.f2informatica.services.model.builder.ExperienceModelBuilder.experienceModel;

public class MySQLExperienceToModelConverter extends EntityToModelConverter<Experience, ExperienceModel> {

  @Override
  public ExperienceModel convert(Experience experience) {
    return (experience == null) ? null :
      experienceModel()
        .withId(String.valueOf(experience.getId()))
        .inCompany(experience.getCompanyName())
        .withPosition(experience.getJobPosition())
        .locatedAt(experience.getLocation())
        .fromPeriod(experience.getPeriodFrom())
        .toPeriod(experience.getPeriodTo())
        .isThisTheCurrentJob(experience.isCurrent())
        .withDescription(experience.getDescription())
        .build();
  }

}
