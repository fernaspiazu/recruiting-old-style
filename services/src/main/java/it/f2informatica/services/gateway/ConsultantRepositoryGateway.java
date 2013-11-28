package it.f2informatica.services.gateway;

import it.f2informatica.services.model.ConsultantModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConsultantRepositoryGateway {

	Page<ConsultantModel> findAllConsultants(Pageable pageable);

	ConsultantModel savePersonalData(ConsultantModel consultantModel);
}
