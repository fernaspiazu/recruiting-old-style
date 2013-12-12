package it.f2informatica.webapp.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Component
public class CurrentHttpRequestUtils {

	public Cookie getCookie(String cookieName) {
		return WebUtils.getCookie(currentHttpServletRequest(), cookieName);
	}

	public Locale getRequestLocale() {
		return currentHttpServletRequest().getLocale();
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
