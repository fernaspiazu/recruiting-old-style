package it.f2informatica.services.gateway;

import it.f2informatica.services.model.ConsultantModel;
import it.f2informatica.services.model.ExperienceModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ConsultantRepositoryGateway {

	Page<ConsultantModel> findAllConsultants(Pageable pageable);

	ConsultantModel saveMasterData(ConsultantModel consultantModel);

	ConsultantModel findConsultantById(String consultantId);

	boolean saveConsultantExperience(ExperienceModel experienceModel, String consultantId);

	List<ExperienceModel> findExperiencesByConsultantId(String consultantId);
}
