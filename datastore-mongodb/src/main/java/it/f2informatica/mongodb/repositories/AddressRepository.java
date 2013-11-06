package it.f2informatica.mongodb.repositories;

import it.f2informatica.mongodb.domain.Address;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AddressRepository extends PagingAndSortingRepository<Address, String> {
}
