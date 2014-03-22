package it.f2informatica.webapp.test.security;

import com.google.common.collect.Lists;
import it.f2informatica.services.Authority;
import it.f2informatica.services.responses.AuthenticationResponse;
import it.f2informatica.webapp.security.SecurityAccessor;
import it.f2informatica.webapp.security.UserDetails;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SecurityContextHolder.class})
public class SecurityAccessorTest {

	@Mock
	private SecurityContext securityContext;

	@Mock
	private Authentication authentication;

	private SecurityAccessor securityAccessor;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		PowerMockito.mockStatic(SecurityContextHolder.class);
		when(SecurityContextHolder.getContext()).thenReturn(securityContext);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.getName()).thenReturn("username");
		when(authentication.getAuthorities()).thenReturn(getAuthorities());
		securityAccessor = new SecurityAccessor();
	}

	@Test
	public void testSecurityContextHolder() {
		SecurityContext context = SecurityContextHolder.getContext();
		assertThat(context.getAuthentication().getName()).isEqualTo("username");
		assertThat(context.getAuthentication().getAuthorities().iterator().next().getAuthority())
				.isEqualTo(Authority.ROLE_ADMIN.toString());
	}

	@Test
	public void getCurrentUsername() {
		assertThat(securityAccessor.getCurrentUsername()).isEqualTo("username");
	}

	@Test
	public void isCurrentUserAdmin() {
		when(authentication.getPrincipal()).thenReturn(createUserDetails(Authority.ROLE_ADMIN));
		assertThat(securityAccessor.isCurrentUserAdmin()).isTrue();
	}

	@Test
	public void isNotCurrentUserAdmin() {
		when(authentication.getPrincipal()).thenReturn(createUserDetails(Authority.ROLE_USER));
		assertThat(securityAccessor.isCurrentUserAdmin()).isFalse();
	}

	@Test
	public void isNotCurrentUserAdminBecauseItsAnotherKindOfUser() {
		when(authentication.getPrincipal()).thenReturn("this is wrong");
		assertThat(securityAccessor.isCurrentUserAdmin()).isFalse();
	}

	@Test
	public void isNotCurrentUserAdminBecausePrincipalIsNull() {
		when(authentication.getPrincipal()).thenReturn(null);
		assertThat(securityAccessor.isCurrentUserAdmin()).isFalse();
	}

	private UserDetails createUserDetails(Authority authority) {
		return new UserDetails(createLoginResponse(), Lists.newArrayList(createGrantedAuthority(authority.toString())));
	}

	@SuppressWarnings("rawtypes")
	private Collection getAuthorities() {
		Collection<GrantedAuthority> authorities = Lists.newArrayList();
		authorities.add(createGrantedAuthority(Authority.ROLE_ADMIN.toString()));
		authorities.add(createGrantedAuthority(Authority.ROLE_USER.toString()));
		return authorities;
	}

	private GrantedAuthority createGrantedAuthority(String authorityName) {
		return new SimpleGrantedAuthority(authorityName);
	}

	private AuthenticationResponse createLoginResponse() {
		AuthenticationResponse response = new AuthenticationResponse();
		response.setUsername("response");
		response.setPassword("password");
		response.setAuthorization("Administrator");
		return response;
	}

}
