package it.f2informatica.webapp.test.security;

import com.google.common.collect.Lists;
import it.f2informatica.services.responses.LoginResponse;
import it.f2informatica.webapp.gateway.LoginServiceGateway;
import it.f2informatica.webapp.security.Authority;
import it.f2informatica.webapp.security.AuthorityService;
import it.f2informatica.webapp.security.RecruitingUserDetailService;
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
public class RecruitingUserDetailServiceTest {

	@Mock
	private LoginServiceGateway loginServiceGateway;

	@Mock
	private AuthorityService authorityService;

	private UserDetails userAuthenticated;

	@Before
	public void setUp() {
		UserDetailsService userDetailsService = new RecruitingUserDetailService();
		((RecruitingUserDetailService) userDetailsService).setLoginServiceGateway(loginServiceGateway);
		((RecruitingUserDetailService) userDetailsService).setAuthorityService(authorityService);

		when(loginServiceGateway.processLogin("username1")).thenReturn(createResponse());
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

	private static LoginResponse createResponse() {
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setUsername("username1");
		loginResponse.setPassword("password1");
		loginResponse.setAuthorization("role_admin");
		return loginResponse;
	}

}
