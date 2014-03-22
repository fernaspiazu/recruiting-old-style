package it.f2informatica.webapp.utils;

import it.f2informatica.webapp.WebAppContext;
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
		String languageParam = getCurrentHttpRequest().getParameter(WebAppContext.LANGUAGE);
		if (StringUtils.hasText(languageParam)) {
			return LocaleUtils.toLocale(languageParam);
		} else {
			Cookie cookie = getCookie(WebAppContext.CURRENT_LOCALE_COOKIE);
			return (cookie != null)
				? LocaleUtils.toLocale(cookie.getValue())
				: getCurrentHttpRequest().getLocale();
		}
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
