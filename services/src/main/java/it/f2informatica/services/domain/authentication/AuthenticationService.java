package it.f2informatica.services.domain.authentication;

import it.f2informatica.services.responses.AuthenticationResponse;

public interface AuthenticationService {

	AuthenticationResponse processLogin(String username);

}
