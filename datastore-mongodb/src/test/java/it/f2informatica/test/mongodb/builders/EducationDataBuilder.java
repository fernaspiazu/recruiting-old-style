package it.f2informatica.test.mongodb.builders;

import it.f2informatica.mongodb.domain.Education;

public class EducationDataBuilder {

	private String school = "Dante Alighieri Institute";
	private int startYear = 1990;
	private int endYear = 1996;
	private String schoolDegree = "Accountancy";
	private String schoolFieldOfStudy = "Accounting";
	private String schoolGrade = "98/100";
	private String schoolActivities = "No details about";
	private boolean isCurrent = false;
	private String description = "No description";

	public static EducationDataBuilder education() {
		return new EducationDataBuilder();
	}

	public EducationDataBuilder inSchool(String school) {
		this.school = school;
		return this;
	}

	public EducationDataBuilder startedInYear(int year) {
		this.startYear = year;
		return this;
	}

	public EducationDataBuilder finishedInYear(int year) {
		this.endYear = year;
		this.isCurrent = false;
		return this;
	}

	public EducationDataBuilder notYetFinished() {
		this.endYear = -1;
		this.isCurrent = true;
		return this;
	}

	public EducationDataBuilder withDegreeIn(String degree) {
		this.schoolDegree = degree;
		return this;
	}

	public EducationDataBuilder fieldOfStudyIn(String schoolFieldOfStudy) {
		this.schoolFieldOfStudy = schoolFieldOfStudy;
		return this;
	}

	public EducationDataBuilder withGrade(String grade) {
		this.schoolGrade = grade;
		return this;
	}

	public EducationDataBuilder withActivitiesIn(String schoolActivities) {
		this.schoolActivities = schoolActivities;
		return this;
	}

	public EducationDataBuilder withDescription(String description) {
		this.description = description;
		return this;
	}

	public Education build() {
		Education education = new Education();
		education.setSchool(school);
		education.setStartYear(startYear);
		education.setEndYear(endYear);
		education.setSchoolDegree(schoolDegree);
		education.setSchoolFieldOfStudy(schoolFieldOfStudy);
		education.setSchoolGrade(schoolGrade);
		education.setSchoolActivities(schoolActivities);
		education.setCurrent(isCurrent);
		education.setDescription(description);
		return education;
	}

}
