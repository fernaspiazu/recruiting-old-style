package it.f2informatica.webapp.test.security;

import com.google.common.collect.Lists;
import it.f2informatica.core.Authority;
import it.f2informatica.core.model.AuthenticationModel;
import it.f2informatica.core.services.AuthenticationService;
import it.f2informatica.webapp.security.AuthorityService;
import it.f2informatica.webapp.security.DatabaseUserDetailService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseUserDetailServiceTest {

  @Mock
  private AuthenticationService authenticationService;

  @Mock
  private AuthorityService authorityService;

  private UserDetails userAuthenticated;

  @Before
  public void setUp() {
    UserDetailsService userDetailsService = new DatabaseUserDetailService();
    ((DatabaseUserDetailService) userDetailsService).setAuthenticationService(authenticationService);
    ((DatabaseUserDetailService) userDetailsService).setAuthorityService(authorityService);

    when(authenticationService.processLogin("username1")).thenReturn(createResponse());
    when(authorityService.createAuthorities("role_admin")).thenReturn(getGrantedAuthorities());
    userAuthenticated = userDetailsService.loadUserByUsername("username1");
  }

  @Test
  public void loadUserByUsername() {
    assertThat(userAuthenticated).isNotNull();
  }

  @Test
  public void testUserAuthenticatedUsername() {
    assertThat(userAuthenticated.getUsername()).isEqualTo("username1");
  }

  @Test
  public void testUserAuthenticatedPassword() {
    assertThat(userAuthenticated.getPassword()).isEqualTo("password1");
  }

  private List<GrantedAuthority> getGrantedAuthorities() {
    return Lists.<GrantedAuthority>newArrayList(
      new SimpleGrantedAuthority(Authority.ROLE_ADMIN.toString())
    );
  }

  private static AuthenticationModel createResponse() {
    AuthenticationModel authenticationModel = new AuthenticationModel();
    authenticationModel.setUsername("username1");
    authenticationModel.setPassword("password1");
    authenticationModel.setAuthorization("role_admin");
    return authenticationModel;
  }

}
