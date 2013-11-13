package it.f2informatica.webapp.gateway;

import it.f2informatica.services.domain.consultant.ConsultantService;
import it.f2informatica.services.responses.ConsultantPaginatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ConsultantServiceGateway {

	private ConsultantService consultantService;

	@Autowired
	public void setConsultantService(ConsultantService consultantService) {
		this.consultantService = consultantService;
	}

	public Page<ConsultantPaginatedResponse> findAllConsultants(Pageable pageable) {
		return consultantService.findAll(pageable);
	}

}
