/*
 * =============================================================================
 *
 *   Copyright (c) 2014, Fernando Aspiazu
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */
package it.f2informatica.webapp.test.security;

import com.google.common.collect.Lists;
import it.f2informatica.core.Authority;
import it.f2informatica.core.model.AuthenticationModel;
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

  private AuthenticationModel createLoginResponse() {
    AuthenticationModel response = new AuthenticationModel();
    response.setUsername("response");
    response.setPassword("password");
    response.setAuthorization("Administrator");
    return response;
  }

}
