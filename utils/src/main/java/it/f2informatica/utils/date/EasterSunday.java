package it.f2informatica.utils.date;

import java.util.Date;
import java.util.GregorianCalendar;

class EasterSunday {

	int nP = 0;
	private int easterMonth = 0;
	private int easterDay = 0;

	private EasterSunday() {
	}

	public static Date calculateEasterSunday(int yearNumber) {
		return new EasterSunday().internalCalculateEasterSunday(yearNumber);
	}

	private Date internalCalculateEasterSunday(int yearNumber) {
		calcEasterSunday(yearNumber);
		return new GregorianCalendar(yearNumber, easterMonth, easterDay, 0, 0, 0).getTime();
	}

	private void calcEasterSunday(int yearNumber) {
		int nA = yearNumber % 19;
		int nB = yearNumber / 100;
		int nC = yearNumber % 100;
		int nD = nB / 4;
		int nE = nB % 4;
		int nF = (nB + 8) / 25;
		int nG = (nB - nF + 1) / 3;
		int nH = (19 * nA + nB - nD - nG + 15) % 30;
		int nI = nC / 4;
		int nK = nC % 4;
		int nL = (32 + 2 * nE + 2 * nI - nH - nK) % 7;
		int nM = (nA + 11 * nH + 22 * nL) / 451;

		// [3=March, 4=April]
		easterMonth = (nH + nL - 7 * nM + 114) / 31;
		--easterMonth;
		nP = (nH + nL - 7 * nM + 114) % 31;
		easterDay = dateInEasterMonth();
	}

	private int dateInEasterMonth() {
		return nP + 1;
	}

}
