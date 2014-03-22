package it.f2informatica.services.gateway;

import it.f2informatica.services.model.ConsultantModel;
import it.f2informatica.services.model.EducationModel;
import it.f2informatica.services.model.ExperienceModel;
import it.f2informatica.services.model.LanguageModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ConsultantRepositoryGateway {

	ConsultantModel findOneConsultant(String consultantId);

	Page<ConsultantModel> findAllConsultants(Pageable pageable);

	ConsultantModel savePersonalDetails(ConsultantModel consultantModel);

	boolean addExperience(ExperienceModel experienceModel, String consultantId);

	boolean updateExperience(ExperienceModel experienceModel, String consultantId);

	void removeExperience(String consultantId, String experienceId);

	ExperienceModel findOneExperience(String consultantId, String experienceId);

	boolean addLanguages(LanguageModel[] languageModelArray, String consultantId);

	boolean addSkills(String[] skills, String consultantId);

	boolean updateEducation(EducationModel educationModel, String consultantId);

	void removeEducation(String consultantId, String educationId);

	boolean addEducation(EducationModel educationModel, String consultantId);

	EducationModel findOneEducation(String consultantId, String educationId);
}
