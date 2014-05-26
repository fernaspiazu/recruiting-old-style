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
