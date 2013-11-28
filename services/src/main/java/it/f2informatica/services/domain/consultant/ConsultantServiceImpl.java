package it.f2informatica.services.domain.consultant;

import it.f2informatica.services.gateway.ConsultantRepositoryGateway;
import it.f2informatica.services.model.ConsultantModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ConsultantServiceImpl implements ConsultantService {

	@Autowired
	private ConsultantRepositoryGateway consultantRepositoryGateway;

	@Override
	public Page<ConsultantModel> showAllConsultants(Pageable pageable) {
		return consultantRepositoryGateway.findAllConsultants(pageable);
	}

}
