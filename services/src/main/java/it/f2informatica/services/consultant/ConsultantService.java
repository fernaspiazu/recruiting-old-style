package it.f2informatica.services.consultant;

import it.f2informatica.services.model.ConsultantModel;
import it.f2informatica.services.model.EducationModel;
import it.f2informatica.services.model.ExperienceModel;
import it.f2informatica.services.model.LanguageModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConsultantService {

	ConsultantModel buildNewConsultantModel();

	Page<ConsultantModel> showAllConsultants(Pageable pageable);

	ConsultantModel savePersonalDetails(ConsultantModel consultantModel);

	ConsultantModel findConsultantById(String consultantId);

	String generateConsultantNumber();

	boolean addConsultantExperience(ExperienceModel experienceModel, String consultantId);

	boolean addLanguages(LanguageModel[] languageModelArray, String consultantId);

	boolean addSkills(String[] skills, String consultantId);

	ExperienceModel findExperience(String consultantId, String experienceId);

	boolean updateConsultantExperience(ExperienceModel experienceModel, String consultantId);

	void removeExperience(String consultantId, String experienceId);

	EducationModel findEducation(String consultantId, String educationId);

	boolean addConsultantEducation(EducationModel educationModel, String consultantId);

	boolean updateConsultantEducation(EducationModel educationModel, String consultantId);

	void removeEducation(String consultantId, String educationId);
}
