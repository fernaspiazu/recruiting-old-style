package it.f2informatica.utils.date;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.*;
import org.joda.time.base.BaseInterval;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class DateHelper extends DateUtils {
	private static final SimpleDateFormat YEAR_MONTH_FORMAT = new SimpleDateFormat("yyyy/MM");
	private static final SimpleDateFormat MONTH_YEAR_FORMAT = new SimpleDateFormat("MM/yyyy");
	private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat TIME_STAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private static final SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy");
	private static final SimpleDateFormat YEAR_MONTH_DAY_FORMAT_NO_SEPARATOR = new SimpleDateFormat("yyyyMMdd");

	public static Date createDate(int year, int month, int dayOfMonth) {
		Assert.isTrue(month >= 1 && month <= 12, "Invalid month: " + month);
		return new GregorianCalendar(year, month - 1, dayOfMonth).getTime();
	}

	public static Date addDays(Date date, int daysNo) {
		return add(Calendar.DATE, date, daysNo);
	}

	public static Date addMonths(Date date, int monthsNo) {
		return add(Calendar.MONTH, date, monthsNo);
	}

	private static Date add(int field, Date date, int amount) {
		Assert.notNull(date);
		if (amount == 0) {
			return date;
		}
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(field, amount);
		return cal.getTime();
	}

	public static boolean isBetweenIncludingLimits(Date date, Date dateFrom, Date dateTo) {
		return isBetween(date, dateFrom, dateTo)
				|| dateEquals(date, dateFrom)
				|| dateEquals(date, dateTo);
	}

	public static boolean isBetween(Date date, Date dateFrom, Date dateTo) {
		Assert.notNull(date);
		boolean before = dateTo == null || date.before(dateTo);
		boolean after = dateFrom == null || date.after(dateFrom);
		return before && after;
	}

	/**
	 * Helper method checks if the <code>firstDate</code> is after
	 * <code>secondDate</code> according only to days (not hh:mm:ss).
	 * When <code>firstDate</code> is '2010-01-10 12:05:00' and
	 * <code>secondDate</code> is '2010-01-10 12:00:00' this method
	 * will return <code>false</code>. The <code>java.util.Date.after()</code>
	 * method would return <code>true</code>.
	 *
	 * @throws IllegalArgumentException if <code>dateAfter</code>
	 *                              or <code>dateBefore</code> is null.
	 */
	public static boolean dateAfter(Date dateAfter, Date dateBefore) {
		Assert.notNull(dateAfter);
		Assert.notNull(dateBefore);
		return getNumberOfDaysSinceUnixEpochLocal(dateAfter) > getNumberOfDaysSinceUnixEpochLocal(dateBefore);
	}

	/**
	 * Helper method checks if the <code>firstDate</code> is before
	 * <code>secondDate</code> according only to days (not hh:mm:ss).
	 * When <code>firstDate</code> is '2010-01-10 12:00:00' and
	 * <code>secondDate</code> is '2010-01-10 12:05:00' this method
	 * will return <code>false</code>. The <code>java.util.Date.after()</code>
	 * method would return <code>true</code>.
	 *
	 * @throws IllegalArgumentException if <code>dateBefore</code>
	 *                              or <code>dateAfter</code> is null.
	 */
	public static boolean dateBefore(Date dateBefore, Date dateAfter) {
		Assert.notNull(dateAfter);
		Assert.notNull(dateBefore);
		return getNumberOfDaysSinceUnixEpochLocal(dateBefore) < getNumberOfDaysSinceUnixEpochLocal(dateAfter);
	}

	/**
	 * Helper method checks if the <code>firstDate</code> is the same as
	 * <code>secondDate</code> according only to days (not hh:mm:ss).
	 * When <code>firstDate</code> is '2010-01-10 12:05:00' and
	 * <code>secondDate</code> is '2010-01-11 12:00:00' this method will return
	 * <code>false</code>. The <code>java.util.Date.equals()</code>
	 * method would return <code>true</code>.
	 */
	public static boolean dateEquals(Date firstDate, Date secondDate) {
		return getNumberOfDaysSinceUnixEpochLocal(firstDate) == getNumberOfDaysSinceUnixEpochLocal(secondDate);
	}

	public static String dateToString(Date date) {
		Assert.notNull(date);
		return DEFAULT_DATE_FORMAT.format(date);
	}

	public static String dateTimeToString(Date date) {
		Assert.notNull(date);
		return TIME_STAMP_FORMAT.format(date);
	}

	public static String dateToYearMonth(Date date) {
		Assert.notNull(date);
		return YEAR_MONTH_FORMAT.format(date);
	}

	public static String dateToMonthYear(Date date) {
		Assert.notNull(date);
		return MONTH_YEAR_FORMAT.format(date);
	}

	public static String dateToYear(Date date) {
		Assert.notNull(date);
		return YEAR_FORMAT.format(date);
	}

	public static String dateToYearMonthDayFormatWithoutSeparators(Date date) {
		Assert.notNull(date);
		return YEAR_MONTH_DAY_FORMAT_NO_SEPARATOR.format(date);
	}

	public static long daysDifference(Date dateFrom, Date dateTo) {
		Assert.notNull(dateFrom);
		Assert.notNull(dateTo);
		return getNumberOfDaysSinceUnixEpochLocal(dateFrom) - getNumberOfDaysSinceUnixEpochLocal(dateTo);
	}

	public static Date getCurrentDate() {
		final Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static long getNumberOfDaysSinceUnixEpochLocal(Date date) {
		Assert.notNull(date);
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return (calendar.getTimeZone().getOffset(date.getTime()) + date.getTime()) / DateUtils.MILLIS_PER_DAY;
	}

	public static long getNumberOfDaysSinceUnixEpochUtc(Date date) {
		Assert.notNull(date);
		return date.getTime() / DateUtils.MILLIS_PER_DAY;
	}

	public static Date later(Date dateA, Date dateB) {
		return earliestDate(dateA, dateB);
	}

	public static Date earliestDate(Date dateA, Date dateB) {
		Assert.notNull(dateA);
		Assert.notNull(dateB);
		return dateA.getTime() > dateB.getTime() ? dateA : dateB;
	}

	public static Date nextDay(Date date) {
		return addDays(cloneThisDate(date), 1);
	}

	public static Date nextMonth(Date date) {
		return addMonths(cloneThisDate(date), 1);
	}

	public static Date previousDay(Date date) {
		return addDays(cloneThisDate(date), -1);
	}

	public static Date previousMonth(final Date date) {
		return addMonths(cloneThisDate(date), -1);
	}

	public static Date cloneThisDate(Date date) {
		Assert.notNull(date);
		return (Date) date.clone();
	}

	/**
	 * Returns a date represented by String specified as parameter
	 *
	 * @param stringDate Must be "2012-05-12" format.
	 */
	public static Date stringToDate(String stringDate) {
		try {
			return DEFAULT_DATE_FORMAT.parse(stringDate);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Error parsing date: " + stringDate, e);
		}
	}

	/**
	 * Returns a date represented by String specified as parameter
	 *
	 * @param yearMonthDay Must be "20120512" format.
	 */
	public static Date yearMonthDayNoSeparatorToDate(String yearMonthDay) {
		try {
			return YEAR_MONTH_DAY_FORMAT_NO_SEPARATOR.parse(yearMonthDay);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Error parsing date: " + yearMonthDay, e);
		}
	}

	/**
	 * Returns a date represented by String specified as parameter.
	 * Since the day of month is not specified in string, by default
	 * this date will have the first day of month.
	 *
	 * @param yearMonth Must be "2012/05" format.
	 * @return a date with representation 2012/05/01.
	 */
	public static Date yearMonthToDate(String yearMonth) {
		try {
			return YEAR_MONTH_FORMAT.parse(yearMonth);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Error parsing date: " + yearMonth, e);
		}
	}

	/**
	 * Returns a date represented by String specified as parameter.
	 * Since the day of month is not specified in string, by default
	 * this date will have the first day of month.
	 *
	 * @param monthYear Must be "05/2012" format.
	 * @return a date with representation 2012/05/01.
	 */
	public static Date monthYearToDate(String monthYear) {
		try {
			return MONTH_YEAR_FORMAT.parse(monthYear);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Error parsing date: " + monthYear, e);
		}
	}

	/**
	 * Returns the dates isBetween <code>startDate</code>
	 * and <code>endDate</code>.<br/> StartDate and EndDate
	 * are included -> [startDate ... endDate]
	 */
	public static List<Date> getDatesBetweenStartDateAndEndDate(Date startDate, Date endDate) {
		Assert.notNull(startDate);
		Assert.notNull(endDate);
		return internalElaborationDatesBetweenStartDateAndEndDate(startDate, endDate);
	}

	private static List<Date> internalElaborationDatesBetweenStartDateAndEndDate(Date startDate, Date endDate) {
		List<Date> dates = new ArrayList<>();
		BaseInterval baseInterval = new Interval(new DateTime(startDate), new DateTime(endDate));
		Days days = Days.daysIn(baseInterval);
		for (int i = 0; i <= days.getDays(); i++) {
			dates.add(baseInterval.getStart().withFieldAdded(DurationFieldType.days(), i).toDate());
		}
		return dates;
	}

	public static Integer numberOfDaysBetweenDates(Date startDate, Date endDate) {
		Assert.notNull(startDate);
		Assert.notNull(endDate);
		return Days.daysBetween(
				new DateTime(startDate),
				new DateTime(endDate))
		.getDays();
	}

	public static List<Date> getAllSaturdayAndSundayByYear(int year) {
		Assert.isTrue(year > 0, "Invalid year: " + year);
		List<Date> dates = new ArrayList<>();
		GregorianCalendar endOfYear = new GregorianCalendar(year, Calendar.DECEMBER, 31);
		GregorianCalendar gregorianCalendar = new GregorianCalendar(year, 0, 1, 0, 0, 0);
		while (isGregorianCalendarAfterEndOfYear(endOfYear, gregorianCalendar)) {
			populateSaturdayAndSundays(dates, gregorianCalendar);
		}
		return dates;
	}

	private static boolean isGregorianCalendarAfterEndOfYear(GregorianCalendar endOfYear, GregorianCalendar gregorianCalendar) {
		return !gregorianCalendar.after(endOfYear);
	}

	private static void populateSaturdayAndSundays(List<Date> dates, GregorianCalendar gregorianCalendar) {
		if (isSaturdayWeekDay(gregorianCalendar)) {
			dates.add(gregorianCalendar.getTime());
			gregorianCalendar.add(Calendar.DAY_OF_YEAR, 1);
		} else if (isSundayWeekDay(gregorianCalendar)) {
			dates.add(gregorianCalendar.getTime());
			gregorianCalendar.add(Calendar.DAY_OF_YEAR, 6);
		} else {
			gregorianCalendar.add(Calendar.DAY_OF_YEAR, 1);
		}
	}

	private static boolean isSaturdayWeekDay(GregorianCalendar gregorianCalendar) {
		return gregorianCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;
	}

	private static boolean isSundayWeekDay(GregorianCalendar gregorianCalendar) {
		return gregorianCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
	}

	/**
	 * Calculate Easter Sunday
	 * <p/>
	 * Written by Gregory N. Mirsky
	 * <p/>
	 * Source: 2nd Edition by Peter Duffett-Smith.
	 * It was originally from Butcher's Ecclesiastical Calendar, published in 1876.
	 * This algorithm has also been published in the 1922 book
	 * General Astronomy by Spencer Jones; in The Journal of the
	 * British Astronomical Association (Vol.88, page 91, December 1977);
	 * and in Astronomical Algorithms (1991) by Jean Meeus.
	 * <p/>
	 * This algorithm holds for any year in the Gregorian Calendar,
	 * which (of course) means years including and after 1583.
	 * <p/>
	 * a=year%19 b=year/100 c=year%100 d=b/4 e=b%4 f=(b+8)/25 g=(b-f+1)/3 h=(19*a+b-d-g+15)%30 i=c/4 k=c%4
	 * l=(32+2*e+2*i-h-k)%7 m=(a+11*h+22*l)/451 Easter Month =(h+l-7*m+114)/31 [3=March, 4=April]
	 * p=(h+l-7*m+114)%31 Easter Date=p+1 (date in Easter Month)
	 * <p/>
	 * Note:
	 * Integer truncation is already factored into the calculations.
	 * Using higher percision variables will cause inaccurate calculations.
	 */
	public static Date easterSunday(int yearNumber) {
		return EasterSunday.calculateEasterSunday(yearNumber);
	}

	public static Date easterMonday(int yearNumber) {
		return EasterMonday.calculateEasterMonday(yearNumber);
	}

	public static int getMonthsBetweenDates(Date dateFrom, Date dateTo) {
		if (dateFrom != null && dateTo != null) {
			Months months = Months.monthsBetween(new DateTime(dateFrom), new DateTime(dateTo));
			return months.getMonths();
		}
		return 0;
	}

}
