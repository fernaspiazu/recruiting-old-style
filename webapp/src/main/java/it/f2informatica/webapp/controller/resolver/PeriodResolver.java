package it.f2informatica.webapp.controller.resolver;

import it.f2informatica.webapp.context.WebAppContext;
import it.f2informatica.webapp.utils.CurrentHttpServletRequest;
import org.apache.commons.lang3.LocaleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

@Component
public class PeriodResolver {

	@Autowired
	private CurrentHttpServletRequest currentHttpServletRequest;

	public Date resolveDateByMonthAndYear(String month, String year) {
		try {
			return _resolveDateByMonthAndYear(month, year);
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
			return null;
		}
	}

	private Date _resolveDateByMonthAndYear(String month, String year) {
		int yearInt = Integer.parseInt(year);
		int monthInt = Integer.parseInt(month);
		return new GregorianCalendar(yearInt, monthInt, 1).getTime();
	}

	public String periodToString(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMMMM yyyy", getHttpRequestLocale());
		return (date != null) ? dateFormat.format(date) : "";
	}

	private Locale getHttpRequestLocale() {
		Cookie cookie = currentHttpServletRequest.getCookie(WebAppContext.CURRENT_LOCALE_COOKIE);
		return (cookie != null)
				? LocaleUtils.toLocale(cookie.getValue())
				: currentHttpServletRequest.getRequestLocale();
	}

}
