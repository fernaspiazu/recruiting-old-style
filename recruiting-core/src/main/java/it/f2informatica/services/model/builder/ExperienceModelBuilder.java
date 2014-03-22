package it.f2informatica.services.model.builder;

import it.f2informatica.services.model.ExperienceModel;

import java.util.Calendar;
import java.util.Date;

public class ExperienceModelBuilder {

	private ExperienceModel experience = new ExperienceModel();

	public static ExperienceModelBuilder experienceModel() {
		return new ExperienceModelBuilder();
	}

	public ExperienceModelBuilder withId(String id) {
		this.experience.setId(id);
		return this;
	}

	public ExperienceModelBuilder inCompany(String companyName) {
		this.experience.setCompanyName(companyName);
		return this;
	}

	public ExperienceModelBuilder fromPeriod(Date periodFrom) {
		this.experience.setPeriodFrom(periodFrom);
		this.experience.setMonthFrom(getMonth(periodFrom));
		this.experience.setYearFrom(getYear(periodFrom));
		return this;
	}

	public ExperienceModelBuilder toPeriod(Date periodTo) {
		this.experience.setPeriodTo(periodTo);
		this.experience.setCurrent(false);
		if (periodTo != null) {
			this.experience.setMonthTo(getMonth(periodTo));
			this.experience.setYearTo(getYear(periodTo));
		}
		return this;
	}

	private String getMonth(Date date) {
		return String.valueOf(getCalendar(date).get(Calendar.MONTH));
	}

	private String getYear(Date date) {
		return String.valueOf(getCalendar(date).get(Calendar.YEAR));
	}

	private Calendar getCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	public ExperienceModelBuilder isThisTheCurrentJob(boolean isCurrent) {
		if (isCurrent) {
			return thisIsTheCurrentJob();
		}
		this.experience.setCurrent(false);
		return this;
	}

	public ExperienceModelBuilder thisIsTheCurrentJob() {
		this.experience.setPeriodTo(null);
		this.experience.setCurrent(true);
		return this;
	}

	public ExperienceModelBuilder withPosition(String position) {
		this.experience.setPosition(position);
		return this;
	}

	public ExperienceModelBuilder locatedAt(String location) {
		this.experience.setLocality(location);
		return this;
	}

	public ExperienceModelBuilder withDescription(String description) {
		this.experience.setDescription(description);
		return this;
	}

	public ExperienceModel build() {
		return experience;
	}

}
