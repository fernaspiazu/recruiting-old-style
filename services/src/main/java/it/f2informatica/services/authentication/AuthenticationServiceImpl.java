package it.f2informatica.services.authentication;

import it.f2informatica.services.gateway.UserRepositoryGateway;
import it.f2informatica.services.responses.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private UserRepositoryGateway userRepositoryGateway;

	@Override
	public AuthenticationResponse processLogin(String username) {
		return userRepositoryGateway.authenticationByUsername(username);
	}

}
