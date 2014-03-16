package it.f2informatica.test.mongodb.builders;

import it.f2informatica.mongodb.domain.Experience;

import java.util.Date;
import java.util.GregorianCalendar;

public class ExperienceDataBuilder {

	private String id = "0000000000";
	private String companyName = "Accenture SPA";
	private String function = "Functional Analyst";
	private String location = "Torino";
	private Date periodFrom = new GregorianCalendar(2012, 1, 15).getTime();
	private Date periodTo = new GregorianCalendar(2013, 12, 15).getTime();
	private boolean isCurrent = false;
	private String description = "No description";

	public static ExperienceDataBuilder experience() {
		return new ExperienceDataBuilder();
	}

	public ExperienceDataBuilder withId(String id) {
		this.id = id;
		return this;
	}

	public ExperienceDataBuilder inCompany(String companyName) {
		this.companyName = companyName;
		return this;
	}

	public ExperienceDataBuilder fromPeriod(Date periodFrom) {
		this.periodFrom = periodFrom;
		return this;
	}

	public ExperienceDataBuilder toPeriod(Date periodTo) {
		this.periodTo = periodTo;
		this.isCurrent = false;
		return this;
	}

	public ExperienceDataBuilder thisIsTheCurrentJob() {
		this.isCurrent = true;
		this.periodTo = null;
		return this;
	}

	public ExperienceDataBuilder inFunctionOf(String function) {
		this.function = function;
		return this;
	}

	public ExperienceDataBuilder locatedAt(String location) {
		this.location =location;
		return this;
	}

	public ExperienceDataBuilder withDescription(String description) {
		this.description = description;
		return this;
	}

	public Experience build() {
		Experience experience = new Experience();
		experience.setId(id);
		experience.setCompanyName(companyName);
		experience.setPosition(function);
		experience.setLocation(location);
		experience.setPeriodFrom(periodFrom);
		experience.setPeriodTo(periodTo);
		experience.setCurrent(isCurrent);
		experience.setDescription(description);
		return experience;
	}

}
