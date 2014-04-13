package it.f2informatica.core.services;

import it.f2informatica.core.gateway.UserRepositoryGateway;
import it.f2informatica.core.model.AuthenticationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private UserRepositoryGateway userRepositoryGateway;

	@Override
	public AuthenticationModel processLogin(String username) {
		return userRepositoryGateway.authenticationByUsername(username);
	}

}
