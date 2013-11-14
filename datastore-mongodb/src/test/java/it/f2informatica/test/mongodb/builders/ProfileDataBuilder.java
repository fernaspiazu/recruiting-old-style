package it.f2informatica.test.mongodb.builders;

import com.google.common.collect.Lists;
import it.f2informatica.mongodb.domain.Experience;
import it.f2informatica.mongodb.domain.Language;
import it.f2informatica.mongodb.domain.Profile;
import it.f2informatica.mongodb.domain.Education;

import java.util.List;

public class ProfileDataBuilder {

	private List<Experience> experiences = Lists.newArrayList();
	private List<Education> educationList = Lists.newArrayList();
	private List<String> skills = Lists.newArrayList();
	private List<Language> languages = Lists.newArrayList();
	private String interests = "Programming, Swimming, Running";

	public static ProfileDataBuilder profile() {
		return new ProfileDataBuilder();
	}

	public ProfileDataBuilder withExperienceIn(ExperienceDataBuilder experience) {
		return withExperienceIn(experience.build());
	}

	public ProfileDataBuilder withExperienceIn(Experience experience) {
		this.experiences.add(experience);
		return this;
	}

	public ProfileDataBuilder withTrainingIn(EducationDataBuilder training) {
		return withTrainingIn(training.build());
	}

	public ProfileDataBuilder withTrainingIn(Education education) {
		this.educationList.add(education);
		return this;
	}

	public ProfileDataBuilder withSkill(String skill) {
		this.skills.add(skill);
		return this;
	}

	public ProfileDataBuilder speaking(LanguageDataBuilder language) {
		return speaking(language.build());
	}

	public ProfileDataBuilder speaking(Language language) {
		this.languages.add(language);
		return this;
	}

	public ProfileDataBuilder withInterestsIn(String interestsIn) {
		this.interests = interestsIn;
		return this;
	}

	public Profile build() {
		Profile profile = new Profile();
		profile.setExperiences(experiences);
		profile.setEducationList(educationList);
		profile.setSkills(skills);
		profile.setLanguages(languages);
		profile.setInterests(interests);
		return profile;
	}

}
