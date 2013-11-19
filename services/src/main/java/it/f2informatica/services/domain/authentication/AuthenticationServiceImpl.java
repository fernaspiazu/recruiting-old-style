package it.f2informatica.services.domain.authentication;

import it.f2informatica.mongodb.domain.User;
import it.f2informatica.mongodb.repositories.UserRepository;
import it.f2informatica.services.responses.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private UserRepository userRepository;

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
