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
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

public class BasicAuthorityService implements AuthorityService {
	private static final String PREFIX_ROLE = "ROLE_";

	@Override
	public Collection<GrantedAuthority> createAuthorities(String authorization) {
		Collection<GrantedAuthority> authorities = Lists.newArrayList();
		for (Authority roleType : Authority.values()) {
			final String roleAuthority = roleType.toString();
			if (matchRoles(authorization, roleAuthority)) {
				authorities.add(createAuthority(roleAuthority));
			}
		}
		return authorities;
	}

	private boolean matchRoles(String authorization, String roleAuthority) {
		final String authorizationName = StringUtils.removeStart(roleAuthority, PREFIX_ROLE);
		return StringUtils.containsIgnoreCase(authorization, authorizationName);
	}

	private GrantedAuthority createAuthority(String roleAuthority) {
		return new SimpleGrantedAuthority(roleAuthority);
	}

}
