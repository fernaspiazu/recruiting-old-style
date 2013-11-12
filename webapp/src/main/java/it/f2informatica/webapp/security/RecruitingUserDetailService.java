package it.f2informatica.webapp.security;

import it.f2informatica.services.responses.AuthenticationResponse;
import it.f2informatica.webapp.gateway.AuthenticationServiceGateway;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class RecruitingUserDetailService implements UserDetailsService {
	private static final Logger log = Logger.getLogger(RecruitingUserDetailService.class);

	private AuthorityService authorityService;
	private AuthenticationServiceGateway loginServiceGateway;

	@Autowired
	public void setLoginServiceGateway(AuthenticationServiceGateway loginServiceGateway) {
		this.loginServiceGateway = loginServiceGateway;
	}

	@Autowired
	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("Loding user >>> " + username);
		AuthenticationResponse authenticationResponse = loginServiceGateway.processLogin(username);
		return createUserDetails(authenticationResponse);
	}

	private UserDetails createUserDetails(AuthenticationResponse userLogged) {
		return new UserDetails(userLogged, authorityService.createAuthorities(userLogged.getAuthorization()));
	}

}
