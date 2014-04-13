package it.f2informatica.webapp.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface AuthorityService {

  public Collection<GrantedAuthority> createAuthorities(String authorization);

}
