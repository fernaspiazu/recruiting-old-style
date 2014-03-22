package it.f2informatica.mongodb.domain.builder;

import it.f2informatica.mongodb.domain.Education;

public class EducationBuilder {

	private Education education = new Education();

	public static EducationBuilder education() {
		return new EducationBuilder();
	}

	public EducationBuilder withId(String id) {
		this.education.setId(id);
		return this;
	}

	public EducationBuilder inSchool(String school) {
		education.setSchool(school);
		return this;
	}

	public EducationBuilder startedInYear(int year) {
		education.setStartYear(year);
		return this;
	}

	public EducationBuilder finishedInYear(int year) {
		education.setEndYear(year);
		education.setCurrent(false);
		return this;
	}

	public EducationBuilder isInProgress(boolean isCurrent) {
		if (isCurrent) {
			return inProgress();
		}
		education.setCurrent(false);
		return this;
	}

	public EducationBuilder inProgress() {
		education.setEndYear(-1);
		education.setCurrent(true);
		return this;
	}

	public EducationBuilder withDegreeIn(String degree) {
		education.setSchoolDegree(degree);
		return this;
	}

	public EducationBuilder fieldOfStudyIn(String schoolFieldOfStudy) {
		education.setSchoolFieldOfStudy(schoolFieldOfStudy);
		return this;
	}

	public EducationBuilder withGrade(String grade) {
		education.setSchoolGrade(grade);
		return this;
	}

	public EducationBuilder withActivitiesIn(String schoolActivities) {
		education.setSchoolActivities(schoolActivities);
		return this;
	}

	public EducationBuilder withDescription(String description) {
		education.setDescription(description);
		return this;
	}

	public Education build() {
		return education;
	}

}
