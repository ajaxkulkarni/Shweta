package com.rns.shwetalab.mobile.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class CommonUtil {

	public static final String DATE_FORMAT = "dd-MM-yyyy";
	public static final String TYPE_DOCTOR = "Doctor";
	private static SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

	
	public static String convertDate(Date date) {
		return sdf.format(date);
	}

	public static Date convertDate(String date) {
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
		}
		return null;
	}

}
