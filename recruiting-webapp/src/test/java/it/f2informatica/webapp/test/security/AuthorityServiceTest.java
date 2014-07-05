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

import it.f2informatica.core.Authority;
import it.f2informatica.webapp.security.AuthorityService;
import it.f2informatica.webapp.security.BasicAuthorityService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.fest.assertions.Assertions.assertThat;

public class AuthorityServiceTest {
	private static final String ROLE_2_NAME = "User";
	private static final String ROLE_1_NAME = "Administrator";
	private static final String ROLE_ADMIN = "ROLE_ADMIN";
	private static final String ROLE_USER = "ROLE_USER";

	private static AuthorityService authorityService;

	@BeforeClass
	public static void setUp() {
		authorityService = new BasicAuthorityService();
	}

	@Test
	public void adminRoleTypes() {
		assertThat(Authority.ROLE_ADMIN.toString()).isEqualTo(ROLE_ADMIN);
	}

	@Test
	public void userRoleTypes() {
		assertThat(Authority.ROLE_USER.toString()).isEqualTo(ROLE_USER);
	}

	@Test
	public void createAuthorities_ADMIN() {
		assertThat(authorityService.createAuthorities(ROLE_1_NAME)).contains(roleAdminGrantedAuthority());
	}

	@Test
	public void createAuthorities_USER() {
		assertThat(authorityService.createAuthorities(ROLE_2_NAME)).contains(roleUserGrantedAuthority());
	}

	private SimpleGrantedAuthority roleAdminGrantedAuthority() {
		return createGrantedAuthority(Authority.ROLE_ADMIN.toString());
	}

	private SimpleGrantedAuthority roleUserGrantedAuthority() {
		return createGrantedAuthority(Authority.ROLE_USER.toString());
	}

	private SimpleGrantedAuthority createGrantedAuthority(String authorityName) {
		return new SimpleGrantedAuthority(authorityName);
	}

}
