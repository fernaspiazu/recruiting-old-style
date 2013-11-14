package it.f2informatica.services.domain.consultant.impl;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import it.f2informatica.mongodb.domain.Consultant;
import it.f2informatica.mongodb.repositories.ConsultantRepository;
import it.f2informatica.services.domain.consultant.ConsultantService;
import it.f2informatica.services.requests.ConsultantRequest;
import it.f2informatica.services.responses.ConsultantPaginatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ConsultantServiceImpl implements ConsultantService {

	@Autowired
	private ConsultantRepository consultantRepository;

	@Override
	public Page<ConsultantPaginatedResponse> findAll(Pageable pageable) {
		Page<Consultant> consultants = consultantRepository.findAll(pageable);
		return new PageImpl<>(Lists.newArrayList(
				Iterables.transform(consultants, new ConsultantToConsultantPaginationResponseTransformer())
		));
	}

	@Override
	public String saveConsultant(ConsultantRequest request) {
		Consultant consultant = new ConsultantRequestToConsultantTransformer().apply(request);
		Consultant savedConsultant = consultantRepository.save(consultant);
		return String.valueOf(savedConsultant.getId());
	}

}
