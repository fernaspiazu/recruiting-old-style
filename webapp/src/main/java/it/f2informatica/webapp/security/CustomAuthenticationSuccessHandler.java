package it.f2informatica.webapp.security;

import it.f2informatica.services.responses.UserResponse;
import it.f2informatica.webapp.gateway.UserServiceGateway;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	private static final Logger log = Logger.getLogger(CustomAuthenticationSuccessHandler.class);

	private UserServiceGateway userServiceGateway;

	@Autowired
	public void setUserServiceGateway(UserServiceGateway userServiceGateway) {
		this.userServiceGateway = userServiceGateway;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
										Authentication authentication) throws ServletException, IOException {
		String username = authentication.getName();
		UserResponse user = setUserInSession(request.getSession(true), username);
		log.info("user in session: [username: " + user.getUsername() + ", id: " + user.getUserId() + "]");
		super.handle(request, response, authentication);
	}

	private UserResponse setUserInSession(HttpSession session, String username) {
		UserResponse user = userServiceGateway.getAuthenticatedUser(username);
		session.setAttribute("user", user);
		return user;
	}
}
