package com.intrigueit.myc2i.common.utility;

import java.util.Locale;

public class CalendarManageBean {
	private Locale locale;
	private String pattern;
	private boolean popup;
	private boolean readonly;

	public CalendarManageBean() {
		locale = Locale.US;
		popup = true;
		//pattern = "MMM d, yyyy";
		pattern = "MM-dd-yyyy";
		readonly = true;
	}

	public Locale getLocale() {
		return locale;
	}
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	public boolean isPopup() {
		return popup;
	}
	public void setPopup(boolean popup) {
		this.popup = popup;
	}
	public boolean isReadonly() {
		return readonly;
	}
	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}


}
