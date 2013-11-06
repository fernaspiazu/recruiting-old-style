package it.f2informatica.webapp.security;

import it.f2informatica.services.responses.LoginResponse;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserDetails extends org.springframework.security.core.userdetails.User {

	public UserDetails(LoginResponse userLogged, Collection<GrantedAuthority> authorities) {
		super(userLogged.getUsername(), userLogged.getPassword(), authorities);
	}

}
