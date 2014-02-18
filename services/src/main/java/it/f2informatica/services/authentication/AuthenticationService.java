package it.f2informatica.services.authentication;

import it.f2informatica.services.responses.AuthenticationResponse;

public interface AuthenticationService {

	AuthenticationResponse processLogin(String username);

}
