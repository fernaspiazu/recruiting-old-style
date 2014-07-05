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

import org.apache.commons.lang3.ArrayUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

@Component
public class PeriodParser {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private CurrentHttpRequest currentHttpRequest;

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
		return new GregorianCalendar(yearInt, monthInt, 1, 0, 0, 0).getTime();
	}

	public String formatDateByMonthNameAndYear(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMMMM yyyy", currentHttpRequest.getLocale());
		return (date != null) ? dateFormat.format(date) : "";
	}

	public String printTotalTimeOfPeriodWhichHasElapsed(Date from, Date to) {
		DateTime timeFrom = new DateTime(from);
		DateTime timeTo = (to != null) ? new DateTime(to) : new DateTime();
		Period period = new Period(timeFrom, timeTo);
		return "(" + appendYears(period) + appendMonths(period) + ")";
	}

	private String appendYears(Period period) {
		int years = period.getYears();
		if (years <= 0) {
			return "";
		}
		return String.valueOf(years) + " " + ((years == 1) ? getMessage("global.year") : getMessage("global.years")) + " ";
	}

	private String appendMonths(Period period) {
		int months = period.getMonths();
		if (months <= 0) {
			return appendDays(period);
		}
		return String.valueOf(months) + " " + ((months == 1) ? getMessage("global.month") : getMessage("global.months"));
	}

	private String appendDays(Period period) {
		int days = period.getDays();
		if (days <= 0) {
			return " " + String.valueOf(days) + " " + getMessage("global.days");
		}
		return " " + String.valueOf(days) + " " + ((days == 1) ? getMessage("global.day") : getMessage("global.days"));
	}

	private String getMessage(String code) {
		return messageSource.getMessage(code, ArrayUtils.EMPTY_OBJECT_ARRAY, currentHttpRequest.getLocale());
	}

}
