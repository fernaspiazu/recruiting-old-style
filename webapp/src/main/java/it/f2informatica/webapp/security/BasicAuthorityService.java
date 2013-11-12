package it.f2informatica.webapp.security;

import com.google.common.collect.Lists;
import it.f2informatica.mongodb.domain.constants.Authority;
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
