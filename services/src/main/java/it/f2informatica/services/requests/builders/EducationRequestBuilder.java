package it.f2informatica.services.requests.builders;

import it.f2informatica.services.requests.EducationRequest;

public class EducationRequestBuilder {

	private EducationRequest education = new EducationRequest();

	public static EducationRequestBuilder educationRequest() {
		return new EducationRequestBuilder();
	}

	public EducationRequestBuilder inSchool(String school) {
		education.setSchool(school);
		return this;
	}

	public EducationRequestBuilder startedInYear(int year) {
		education.setStartYear(year);
		return this;
	}

	public EducationRequestBuilder finishedInYear(int year) {
		education.setEndYear(year);
		education.setCurrent(false);
		return this;
	}

	public EducationRequestBuilder notYetFinished() {
		education.setEndYear(-1);
		education.setCurrent(true);
		return this;
	}

	public EducationRequestBuilder withDegreeIn(String degree) {
		education.setSchoolDegree(degree);
		return this;
	}

	public EducationRequestBuilder fieldOfStudyIn(String schoolFieldOfStudy) {
		education.setSchoolFieldOfStudy(schoolFieldOfStudy);
		return this;
	}

	public EducationRequestBuilder withGrade(String grade) {
		education.setSchoolGrade(grade);
		return this;
	}

	public EducationRequestBuilder withActivitiesIn(String schoolActivities) {
		education.setSchoolActivities(schoolActivities);
		return this;
	}

	public EducationRequestBuilder withDescription(String description) {
		education.setDescription(description);
		return this;
	}

	public EducationRequest build() {
		return education;
	}

}
