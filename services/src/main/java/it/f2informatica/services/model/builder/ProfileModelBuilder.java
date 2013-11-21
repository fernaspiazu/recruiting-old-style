package it.f2informatica.services.model.builder;

import it.f2informatica.services.model.EducationModel;
import it.f2informatica.services.model.ExperienceModel;
import it.f2informatica.services.model.LanguageModel;
import it.f2informatica.services.model.ProfileModel;

import java.util.List;

public class ProfileModelBuilder {

	private ProfileModel profile = new ProfileModel();

	public static ProfileModelBuilder profile() {
		return new ProfileModelBuilder();
	}

	public ProfileModelBuilder withExperienceIn(ExperienceModelBuilder experience) {
		return withExperienceIn(experience.build());
	}

	public ProfileModelBuilder withExperienceIn(ExperienceModel experience) {
		profile.getExperiences().add(experience);
		return this;
	}

	public ProfileModelBuilder withExperiencesIn(List<ExperienceModel> experiences) {
		profile.getExperiences().addAll(experiences);
		return this;
	}

	public ProfileModelBuilder withEducationIn(EducationModelBuilder education) {
		return withEducationIn(education.build());
	}

	public ProfileModelBuilder withEducationIn(EducationModel education) {
		profile.getEducationList().add(education);
		return this;
	}

	public ProfileModelBuilder withEducationIn(List<EducationModel> educations) {
		profile.getEducationList().addAll(educations);
		return this;
	}

	public ProfileModelBuilder withSkill(String skill) {
		profile.getSkills().add(skill);
		return this;
	}

	public ProfileModelBuilder withSkills(List<String> skills) {
		profile.getSkills().addAll(skills);
		return this;
	}

	public ProfileModelBuilder speaking(LanguageModelBuilder language) {
		return speaking(language.build());
	}

	public ProfileModelBuilder speaking(LanguageModel language) {
		profile.getLanguages().add(language);
		return this;
	}

	public ProfileModelBuilder speakingLanguages(List<LanguageModel> languages) {
		profile.getLanguages().addAll(languages);
		return this;
	}

	public ProfileModelBuilder withInterestsIn(String interestsIn) {
		profile.setInterests(interestsIn);
		return this;
	}

	public ProfileModel build() {
		return profile;
	}

}
