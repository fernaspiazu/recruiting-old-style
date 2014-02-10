package it.f2informatica.mongodb.repositories.custom;

import it.f2informatica.mongodb.domain.Experience;
import it.f2informatica.mongodb.domain.Language;

import java.util.List;

public interface CustomConsultantRepository {

	Experience findExperience(String consultantId, String experienceId);

	boolean addExperience(Experience experience, String consultantId);

	boolean updateExperience(Experience experience, String consultantId);

	boolean addLanguage(Language language, String consultantId);

	boolean addLanguages(List<Language> languages, String consultantId);

	boolean addSkills(String[] skills, String consultantId);

	boolean removeExperience(String consultantId, String experienceId);
}
