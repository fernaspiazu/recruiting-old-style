package it.f2informatica.webapp.security;

import it.f2informatica.services.responses.LoginResponse;
import it.f2informatica.webapp.gateway.LoginServiceGateway;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class RecruitingUserDetailService implements UserDetailsService {
	private static final Logger log = Logger.getLogger(RecruitingUserDetailService.class);

	private AuthorityService authorityService;
	private LoginServiceGateway loginServiceGateway;

	@Autowired
	public void setLoginServiceGateway(LoginServiceGateway loginServiceGateway) {
		this.loginServiceGateway = loginServiceGateway;
	}

	@Autowired
	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("Loding user >>> " + username);
		LoginResponse loginResponse = loginServiceGateway.processLogin(username);
		return createUserDetails(loginResponse);
	}

	private UserDetails createUserDetails(LoginResponse userLogged) {
		return new UserDetails(userLogged, authorityService.createAuthorities(userLogged.getAuthorization()));
	}

}
