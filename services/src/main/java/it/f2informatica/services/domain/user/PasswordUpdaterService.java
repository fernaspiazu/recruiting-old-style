package it.f2informatica.services.domain.user;

import it.f2informatica.services.requests.UpdatePasswordRequest;

public interface PasswordUpdaterService {

	boolean updatePassword(UpdatePasswordRequest request);

}
