package it.f2informatica.core.gateway.mongodb.converter;

import it.f2informatica.core.gateway.EntityToModelConverter;
import it.f2informatica.core.model.EducationModel;
import it.f2informatica.mongodb.domain.Education;
import org.springframework.stereotype.Component;

import static it.f2informatica.core.model.builder.EducationModelBuilder.educationModel;

@Component("educationToModelConverter")
public class MongoDBEducationToModelConverter
  extends EntityToModelConverter<Education, EducationModel> {

  @Override
  public EducationModel convert(Education education) {
    return (education == null) ? null :
      educationModel()
        .withId(education.getId())
        .inSchool(education.getSchool())
        .startedInYear(education.getStartYear())
        .finishedInYear(education.getEndYear())
        .withDegreeIn(education.getSchoolDegree())
        .fieldOfStudyIn(education.getSchoolFieldOfStudy())
        .withGrade(education.getSchoolGrade())
        .withActivitiesIn(education.getSchoolActivities())
        .isInProgress(education.isCurrent())
        .withDescription(education.getDescription())
        .build();
  }

}
