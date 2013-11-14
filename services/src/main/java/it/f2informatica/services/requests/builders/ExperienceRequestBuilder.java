package it.f2informatica.services.requests.builders;

import it.f2informatica.services.requests.ExperienceRequest;

import java.util.Date;

public class ExperienceRequestBuilder {

	private ExperienceRequest experience = new ExperienceRequest();

	public static ExperienceRequestBuilder experienceRequest() {
		return new ExperienceRequestBuilder();
	}

	public ExperienceRequestBuilder inCompany(String companyName) {
		this.experience.setCompanyName(companyName);
		return this;
	}

	public ExperienceRequestBuilder fromPeriod(Date periodFrom) {
		this.experience.setPeriodFrom(periodFrom);
		return this;
	}

	public ExperienceRequestBuilder toPeriod(Date periodTo) {
		this.experience.setPeriodTo(periodTo);
		this.experience.setCurrent(false);
		return this;
	}

	public ExperienceRequestBuilder thisIsTheCurrentJob() {
		this.experience.setPeriodTo(null);
		this.experience.setCurrent(true);
		return this;
	}

	public ExperienceRequestBuilder inFunctionOf(String function) {
		this.experience.setFunction(function);
		return this;
	}

	public ExperienceRequestBuilder locatedAt(String location) {
		this.experience.setLocation(location);
		return this;
	}

	public ExperienceRequestBuilder withDescription(String description) {
		this.experience.setDescription(description);
		return this;
	}

	public ExperienceRequest build() {
		return experience;
	}

}
