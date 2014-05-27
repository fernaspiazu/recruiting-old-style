/*
 * =============================================================================
 *
 *   Copyright (c) 2014, Fernando Aspiazu
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */
package it.f2informatica.core.model.builder;

import it.f2informatica.core.model.ExperienceModel;

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
