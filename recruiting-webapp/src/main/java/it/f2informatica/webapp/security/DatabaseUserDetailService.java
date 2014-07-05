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
package it.f2informatica.webapp.security;

import com.google.common.base.Optional;
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
		Optional<AuthenticationModel> user = authenticationService.processLogin(username);
		if (user.isPresent()) {
			AuthenticationModel foundUser = user.get();
			logger.info("Authenticating with username: '" + foundUser.getUsername()
				+ "', with role: '" + foundUser.getAuthorization() + "'");
			return createUserDetails(foundUser);
		}

		UsernameNotFoundException userNotFoundException = new UsernameNotFoundException(
			"Bad Credentials!!! User has not been found. Wrong Username[" + username + "]");
		logger.info(userNotFoundException.getMessage());

		throw userNotFoundException;
	}

	private UserDetails createUserDetails(AuthenticationModel userLogged) {
		return new UserDetails(userLogged, authorityService.createAuthorities(userLogged.getAuthorization()));
	}

}
