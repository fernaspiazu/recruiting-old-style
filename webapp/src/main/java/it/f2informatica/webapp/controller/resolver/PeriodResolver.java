package it.f2informatica.webapp.controller.resolver;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.GregorianCalendar;

@Component
public class PeriodResolver {

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

}
