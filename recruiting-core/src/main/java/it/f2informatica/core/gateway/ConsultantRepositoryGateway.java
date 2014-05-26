package it.f2informatica.core.gateway;

import it.f2informatica.core.model.ConsultantModel;
import it.f2informatica.core.model.EducationModel;
import it.f2informatica.core.model.ExperienceModel;
import it.f2informatica.core.model.LanguageModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConsultantRepositoryGateway {

  ConsultantModel findOneConsultant(String consultantId);

  Page<ConsultantModel> findAllConsultants(Pageable pageable);

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
