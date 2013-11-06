package it.f2informatica.mongodb.repositories;

import it.f2informatica.mongodb.domain.Profile;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProfileRepository extends PagingAndSortingRepository<Profile, String> {
}
