package it.f2informatica.webapp.gateway;

import it.f2informatica.services.responses.LoginResponse;
import it.f2informatica.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceGateway {

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public LoginResponse processLogin(String username) {
		return userService.processLogin(username);
	}

}
