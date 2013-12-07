package it.f2informatica.webapp.controller.helper;

import com.google.common.collect.Lists;
import it.f2informatica.webapp.context.WebAppContext;
import org.apache.commons.lang3.LocaleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public class MonthHelper {

	@Autowired
	private MessageSource messageSource;

	public List<Month> getMonths(HttpServletRequest request) {
		return Lists.newArrayList(
			new Month("0", getMessage("month.january", request)),
			new Month("1", getMessage("month.february", request)),
			new Month("2", getMessage("month.march", request)),
			new Month("3", getMessage("month.april", request)),
			new Month("4", getMessage("month.may", request)),
			new Month("5", getMessage("month.june", request)),
			new Month("6", getMessage("month.july", request)),
			new Month("7", getMessage("month.august", request)),
			new Month("8", getMessage("month.september", request)),
			new Month("9", getMessage("month.october", request)),
			new Month("10", getMessage("month.november", request)),
			new Month("11", getMessage("month.december", request))
		);
	}

	private String getMessage(String label, HttpServletRequest request) {
		Cookie currentLocaleCookie = WebUtils.getCookie(request, WebAppContext.CURRENT_LOCALE_COOKIE);
		return (currentLocaleCookie != null)
			? messageSource.getMessage(label, null, LocaleUtils.toLocale(currentLocaleCookie.getValue()))
			: messageSource.getMessage(label, null, request.getLocale());
	}

}
