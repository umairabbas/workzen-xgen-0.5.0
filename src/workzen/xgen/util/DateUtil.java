/*
 * DateUtil.java
 * Copyright (c) Brad D Matlack 2002 - 2003
 * License: http://www.gnu.org/gpl
 *
 * This program is free software.
 *
 * You may redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation.
 * Version 2 of the license should be included with this distribution in
 * the file LICENSE, as well as License.html. If the license is not
 * included with this distribution, you may find a copy at the FSF web
 * site at 'www.gnu.org' or 'www.fsf.org', or you may write to the
 * Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139 USA.
 *
 * THIS SOFTWARE IS PROVIDED AS-IS WITHOUT WARRANTY OF ANY KIND,
 * NOT EVEN THE IMPLIED WARRANTY OF MERCHANTABILITY. THE AUTHOR
 * OF THIS SOFTWARE, ASSUMES _NO_ bundlePONSIBILITY FOR ANY
 * CONSEQUENCE bundleULTING FROM THE USE, MODIFICATION, OR
 * REDISTRIBUTION OF THIS SOFTWARE.
 */
package workzen.xgen.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.text.SimpleDateFormat;

/**
 * This class converts date "strings" into Date objects.
 * It takes standard sql date - time - timestamp formats as input.
 * 
 * @author <a href="matlackb@users.sourceforge.net">Brad Matlack</a>
 */
public class DateUtil {

	/** 
	 * This Date class provides a string wrapper for all three sql types:
	 * java.sql.Date, java.sql.Time and java.sql.Timestamp
	 * 
	 * Three types of string formats are allowed:
	 * <li>year-month-day "2001-05-20"</li>
	 * <li>hour:min:sec "01:01:01"</li>
	 * <li>year-month-day hour:min:sec "2001-05-20 01:01:01"</li>
	 * <p>
	 * If "2001:05:20 01:01:01" is passed in, year, month and day = 0
	 * </p>
	 * Date will be set to current time if val = ""
	 * 
	 * @param String date formatted string
	 */
	public static java.util.Date getDate(java.lang.String val) {

		java.util.Date date = null;

		if ((val.equals("")) || (val.equals("0"))) {
			date = new java.sql.Date(new java.util.Date().getTime());
			return date;
		}

		GregorianCalendar cal = new GregorianCalendar();
		java.lang.String[] ts = split(val, " ");
		java.lang.String[] ymd, hms;
		int year = 0, month = 0, day = 0, hour = 0, min = 0, sec = 0;

		// parse "2001-05-20"
		if (ts[0].indexOf("-") > -1) {
			ymd = split(ts[0], "-");
			year = Integer.parseInt(ymd[0]);
			month = Integer.parseInt(ymd[1]);
			day = Integer.parseInt(ymd[2]);
		}

		// parse "01:01:01"
		else if (ts[0].indexOf(":") > -1) {
			hms = split(ts[0], ":");
			hour = Integer.parseInt(hms[0]);
			min = Integer.parseInt(hms[1]);
			sec = Integer.parseInt(hms[2]);
		}

		// parse "01:01:01" in second array element
		if (ts.length > 1) {
			hms = split(ts[1], ":");
			hour = Integer.parseInt(hms[0]);
			min = Integer.parseInt(hms[1]);
			sec = Integer.parseInt(hms[2]);
		}

		cal.set(year, month, day);
		cal.set(Calendar.HOUR, hour);
		cal.set(Calendar.MINUTE, min);
		cal.set(Calendar.SECOND, sec);

		// cannot get millis from calendar in JDK1.3 so first we get a java.util.Date
		date = cal.getTime();
		//date = new java.sql.Date( utilDate.getTime() );
		return date;
	}

	/**
	 * format = "yyyy-MM-dd HH:mm:ss";
	 */
	public static String formatTimestamp(Date date) {
		String format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	/**
	 * format = "yyyy-MM-dd";
	 */
	public static String formatDate(Date date) {
		String format = "yyyy-MM-dd";
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
	
	/**
	 * returns: "TO_DATE('2001-01-21 00:00:00','YYYY-MM-DD HH24:MI:SS')"
	 */
	public static String formatOracleTimestamp(Date date) {
		String format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		String str = "TO_DATE('" + dateFormat.format(date) + "','YYYY-MM-DD HH24:MI:SS')";
		return str;
	}

	/**
	 * Splits the provided CSV text into a list.
	 *
	 * @param text      The CSV list of values to split apart.
	 * @param pos       The piece of the array
	 * @return          The list of values.
	 */
	public static String[] split(String text, String separator) {
		if (text == null) {
			return new String[0];
		}
		if (text.indexOf(separator) < 0) {
			return new String[] { text };
		}
		StringTokenizer st = new StringTokenizer(text, separator);
		String[] values = new String[st.countTokens()];
		int pos = 0;
		while (st.hasMoreTokens()) {
			values[pos++] = st.nextToken();
		}
		return values;
	}

}
