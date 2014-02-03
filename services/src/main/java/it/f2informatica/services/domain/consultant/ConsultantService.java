package it.f2informatica.services.domain.consultant;

import it.f2informatica.services.model.ConsultantModel;
import it.f2informatica.services.model.ExperienceModel;
import it.f2informatica.services.model.LanguageModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ConsultantService {

	ConsultantModel buildNewConsultantModel();

	ExperienceModel buildNewExperienceModel();

	Page<ConsultantModel> showAllConsultants(Pageable pageable);

	ConsultantModel registerConsultantMasterData(ConsultantModel consultantModel);

	ConsultantModel findConsultantById(String consultantId);

	String generateConsultantNumber();

	boolean addConsultantExperience(ExperienceModel experienceModel, String consultantId);

	List<ExperienceModel> findExperiences(String consultantId);

	List<ExperienceModel> findMinimalExperiences(String consultantId);

	LanguageModel buildNewLanguageModel();

	boolean addLanguage(LanguageModel languageModel, String consultantId);

	boolean addLanguages(LanguageModel[] languageModelArray, String consultantId);

	boolean addSkills(String[] skills, String consultantId);
}
