package it.f2informatica.core.services;

import it.f2informatica.core.model.ConsultantModel;
import it.f2informatica.core.model.EducationModel;
import it.f2informatica.core.model.ExperienceModel;
import it.f2informatica.core.model.LanguageModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConsultantService {

  ConsultantModel buildNewConsultantModel();

  Page<ConsultantModel> paginateConsultants(Pageable pageable);

  ConsultantModel findConsultantById(String consultantId);

  String generateConsultantNumber();

  ConsultantModel savePersonalDetails(ConsultantModel consultantModel);

  void updatePersonalDetails(ConsultantModel consultantModel, String consultantId);

  boolean addConsultantExperience(ExperienceModel experienceModel, String consultantId);

  boolean addLanguages(LanguageModel[] languageModelArray, String consultantId);

  boolean addSkills(String[] skills, String consultantId);

  ExperienceModel findExperience(String consultantId, String experienceId);

  void updateConsultantExperience(ExperienceModel experienceModel, String consultantId);

  void removeExperience(String consultantId, String experienceId);

  EducationModel findEducation(String consultantId, String educationId);

  boolean addConsultantEducation(EducationModel educationModel, String consultantId);

  void updateConsultantEducation(EducationModel educationModel, String consultantId);

  void removeEducation(String consultantId, String educationId);
}
