package it.f2informatica.test.mongodb.builders;

import it.f2informatica.mongodb.domain.Training;

public class TrainingDataBuilder {

	private String school = "Dante Alighieri Institute";
	private int startYear = 1990;
	private int endYear = 1996;
	private String schoolDegree = "Accountancy";
	private String schoolFieldOfStudy = "Accounting";
	private String schoolGrade = "98/100";
	private String schoolActivities = "No details about";
	private String description = "No description";

	public static TrainingDataBuilder training() {
		return new TrainingDataBuilder();
	}

	public TrainingDataBuilder inSchool(String school) {
		this.school = school;
		return this;
	}

	public TrainingDataBuilder startedInYear(int year) {
		this.startYear = year;
		return this;
	}

	public TrainingDataBuilder finishedInYear(int year) {
		this.endYear =year;
		return this;
	}

	public TrainingDataBuilder notYetFinished() {
		this.endYear = -1;
		return this;
	}

	public TrainingDataBuilder withDegreeIn(String degree) {
		this.schoolDegree = degree;
		return this;
	}

	public TrainingDataBuilder fieldOfStudyIn(String schoolFieldOfStudy) {
		this.schoolFieldOfStudy = schoolFieldOfStudy;
		return this;
	}

	public TrainingDataBuilder withGrade(String grade) {
		this.schoolGrade = grade;
		return this;
	}

	public TrainingDataBuilder withActivitiesIn(String schoolActivities) {
		this.schoolActivities = schoolActivities;
		return this;
	}

	public TrainingDataBuilder withDescription(String description) {
		this.description = description;
		return this;
	}

	public Training build() {
		Training training = new Training();
		training.setSchool(school);
		training.setStartYear(startYear);
		training.setEndYear(endYear);
		training.setSchoolDegree(schoolDegree);
		training.setSchoolFieldOfStudy(schoolFieldOfStudy);
		training.setSchoolGrade(schoolGrade);
		training.setSchoolActivities(schoolActivities);
		training.setDescription(description);
		return training;
	}

}
