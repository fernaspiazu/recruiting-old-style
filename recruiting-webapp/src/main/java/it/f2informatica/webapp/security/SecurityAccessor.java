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

import com.google.common.collect.Lists;
import it.f2informatica.core.Authority;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public class SecurityAccessor {

	public String getCurrentUsername() {
		return getAuthentication().getName();
	}

	public boolean isCurrentUserAdmin() {
		return isCurrentHasRole(Authority.ROLE_ADMIN);
	}

	private boolean isCurrentHasRole(Authority type) {
		return isPrincipalForAuthenticatedUser() && isRolePresent(type);
	}

	private boolean isRolePresent(Authority type) {
		boolean isRolePresent = false;
		for (GrantedAuthority auth : getAuthorities()) {
			isRolePresent = auth.getAuthority().equals(type.toString());
			if (isRolePresent) break;
		}
		return isRolePresent;
	}

	private Collection<GrantedAuthority> getAuthorities() {
		return Lists.newArrayList(((UserDetails) getPrincipal()).getAuthorities());
	}

	private Authentication getAuthentication() {
		return getSecurityContext().getAuthentication();
	}

	private SecurityContext getSecurityContext() {
		return SecurityContextHolder.getContext();
	}

	private boolean isPrincipalForAuthenticatedUser() {
		return isPrincipalInstanceOf(UserDetails.class);
	}

	private boolean isPrincipalInstanceOf(Class<?> type) {
		Object principal = getPrincipal();
		return principal != null && principal.getClass().isAssignableFrom(type);
	}

	private Object getPrincipal() {
		return getAuthentication().getPrincipal();
	}

}
