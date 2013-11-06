package it.f2informatica.mongodb.repositories;

import it.f2informatica.mongodb.domain.Person;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PersonRepository extends PagingAndSortingRepository<Person, String> {
}
