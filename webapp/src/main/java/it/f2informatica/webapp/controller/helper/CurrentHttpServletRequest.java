package it.f2informatica.webapp.controller.helper;

import it.f2informatica.webapp.context.WebAppContext;
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

@Component
public class CurrentHttpServletRequest {

	public Locale getRequestLocale() {
		String languageParam = currentHttpServletRequest().getParameter(WebAppContext.LANGUAGE);
		if (StringUtils.hasText(languageParam)) {
			return LocaleUtils.toLocale(languageParam);
		} else {
			Cookie cookie = getCookie(WebAppContext.CURRENT_LOCALE_COOKIE);
			return (cookie != null)
					? LocaleUtils.toLocale(cookie.getValue())
					: currentHttpServletRequest().getLocale();
		}
	}

	public Cookie getCookie(String cookieName) {
		return WebUtils.getCookie(currentHttpServletRequest(), cookieName);
	}

	public HttpServletRequest currentHttpServletRequest() {
		return currentServletRequestAttributes().getRequest();
	}

	public ServletRequestAttributes currentServletRequestAttributes() {
		return (ServletRequestAttributes) currentRequestAttributes();
	}

	public RequestAttributes currentRequestAttributes() {
		return RequestContextHolder.currentRequestAttributes();
	}

}
