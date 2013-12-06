package it.f2informatica.mongodb.repositories.customrepositories;

import it.f2informatica.mongodb.domain.Experience;
import it.f2informatica.mongodb.domain.Profile;

public interface AdditionalConsultantRepository {

	Profile findProfileByConsultantId(String consultantId);

	boolean addExperience(Experience experience, String consultantId);
}
