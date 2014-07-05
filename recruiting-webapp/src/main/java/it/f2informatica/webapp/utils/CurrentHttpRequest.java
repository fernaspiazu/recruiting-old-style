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
package it.f2informatica.webapp.utils;

import it.f2informatica.webapp.WebApplicationConfig;
import org.apache.commons.lang3.LocaleUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Class which represents the current Http Request performed.
 */
@Component
public class CurrentHttpRequest {

	/**
	 * Returns the current locale associated to this request
	 * if any has been selected, otherwise it will return the
	 * default browser locale.
	 *
	 * @return the current locale
	 */
	public Locale getLocale() {
		String languageParam = getCurrentHttpRequest().getParameter(WebApplicationConfig.LANGUAGE);
		if (StringUtils.hasText(languageParam)) {
			return LocaleUtils.toLocale(languageParam);
		}
		Cookie cookie = getCookie(WebApplicationConfig.CURRENT_LOCALE_COOKIE);
		return (cookie != null)
			? LocaleUtils.toLocale(cookie.getValue())
			: getCurrentHttpRequest().getLocale();
	}

	/**
	 * Gets the cookie associated to this request given
	 * the cookie's name.
	 *
	 * @param cookieName name of cookie which wish be retrieved
	 * @return Cookie belonging this request
	 */
	public Cookie getCookie(String cookieName) {
		return WebUtils.getCookie(getCurrentHttpRequest(), cookieName);
	}

	/**
	 * Gets the current Http Request
	 */
	public HttpServletRequest getCurrentHttpRequest() {
		return getServletRequestAttributes().getRequest();
	}

	/**
	 * Gets servlet attributes
	 */
	public ServletRequestAttributes getServletRequestAttributes() {
		return (ServletRequestAttributes) currentRequestAttributes();
	}

	private RequestAttributes currentRequestAttributes() {
		return RequestContextHolder.currentRequestAttributes();
	}

}
