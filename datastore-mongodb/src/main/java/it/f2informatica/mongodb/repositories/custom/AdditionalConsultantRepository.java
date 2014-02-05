package it.f2informatica.mongodb.repositories.custom;

import it.f2informatica.mongodb.domain.Experience;
import it.f2informatica.mongodb.domain.Language;
import it.f2informatica.mongodb.domain.Profile;

import java.util.List;

public interface AdditionalConsultantRepository {

	Profile findProfileByConsultantId(String consultantId);

	boolean addExperience(Experience experience, String consultantId);

	boolean addLanguage(Language language, String consultantId);

	boolean addLanguages(List<Language> languages, String consultantId);

	boolean addSkills(String[] skills, String consultantId);

	List<Experience> findLimitedExperiences(String consultantId);
}
