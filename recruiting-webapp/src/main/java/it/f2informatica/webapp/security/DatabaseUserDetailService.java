package it.f2informatica.webapp.security;

import it.f2informatica.core.model.AuthenticationModel;
import it.f2informatica.core.services.AuthenticationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class DatabaseUserDetailService implements UserDetailsService {
  private static final Logger logger = Logger.getLogger(DatabaseUserDetailService.class);

  private AuthorityService authorityService;
  private AuthenticationService authenticationService;

  @Autowired
  public void setAuthorityService(AuthorityService authorityService) {
    this.authorityService = authorityService;
  }

  @Autowired
  public void setAuthenticationService(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AuthenticationModel authentication = authenticationService.processLogin(username);
    logger.info("Authenticating with username: '" + authentication.getUsername() + "', with role: '" + authentication.getAuthorization() + "'");
    return createUserDetails(authentication);
  }

  private UserDetails createUserDetails(AuthenticationModel userLogged) {
    return new UserDetails(userLogged, authorityService.createAuthorities(userLogged.getAuthorization()));
  }

}
