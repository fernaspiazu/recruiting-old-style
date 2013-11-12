package it.f2informatica.services.domain.authentication.impl;

import it.f2informatica.mongodb.domain.User;
import it.f2informatica.mongodb.repositories.UserRepository;
import it.f2informatica.services.domain.authentication.AuthenticationService;
import it.f2informatica.services.responses.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	private UserRepository userRepository;

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public AuthenticationResponse processLogin(String username) {
		User user = userRepository.findByUsername(username);
		AuthenticationResponse authenticationResponse = new AuthenticationResponse();
		authenticationResponse.setUsername(user.getUsername());
		authenticationResponse.setPassword(user.getPassword());
		authenticationResponse.setAuthorization(user.getRole().getName());
		return authenticationResponse;
	}

}
