package it.f2informatica.webapp.controller.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

@Component
public class PeriodResolver {

	@Autowired
	private CurrentHttpServletRequest currentHttpRequest;

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
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMMMM yyyy", currentHttpRequest.getRequestLocale());
		return (date != null) ? dateFormat.format(date) : "";
	}

}
