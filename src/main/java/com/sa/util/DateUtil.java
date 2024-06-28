package com.sa.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import ar.org.bbva.util.DateUtils;

public class DateUtil extends DateUtils {
	public static final DateFormat dfYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
	public static final DateFormat dfDDMMYYYYGuion = new SimpleDateFormat("dd-MM-yyyy");

	public static int daysBetween(Date d1, Date d2) {
		return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}
}
