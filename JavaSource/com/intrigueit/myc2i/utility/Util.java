package com.intrigueit.myc2i.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
* Static convenience methods for common web and server related tasks.
*/
public class Util {

	private static String DEFAULT_DATE_FORMATE = "dd-MMM-yyyy";
	private static String DEFAULT_TIME_FORMATE = "hh:mm";


  /**
	 * Formate date by using valid date formate. If formate is empty then it will
	 * return by using default formate. default formate is dd-MMM-yyyy.
	 * @param date date to be formate.
	 * @param dFormate date formate for convert date on this formate.
	 * @return formated string
	 */
	public static String getDateByFormate(Date date, String dFormate) {
		if(dFormate == null || dFormate.equals("")) {
			dFormate = DEFAULT_DATE_FORMATE;
		}
		try {
			DateFormat dateFormat = new SimpleDateFormat(dFormate);
			if(date!=null)return dateFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static Date doFormate(Date date, String dFormate) {
		if(dFormate == null || dFormate.equals("")) {
			dFormate = DEFAULT_DATE_FORMATE;
		}
		try {
			DateFormat dateFormat = new SimpleDateFormat(dFormate);
			if(date!=null)return dateFormat.parse(dFormate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}