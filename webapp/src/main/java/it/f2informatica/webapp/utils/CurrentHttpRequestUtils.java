package it.f2informatica.webapp.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class CurrentHttpRequestUtils {

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
