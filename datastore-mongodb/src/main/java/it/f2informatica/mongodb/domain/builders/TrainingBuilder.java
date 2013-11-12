package it.f2informatica.mongodb.domain.builders;

import it.f2informatica.mongodb.domain.Training;

public class TrainingBuilder {

	private Training training = new Training();

	public static TrainingBuilder training() {
		return new TrainingBuilder();
	}

	public TrainingBuilder inSchool(String school) {
		training.setSchool(school);
		return this;
	}

	public TrainingBuilder startedInYear(int year) {
		training.setStartYear(year);
		return this;
	}

	public TrainingBuilder finishedInYear(int year) {
		training.setEndYear(year);
		training.setCurrent(false);
		return this;
	}

	public TrainingBuilder notYetFinished() {
		training.setEndYear(-1);
		training.setCurrent(true);
		return this;
	}

	public TrainingBuilder withDegreeIn(String degree) {
		training.setSchoolDegree(degree);
		return this;
	}

	public TrainingBuilder fieldOfStudyIn(String schoolFieldOfStudy) {
		training.setSchoolFieldOfStudy(schoolFieldOfStudy);
		return this;
	}

	public TrainingBuilder withGrade(String grade) {
		training.setSchoolGrade(grade);
		return this;
	}

	public TrainingBuilder withActivitiesIn(String schoolActivities) {
		training.setSchoolActivities(schoolActivities);
		return this;
	}

	public TrainingBuilder withDescription(String description) {
		training.setDescription(description);
		return this;
	}

	public Training build() {
		return training;
	}

}
