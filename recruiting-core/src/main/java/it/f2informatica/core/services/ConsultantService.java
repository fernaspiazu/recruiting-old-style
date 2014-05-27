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
package it.f2informatica.core.services;

import com.google.common.base.Optional;
import it.f2informatica.core.model.ConsultantModel;
import it.f2informatica.core.model.EducationModel;
import it.f2informatica.core.model.ExperienceModel;
import it.f2informatica.core.model.LanguageModel;
import it.f2informatica.core.model.query.ConsultantSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConsultantService {

  ConsultantModel buildNewConsultantModel();

  Page<ConsultantModel> paginateConsultants(Pageable pageable);

  Page paginateConsultants(ConsultantSearchCriteria searchCriteria, Pageable pageable);

  Optional<ConsultantModel> findConsultantById(String consultantId);

  String generateConsultantNumber();

  ConsultantModel savePersonalDetails(ConsultantModel consultantModel);

  void updatePersonalDetails(ConsultantModel consultantModel, String consultantId);

  void addConsultantExperience(ExperienceModel experienceModel, String consultantId);

  void addLanguages(LanguageModel[] languageModelArray, String consultantId);

  void addSkills(String[] skills, String consultantId);

  Optional<ExperienceModel> findExperience(String consultantId, String experienceId);

  void updateConsultantExperience(ExperienceModel experienceModel, String consultantId);

  void removeExperience(String consultantId, String experienceId);

  Optional<EducationModel> findEducation(String consultantId, String educationId);

  void addConsultantEducation(EducationModel educationModel, String consultantId);

  void updateConsultantEducation(EducationModel educationModel, String consultantId);

  void removeEducation(String consultantId, String educationId);
}
