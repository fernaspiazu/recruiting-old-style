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
