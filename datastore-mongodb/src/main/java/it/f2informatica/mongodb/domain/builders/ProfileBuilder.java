package it.f2informatica.mongodb.domain.builders;

import it.f2informatica.mongodb.domain.Experience;
import it.f2informatica.mongodb.domain.Language;
import it.f2informatica.mongodb.domain.Profile;
import it.f2informatica.mongodb.domain.Training;

public class ProfileBuilder {

	private Profile profile = new Profile();

	public static ProfileBuilder profile() {
		return new ProfileBuilder();
	}

	public ProfileBuilder withExperienceIn(ExperienceBuilder experience) {
		return withExperienceIn(experience.build());
	}

	public ProfileBuilder withExperienceIn(Experience experience) {
		profile.getExperiences().add(experience);
		return this;
	}

	public ProfileBuilder withTrainingIn(TrainingBuilder training) {
		return withTrainingIn(training.build());
	}

	public ProfileBuilder withTrainingIn(Training training) {
		profile.getTrainingList().add(training);
		return this;
	}

	public ProfileBuilder withSkill(String skill) {
		profile.getSkills().add(skill);
		return this;
	}

	public ProfileBuilder speaking(LanguageBuilder language) {
		return speaking(language.build());
	}

	public ProfileBuilder speaking(Language language) {
		profile.getLanguages().add(language);
		return this;
	}

	public ProfileBuilder withInterestsIn(String interestsIn) {
		profile.setInterests(interestsIn);
		return this;
	}

	public Profile build() {
		return profile;
	}

}
