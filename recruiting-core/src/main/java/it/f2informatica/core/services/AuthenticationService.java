package it.f2informatica.core.services;

import it.f2informatica.core.model.AuthenticationModel;

public interface AuthenticationService {

	AuthenticationModel processLogin(String username);

}
