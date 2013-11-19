package it.f2informatica.webapp.gateway;

import it.f2informatica.services.domain.authentication.AuthenticationService;
import it.f2informatica.services.responses.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceGateway {

	@Autowired
	private AuthenticationService authenticationService;

	public AuthenticationResponse processLogin(String username) {
		return authenticationService.processLogin(username);
	}

}
