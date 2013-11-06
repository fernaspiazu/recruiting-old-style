package it.f2informatica.test.utils.date;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static it.f2informatica.utils.date.DateHelper.*;
import static org.fest.assertions.Assertions.assertThat;

public class DateHelperTest {
	private static Date testDate = createDate(1987, 4, 17);

	@Test
	public void dateCreation() {
		assertThat(testDate).isNotNull();
		assertThat(testDate).isEqualTo(createDate(1987, 4, 17));
	}

	@Test(expected = IllegalArgumentException.class)
	public void dateCreationWithWrongMonthLessThanOne() {
		createDate(2013, 0, 5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void dateCreationWithWrongMonthGreaterThanOne() {
		createDate(2013, 13, 5);
	}

	@Test
	public void addDaysTest() {
		assertThat(dateEquals(addDays(testDate, 3), createDate(1987, 4, 20))).isTrue();
		assertThat(dateEquals(addDays(testDate, 0), createDate(1987, 4, 17))).isTrue();
	}

	@Test
	public void addMonthsTest() {
		assertThat(createDate(1987, 12, 17)).isEqualTo(addMonths(testDate, 8));
		assertThat(createDate(1987, 4, 17)).isEqualTo(addMonths(testDate, 0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void addMethodInNullCase() {
		addDays(null, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void betweenWithNullInAllItsParameters() {
		isBetween(null, null, null);
	}

	@Test
	public void betweenWithDateFromNull() {
		assertThat(isBetween(testDate, null, createDate(1997, 6, 3))).isTrue();
	}

	@Test
	public void betweenWithDateToNull() {
		assertThat(isBetween(testDate, createDate(1984, 5, 27), null)).isTrue();
	}

	@Test
	public void betweenWithBothDateFromAndDateToNull() {
		assertThat(isBetween(testDate, null, null)).isTrue();
	}

	@Test
	public void betweenTest() {
		assertThat(isBetween(testDate, createDate(1984, 5, 27), createDate(1997, 6, 3))).isTrue();
		assertThat(isBetween(testDate, createDate(1984, 5, 27), createDate(1985, 6, 10))).isFalse();
	}

	@Test
	public void between() {
		assertThat(isBetween(testDate, createDate(1987, 4, 17), createDate(1997, 6, 3))).isFalse();
	}

	@Test
	public void betweenIncludingLimits() {
		assertThat(isBetweenIncludingLimits(testDate, createDate(1987, 1, 10), createDate(1997, 6, 3))).isTrue();
		assertThat(isBetweenIncludingLimits(testDate, createDate(1987, 4, 17), createDate(1997, 6, 3))).isTrue();
		assertThat(isBetweenIncludingLimits(testDate, createDate(1987, 1, 10), createDate(1987, 4, 17))).isTrue();
	}

	@Test
	public void betweenIncludingLimitsReturningFalse() {
		assertThat(isBetweenIncludingLimits((testDate), createDate(1987, 4, 18), createDate(1997, 6, 3))).isFalse();
		assertThat(isBetweenIncludingLimits((testDate), createDate(1982, 5, 22), createDate(1987, 4, 16))).isFalse();
	}

	@Test(expected = IllegalArgumentException.class)
	public void dateAfterWithNullAsFirstParameter() {
		dateAfter(null, createDate(2012, 12, 6));
	}

	@Test(expected = IllegalArgumentException.class)
	public void dateAfterWithNullAsLastParameter() {
		dateAfter(createDate(2012, 12, 6), null);
	}

	@Test
	public void dateAfterIsTrue() {
		assertThat(dateAfter(createDate(2012, 12, 6), createDate(2012, 12, 5))).isTrue();
	}

	@Test
	public void dateAfterIsFalse() {
		assertThat(dateAfter(createDate(2012, 12, 5), createDate(2012, 12, 6))).isFalse();
	}

	@Test
	public void dateAfterWithEqualDates() {
		assertThat(dateAfter(createDate(2012, 12, 5), createDate(2012, 12, 5))).isFalse();
	}

	@Test(expected = IllegalArgumentException.class)
	public void dateBeforeWithNullAsFirstParameter() {
		dateBefore(null, createDate(2012, 12, 6));
	}

	@Test(expected = IllegalArgumentException.class)
	public void dateBeforeWithNullAsLastParameter() {
		dateBefore(createDate(2012, 12, 6), null);
	}

	@Test
	public void dateBeforeIsTrue() {
		assertThat(dateBefore(createDate(2012, 12, 5), createDate(2012, 12, 6))).isTrue();
	}

	@Test
	public void dateBeforeIsFalse() {
		assertThat(dateBefore(createDate(2012, 12, 6), createDate(2012, 12, 5))).isFalse();
	}

	@Test
	public void dateBeforeWithEqualDates() {
		assertThat(dateBefore(createDate(2012, 12, 6), createDate(2012, 12, 6))).isFalse();
	}

	@Test
	public void dateEqualsTest() {
		assertThat(dateEquals(createDate(2012, 12, 5), createDate(2012, 12, 5))).isTrue();
		assertThat(dateEquals(createDate(2012, 12, 5), createDate(2012, 12, 5))).isTrue();
	}

	@Test(expected = IllegalArgumentException.class)
	public void dateToStringWithNullParameter() {
		dateToString(null);
	}

	@Test
	public void dateToStringTest() {
		assertThat(dateToString(createDate(2012, 12, 5))).isEqualTo("2012-12-05");
	}

	@Test(expected = IllegalArgumentException.class)
	public void dateTimeToStringWithNullParam() {
		dateTimeToString(null);
	}

	@Test
	public void dateTimeToStringTest() {
		assertThat(dateTimeToString(new Date(1354786246910L)))
				.isEqualTo(dateTimeToString(new Date(1354786246910L)));
	}

	@Test
	public void dateToYearMonthFormat() {
		try {
			assertThat(dateToYearMonth(createDate(2012, 12, 6))).isEqualTo("2012/12");
			dateToYearMonth(null);
		} catch (Throwable e) {
			assertThat(e).isExactlyInstanceOf(IllegalArgumentException.class);
		}
	}

	@Test
	public void dateToMonthYearTest() {
		assertThat(dateToMonthYear(createDate(2012, 12, 6))).isEqualTo("12/2012");
	}

	@Test(expected = IllegalArgumentException.class)
	public void dateToMonthYearThrowingIllegalArgumentException() {
		dateToMonthYear(null);
	}

	@Test
	public void dateToYearTest() {
		assertThat(dateToYear(createDate(2012, 12, 6))).isEqualTo("2012");
	}

	@Test
	public void dateToYearMonthDayFormatWithoutSeparatorsTest() {
		assertThat(dateToYearMonthDayFormatWithoutSeparators(createDate(2012, 12, 6))).isEqualTo("20121206");
	}

	@Test
	public void daysDifferenceTest() {
		assertThat(daysDifference(createDate(2012, 12, 10), createDate(2012, 12, 5))).isEqualTo(5);
	}

	@Test
	public void earliestDateWithFirstParameterMinorThanTheSecond() {
		Date earliestDate = earliestDate(createDate(2012, 11, 28), createDate(2012, 12, 2));
		assertThat(earliestDate).isEqualTo(createDate(2012, 12, 2));
	}

	@Test
	public void earliestDateWithFirstParameterGreaterThanTheSecond() {
		Date earliestDate = earliestDate(createDate(2012, 12, 2), createDate(2012, 11, 28));
		assertThat(earliestDate).isEqualTo(createDate(2012, 12, 2));
	}

	@Test
	public void earliestDateWithEqualDates() {
		Date earliestDateWithEqualDates = earliestDate(createDate(2012, 11, 28), createDate(2012, 11, 28));
		assertThat(earliestDateWithEqualDates).isEqualTo(createDate(2012, 11, 28));
	}

	@Test
	public void currentDate() {
		assertThat(dateEquals(getCurrentDate(), new Date())).isTrue();
		assertThat(dateEquals(
				truncate(getCurrentDate(), Calendar.DAY_OF_MONTH),
				truncate(new Date(), Calendar.DAY_OF_MONTH))
		).isTrue();
	}

	@Test
	public void getNumberOfDaysSinceUnixEpochLocalTest() {
		assertThat(getNumberOfDaysSinceUnixEpochLocal(createDate(2012, 11, 28))).isEqualTo(15672);
	}

	@Test
	public void getNumberOfDaysSinceUnixEpochUtcTest() {
		assertThat(getNumberOfDaysSinceUnixEpochUtc(createDate(2012, 11, 28))).isEqualTo(15671);
	}

	@Test
	public void laterTest() {
		assertThat(later(createDate(2012, 11, 28), createDate(2012, 12, 2))).isEqualTo(createDate(2012, 12, 2));
	}

	@Test
	public void nextDayTest() {
		assertThat(dateToString(nextDay(createDate(2012, 10, 2)))).isEqualTo("2012-10-03");
		assertThat(dateToString(nextDay(createDate(2012, 11, 30)))).isEqualTo("2012-12-01");
		assertThat(dateToString(nextDay(createDate(2012, 2, 28)))).isEqualTo("2012-02-29");
		assertThat(dateToString(nextDay(createDate(2013, 2, 28)))).isEqualTo("2013-03-01");
		assertThat(dateToString(nextDay(createDate(2012, 12, 31)))).isEqualTo("2013-01-01");
	}

	@Test
	public void nextMonthTest() {
		assertThat(dateToString(nextMonth(createDate(2012, 10, 2)))).isEqualTo("2012-11-02");
		assertThat(dateToString(nextMonth(createDate(2012, 12, 2)))).isEqualTo("2013-01-02");
	}

	@Test
	public void previousDayTest() {
		assertThat(dateToString(previousDay(createDate(2012, 10, 2)))).isEqualTo("2012-10-01");
		assertThat(dateToString(previousDay(createDate(2012, 12, 1)))).isEqualTo("2012-11-30");
		assertThat(dateToString(previousDay(createDate(2012, 3, 1)))).isEqualTo("2012-02-29");
		assertThat(dateToString(previousDay(createDate(2013, 3, 1)))).isEqualTo("2013-02-28");
		assertThat(dateToString(previousDay(createDate(2013, 1, 1)))).isEqualTo("2012-12-31");
	}

	@Test
	public void previousMonthTest() {
		assertThat(dateToString(previousMonth(createDate(2012, 10, 2)))).isEqualTo("2012-09-02");
		assertThat(dateToString(previousMonth(createDate(2013, 1, 2)))).isEqualTo("2012-12-02");
	}

	@Test
	public void stringToDateTest() {
		assertThat(createDate(2012, 12, 7)).isEqualTo(stringToDate("2012-12-07"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void stringToDateThrowingIllegalArgumentException() {
		stringToDate("Malformed String");
	}

	@Test
	public void yearMonthDayNoSeparatorToDateTest() {
		assertThat(createDate(2012, 12, 7)).isEqualTo(yearMonthDayNoSeparatorToDate("20121207"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void yearMonthDayNoSeparatorToDateThrowingException() {
		yearMonthDayNoSeparatorToDate("Malformed String");
	}

	@Test
	public void yearMonthToDateTest() {
		assertThat(yearMonthToDate("2012/12")).isEqualTo(createDate(2012, 12, 1));
		assertThat(yearMonthToDate("2012/12")).isNotEqualTo(createDate(2012, 12, 15));
	}

	@Test(expected = IllegalArgumentException.class)
	public void yearMonthToDateThrowingException() {
		yearMonthToDate("Malformed String");
	}

	@Test
	public void monthYearToDateTest() {
		assertThat(monthYearToDate("12/2012")).isEqualTo(createDate(2012, 12, 1));
		assertThat(monthYearToDate("12/2012")).isNotEqualTo(createDate(2012, 12, 15));
	}

	@Test(expected = IllegalArgumentException.class)
	public void monthYearToDateThrowingIllegalArgumentException() {
		monthYearToDate("Malformed String");
	}

	@Test
	public void getDatesBetweenStartDateAndEndDateTest() {
		List<Date> dateRange = getDatesBetweenStartDateAndEndDate(createDate(2012, 12, 10), createDate(2012, 12, 20));
		assertThat(dateRange).hasSize(11).contains(createDate(2012, 12, 15));
	}

	@Test
	public void numberOfDaysBetweenDateTest() {
		assertThat(numberOfDaysBetweenDates(createDate(2012, 12, 10), createDate(2012, 12, 20))).isEqualTo(10);
	}

	@Test
	public void getAllSaturdayAndSundaysOfYear_2012() {
		List<Date> allSaturdayAndSundaysOf2012 = getAllSaturdayAndSundayByYear(2012);
		assertThat(allSaturdayAndSundaysOf2012).hasSize(105).contains(createDate(2012, 12, 15));
	}

	@Test
	public void getAllSaturdayAndSundaysOfYear_2013() {
		List<Date> allSaturdayAndSundaysOf2013 = getAllSaturdayAndSundayByYear(2013);
		assertThat(allSaturdayAndSundaysOf2013).hasSize(104).contains(createDate(2013, 4, 20));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAllSaturdayAndSundaysByYearThrowingException() {
		getAllSaturdayAndSundayByYear(0);
	}

	@Test
	public void easterSunday_2012() {
		assertThat(dateToString(easterSunday(2012))).isEqualTo("2012-04-08");
	}

	@Test
	public void easterSunday_2013() {
		assertThat(dateToString(easterSunday(2013))).isEqualTo("2013-03-31");
	}

	@Test
	public void easterSunday_2014() {
		assertThat(dateToString(easterSunday(2014))).isEqualTo("2014-04-20");
	}

	@Test
	public void easterMonday_2012() {
		assertThat(dateToString(easterMonday(2012))).isEqualTo("2012-04-09");
	}

	@Test
	public void easterMonday_2016() {
		assertThat(dateToString(easterMonday(2016))).isEqualTo("2016-03-28");
	}

	@Test
	public void easterMonday_2013() {
		assertThat(dateToString(easterMonday(2013))).isEqualTo("2013-04-01");
	}

	@Test
	public void getMonthsBetweenDatesCorrectly() {
		assertThat(getMonthsBetweenDates(createDate(2012, 4, 10), createDate(2012, 12, 20))).isEqualTo(8);
	}

	@Test
	public void getMonthsBetweenDatesWithFirstDateGreaterThanSecondDate() {
		assertThat(getMonthsBetweenDates(createDate(2012, 12, 20), createDate(2012, 4, 10))).isEqualTo(-8);
	}

	@Test
	public void getMonthsBetweenDatesWithFirstDateNull() {
		assertThat(getMonthsBetweenDates(null, createDate(2012, 12, 20))).isEqualTo(0);
	}

	@Test
	public void getMonthsBetweenDatesWithSecondDateNull() {
		assertThat(getMonthsBetweenDates(createDate(2012, 4, 10), null)).isEqualTo(0);
	}

}
