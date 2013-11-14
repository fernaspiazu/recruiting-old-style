package it.f2informatica.mongodb.domain.builders;

import it.f2informatica.mongodb.domain.Education;
import it.f2informatica.mongodb.domain.Experience;
import it.f2informatica.mongodb.domain.Language;
import it.f2informatica.mongodb.domain.Profile;

import java.util.List;

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

	public ProfileBuilder withExperiencesIn(List<Experience> experiences) {
		profile.getExperiences().addAll(experiences);
		return this;
	}

	public ProfileBuilder withEducationIn(EducationBuilder training) {
		return withEducationIn(training.build());
	}

	public ProfileBuilder withEducationIn(Education education) {
		profile.getEducationList().add(education);
		return this;
	}

	public ProfileBuilder withEducationIn(List<Education> educations) {
		profile.getEducationList().addAll(educations);
		return this;
	}

	public ProfileBuilder withSkill(String skill) {
		profile.getSkills().add(skill);
		return this;
	}

	public ProfileBuilder withSkills(List<String> skills) {
		profile.getSkills().addAll(skills);
		return this;
	}

	public ProfileBuilder speaking(LanguageBuilder language) {
		return speaking(language.build());
	}

	public ProfileBuilder speaking(Language language) {
		profile.getLanguages().add(language);
		return this;
	}

	public ProfileBuilder speakingLanguages(List<Language> languages) {
		profile.getLanguages().addAll(languages);
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
