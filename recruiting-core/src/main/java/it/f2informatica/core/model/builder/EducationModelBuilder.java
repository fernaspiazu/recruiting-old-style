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

import it.f2informatica.core.model.EducationModel;

public class EducationModelBuilder {

  private EducationModel education = new EducationModel();

  public static EducationModelBuilder educationModel() {
    return new EducationModelBuilder();
  }

  public EducationModelBuilder withId(String id) {
    education.setId(id);
    return this;
  }

  public EducationModelBuilder inSchool(String school) {
    education.setSchool(school);
    return this;
  }

  public EducationModelBuilder startedInYear(int year) {
    education.setStartYear(year);
    return this;
  }

  public EducationModelBuilder finishedInYear(int year) {
    education.setEndYear(year);
    education.setCurrent(false);
    return this;
  }

  public EducationModelBuilder isInProgress(boolean isCurrent) {
    if (isCurrent) {
      return inProgress();
    }
    education.setCurrent(false);
    return this;
  }

  public EducationModelBuilder inProgress() {
    education.setEndYear(-1);
    education.setCurrent(true);
    return this;
  }

  public EducationModelBuilder withDegreeIn(String degree) {
    education.setSchoolDegree(degree);
    return this;
  }

  public EducationModelBuilder fieldOfStudyIn(String schoolFieldOfStudy) {
    education.setSchoolFieldOfStudy(schoolFieldOfStudy);
    return this;
  }

  public EducationModelBuilder withGrade(String grade) {
    education.setSchoolGrade(grade);
    return this;
  }

  public EducationModelBuilder withActivitiesIn(String schoolActivities) {
    education.setSchoolActivities(schoolActivities);
    return this;
  }

  public EducationModelBuilder withDescription(String description) {
    education.setDescription(description);
    return this;
  }

  public EducationModel build() {
    return education;
  }

}
