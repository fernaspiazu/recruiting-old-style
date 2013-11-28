package it.f2informatica.mongodb.repositories;

import it.f2informatica.mongodb.domain.Consultant;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ConsultantRepository
		extends PagingAndSortingRepository<Consultant, String> {

	Consultant findByFiscalCode(String fiscalCode);

}
