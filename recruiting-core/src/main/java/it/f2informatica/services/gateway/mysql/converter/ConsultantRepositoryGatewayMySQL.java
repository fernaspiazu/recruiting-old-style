package it.f2informatica.services.gateway.mysql.converter;

import it.f2informatica.services.gateway.ConsultantRepositoryGateway;
import it.f2informatica.services.model.ConsultantModel;
import it.f2informatica.services.model.EducationModel;
import it.f2informatica.services.model.ExperienceModel;
import it.f2informatica.services.model.LanguageModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ConsultantRepositoryGatewayMySQL implements ConsultantRepositoryGateway {

  @Override
  public ConsultantModel findOneConsultant(String consultantId) {
    return null;
  }

  @Override
  public Page<ConsultantModel> findAllConsultants(Pageable pageable) {
    return null;
  }

  @Override
  public boolean updatePersonalDetails(ConsultantModel consultantModel, String consultantId) {
    return false;
  }

  @Override
  public ConsultantModel savePersonalDetails(ConsultantModel consultantModel) {
    return null;
  }

  @Override
  public boolean addExperience(ExperienceModel experienceModel, String consultantId) {
    return false;
  }

  @Override
  public boolean updateExperience(ExperienceModel experienceModel, String consultantId) {
    return false;
  }

  @Override
  public void removeExperience(String consultantId, String experienceId) {

  }

  @Override
  public ExperienceModel findOneExperience(String consultantId, String experienceId) {
    return null;
  }

  @Override
  public boolean addLanguages(LanguageModel[] languageModelArray, String consultantId) {
    return false;
  }

  @Override
  public boolean addSkills(String[] skills, String consultantId) {
    return false;
  }

  @Override
  public boolean updateEducation(EducationModel educationModel, String consultantId) {
    return false;
  }

  @Override
  public void removeEducation(String consultantId, String educationId) {

  }

  @Override
  public boolean addEducation(EducationModel educationModel, String consultantId) {
    return false;
  }

  @Override
  public EducationModel findOneEducation(String consultantId, String educationId) {
    return null;
  }

}
