package it.f2informatica.mongodb.repositories;

import it.f2informatica.mongodb.domain.Consultant;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ConsultantRepository extends CustomConsultantRepository, PagingAndSortingRepository<Consultant, String> {

	Consultant findByConsultantNo(String consultantNo);

	Consultant findByFiscalCode(String fiscalCode);

}
