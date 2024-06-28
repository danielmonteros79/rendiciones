package com.sa.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import ar.org.bbva.util.DateUtils;

public class DateUtil extends DateUtils {
	
	private DateFormat dfYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
	private DateFormat dfDDMMYYYYGuion = new SimpleDateFormat("dd-MM-yyyy");
	private DateFormat dfDDMMYYYY = new SimpleDateFormat("dd/MM/yyyy");


	public static int daysBetween(Date d1, Date d2) {
		return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}


	public DateFormat getDfDDMMYYYYGuion() {
		return dfDDMMYYYYGuion;
	}


	public void setDfDDMMYYYYGuion(DateFormat dfDDMMYYYYGuion) {
		this.dfDDMMYYYYGuion = dfDDMMYYYYGuion;
	}


	public DateFormat getDfYYYYMMDD() {
		return dfYYYYMMDD;
	}


	public void setDfYYYYMMDD(DateFormat dfYYYYMMDD) {
		this.dfYYYYMMDD = dfYYYYMMDD;
	}


	public DateFormat getDfDDMMYYYY() {
		return dfDDMMYYYY;
	}


	public void setDfDDMMYYYY(DateFormat dfDDMMYYYY) {
		this.dfDDMMYYYY = dfDDMMYYYY;
	}
}