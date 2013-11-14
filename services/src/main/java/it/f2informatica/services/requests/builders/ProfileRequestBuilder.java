package it.f2informatica.services.requests.builders;

import it.f2informatica.services.requests.EducationRequest;
import it.f2informatica.services.requests.ExperienceRequest;
import it.f2informatica.services.requests.LanguageRequest;
import it.f2informatica.services.requests.ProfileRequest;

public class ProfileRequestBuilder {

	private ProfileRequest profile = new ProfileRequest();

	public static ProfileRequestBuilder profileRequest() {
		return new ProfileRequestBuilder();
	}

	public ProfileRequestBuilder withExperienceIn(ExperienceRequestBuilder experience) {
		return withExperienceIn(experience.build());
	}

	public ProfileRequestBuilder withExperienceIn(ExperienceRequest experience) {
		profile.getExperiences().add(experience);
		return this;
	}

	public ProfileRequestBuilder withEducationIn(EducationRequestBuilder training) {
		return withEducationIn(training.build());
	}

	public ProfileRequestBuilder withEducationIn(EducationRequest education) {
		profile.getEducationList().add(education);
		return this;
	}

	public ProfileRequestBuilder withSkill(String skill) {
		profile.getSkills().add(skill);
		return this;
	}

	public ProfileRequestBuilder speaking(LanguageRequestBuilder language) {
		return speaking(language.build());
	}

	public ProfileRequestBuilder speaking(LanguageRequest language) {
		profile.getLanguages().add(language);
		return this;
	}

	public ProfileRequestBuilder withInterestsIn(String interestsIn) {
		profile.setInterests(interestsIn);
		return this;
	}

	public ProfileRequest build() {
		return profile;
	}

}
