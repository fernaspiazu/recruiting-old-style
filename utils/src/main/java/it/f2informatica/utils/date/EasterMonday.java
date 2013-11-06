package it.f2informatica.utils.date;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

class EasterMonday {

	private EasterMonday() {
	}

	public static Date calculateEasterMonday(int yearNumber) {
		return new EasterMonday().internalCalculateEasterMonday(yearNumber);
	}

	private synchronized Date internalCalculateEasterMonday(int yearNumber) {
		int easterMonth;
		int easterDay;
		int march = 2;
		int april = 3;
		Date easterSunday = EasterSunday.calculateEasterSunday(yearNumber);
		Calendar cal = DateUtils.toCalendar(easterSunday);
		easterMonth = cal.get(Calendar.MONTH);
		easterDay = cal.get(Calendar.DAY_OF_MONTH);
		if (easterMonth == march && easterDay == 31) {
			return new GregorianCalendar(yearNumber, april, 1, 0, 0, 0).getTime();
		}
		return new GregorianCalendar(yearNumber, easterMonth, ++easterDay, 0, 0, 0).getTime();
	}

}
