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
package it.f2informatica.mongodb.repositories.custom;

import it.f2informatica.mongodb.domain.Education;
import it.f2informatica.mongodb.domain.Experience;
import it.f2informatica.mongodb.domain.Language;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

public interface ConsultantRepositoryCustom {

	int updateConsultantsPersonalDetails(Update updateFields, String consultantId);

	Experience findExperience(String consultantId, String experienceId);

	int addExperience(Experience experience, String consultantId);

	int updateExperience(Experience experience, String consultantId);

	int addLanguages(List<Language> languages, String consultantId);

	int addSkills(String[] skills, String consultantId);

	int removeExperience(String consultantId, String experienceId);

	Education findEducation(String consultantId, String educationId);

	int addEducation(Education education, String consultantId);

	int updateEducation(Education education, String consultantId);

	int removeEducation(String consultantId, String educationId);
}
