package it.f2informatica.core.authentication;

import it.f2informatica.core.responses.AuthenticationResponse;

public interface AuthenticationService {

	AuthenticationResponse processLogin(String username);

}
