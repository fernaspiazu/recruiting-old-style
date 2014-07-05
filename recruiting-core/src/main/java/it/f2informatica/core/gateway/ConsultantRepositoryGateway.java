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
package it.f2informatica.core.gateway;

import it.f2informatica.core.model.ConsultantModel;
import it.f2informatica.core.model.EducationModel;
import it.f2informatica.core.model.ExperienceModel;
import it.f2informatica.core.model.LanguageModel;
import it.f2informatica.core.model.query.ConsultantSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConsultantRepositoryGateway {

	ConsultantModel findOneConsultant(String consultantId);

	Page<ConsultantModel> findAllConsultants(Pageable pageable);

	Page<ConsultantModel> paginateConsultants(ConsultantSearchCriteria searchCriteria, Pageable pageable);

	void updatePersonalDetails(ConsultantModel consultantModel, String consultantId);

	ConsultantModel savePersonalDetails(ConsultantModel consultantModel);

	void addExperience(ExperienceModel experienceModel, String consultantId);

	void updateExperience(ExperienceModel experienceModel, String consultantId);

	void removeExperience(String consultantId, String experienceId);

	ExperienceModel findOneExperience(String consultantId, String experienceId);

	void addLanguages(LanguageModel[] languageModelArray, String consultantId);

	void addSkills(String[] skills, String consultantId);

	void updateEducation(EducationModel educationModel, String consultantId);

	void removeEducation(String consultantId, String educationId);

	void addEducation(EducationModel educationModel, String consultantId);

	EducationModel findOneEducation(String consultantId, String educationId);
}
