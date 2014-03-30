package it.f2informatica.services.gateway.mysql.converter;

import it.f2informatica.mysql.domain.Education;
import it.f2informatica.services.gateway.EntityToModelConverter;
import it.f2informatica.services.model.EducationModel;

import static it.f2informatica.services.model.builder.EducationModelBuilder.educationModel;

public class MySQLEducationToModelConverter extends EntityToModelConverter<Education, EducationModel> {

  @Override
  public EducationModel convert(Education education) {
    return (education == null) ? null :
      educationModel()
        .withId(String.valueOf(education.getId()))
        .inSchool(education.getSchoolName())
        .startedInYear(education.getStartYear())
        .finishedInYear(education.getEndYear())
        .withDegreeIn(education.getSchoolDegree())
        .fieldOfStudyIn(education.getFieldsOfStudy())
        .withGrade(education.getGrade())
        .withActivitiesIn(education.getActivities())
        .isInProgress(education.isCurrent())
        .withDescription(education.getDescription())
        .build();
  }

}
