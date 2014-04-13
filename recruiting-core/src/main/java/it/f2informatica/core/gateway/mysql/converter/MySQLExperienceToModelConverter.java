package it.f2informatica.core.gateway.mysql.converter;

import it.f2informatica.mysql.domain.Experience;
import it.f2informatica.core.gateway.EntityToModelConverter;
import it.f2informatica.core.model.ExperienceModel;
import org.springframework.stereotype.Component;

import static it.f2informatica.core.model.builder.ExperienceModelBuilder.experienceModel;

@Component("mysqlExperienceToModelConverter")
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
