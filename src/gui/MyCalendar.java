package gui;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyCalendar {

	static String monthYear = "";

	// Given the month, day, and year, return which day of the week it falls on according to the Gregorian calendar.
	public static int day(int day, int month, int year) {
		int y = year - (14 - month) / 12;
		int x = y + y / 4 - y / 100 + y / 400;
		int m = month + 12 * ((14 - month) / 12) - 2;
		int d = (day + x + (31 * m) / 12) % 7;
		return d;
	}

	// return true if the given year is a leap year
	public static boolean isLeapYear(int year) {
		if ((year % 4 == 0) && (year % 100 != 0))
			return true;
		if (year % 400 == 0)
			return true;
		return false;
	}

	public static int getWeekNumber(int day, int month, int year) {
		String input = "";
		String format = "yyyyMMdd";
		String tMonth = month + "";
		String tDay = day + "";

		if (month < 10)
			tMonth = "0" + tMonth;
		if (day < 10)
			tDay = "0" + tDay;

		input = "" + year + "" + tMonth + "" + tDay;
		SimpleDateFormat df = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = df.parse(input);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week = cal.get(Calendar.WEEK_OF_YEAR);

		return week;
	}

	public static String[][] runClass(int month, int year) {
		String[][] strMonth = new String[6][7];

		// months[i] = name of month i
		String[] months = { "", "   January", "  February", "     March", "     April", "       May", "      June", "      July", "    August", " September", "   October",
				"  November", "  December" };

		// days[i] = number of days in month i
		int[] days = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

		// check for leap year
		if (month == 2 && isLeapYear(year))
			days[month] = 29;

		monthYear = months[month] + " " + year;
		// print calendar header
		System.out.println(" ---- " + months[month] + " " + year + " ----");
		// System.out.print(" ");
		// for (int i = 0; i < 7; i++)
		// System.out.printf(" %2s ", daysHeader[i]);
		// System.out.println();

		// starting day
		int d = day(1, month, year);
		// System.out.println(" Day: " + d);
		// final String space = " ";

		// print the calendar
		// System.out.print(" W" + getWeekNumber(1, month, year) + " ");

		// for (int i = 0; i < d - 1; i++)
		// System.out.print(space);

		for (int i = 1; i <= days[month]; i++) {
			// System.out.printf(" %2d ", i);
			if (((i + d - 1) % 7 == 0) && (i != days[month])) {
				// System.out.print("\n W" + getWeekNumber(i + 1, month, year) + " ");
			}
		}

		// System.out.println("\n\n --- testing ");
		int[][] luna = new int[6][7];

		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 7; j++)
				luna[i][j] = 0;

		int cDay = 1;
		int mDay = d - 1;
		if (mDay == -1)
			mDay = 6;
		int mWeek = 0;
		while (cDay != (days[month] + 1)) {
			luna[mWeek][mDay++] = cDay++;
			if (mDay == 7) {
				mDay = 0;
				mWeek++;
			}
		}

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				if (luna[i][j] != 0)
					strMonth[i][j] = "" + luna[i][j];
				else
					strMonth[i][j] = "";
				// System.out.printf(" %2d ", luna[i][j]);
			}
			// System.out.println();
		}

		return strMonth;
	}

	public static void main(String[] args) {
		runClass(9, 2019);
	}
}
