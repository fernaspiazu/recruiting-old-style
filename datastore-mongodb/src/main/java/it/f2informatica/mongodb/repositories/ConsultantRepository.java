package it.f2informatica.mongodb.repositories;

import it.f2informatica.mongodb.domain.Consultant;
import it.f2informatica.mongodb.domain.Profile;
import it.f2informatica.mongodb.repositories.customrepositories.AdditionalConsultantRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ConsultantRepository
		extends AdditionalConsultantRepository, PagingAndSortingRepository<Consultant, String> {

	Consultant findByConsultantNo(String consultantNo);

	Consultant findByFiscalCode(String fiscalCode);

}
