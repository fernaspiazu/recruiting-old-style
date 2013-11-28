package it.f2informatica.services.domain.consultant;

import it.f2informatica.services.model.ConsultantModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConsultantService {

	Page<ConsultantModel> showAllConsultants(Pageable pageable);

}
