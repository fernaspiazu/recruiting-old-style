package it.f2informatica.services.domain.consultant;

import it.f2informatica.services.requests.ConsultantRequest;
import it.f2informatica.services.responses.ConsultantPaginatedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConsultantService {

	Page<ConsultantPaginatedResponse> findAll(Pageable pageable);

	String saveConsultant(ConsultantRequest request);
}
