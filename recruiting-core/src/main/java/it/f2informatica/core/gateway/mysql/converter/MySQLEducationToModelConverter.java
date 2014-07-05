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
package it.f2informatica.core.gateway.mysql.converter;

import it.f2informatica.core.gateway.EntityToModelConverter;
import it.f2informatica.core.model.EducationModel;
import it.f2informatica.mysql.domain.Education;
import org.springframework.stereotype.Component;

import static it.f2informatica.core.model.builder.EducationModelBuilder.educationModel;

@Component("mysqlEducationToModelConverter")
public class MySQLEducationToModelConverter extends EntityToModelConverter<Education, EducationModel> {

	@Override
	public EducationModel convert(Education education) {
		return (education == null) ? null :
			educationModel()
				.withId(String.valueOf(education.getId()))
				.inSchool(education.getSchoolName())
				.startedInYear(education.getStartYear())
				.finishedInYear(education.getEndYear())
				.withDegreeIn(education.getSchoolDegree())
				.fieldOfStudyIn(education.getFieldsOfStudy())
				.withGrade(education.getGrade())
				.withActivitiesIn(education.getActivities())
				.isInProgress(education.isCurrent())
				.withDescription(education.getDescription())
				.build();
	}

}
