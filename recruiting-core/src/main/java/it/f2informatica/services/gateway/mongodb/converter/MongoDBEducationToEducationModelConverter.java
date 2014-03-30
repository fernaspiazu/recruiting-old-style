package it.f2informatica.services.gateway.mongodb.converter;

import it.f2informatica.mongodb.domain.Education;
import it.f2informatica.services.gateway.EntityToModelConverter;
import it.f2informatica.services.model.EducationModel;

import static it.f2informatica.services.model.builder.EducationModelBuilder.educationModel;

public class MongoDBEducationToEducationModelConverter
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
