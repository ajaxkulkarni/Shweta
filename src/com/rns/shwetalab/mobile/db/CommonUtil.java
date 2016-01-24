package com.rns.shwetalab.mobile.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class CommonUtil {

	public static final String DATE_FORMAT = "dd-MM-yyyy";
	public static final String TYPE_DOCTOR = "Doctor";
	public static final String TYPE_LAB = "Lab";
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

	public static void showMessage(final Context context) {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle("Shweta Dental Lab");
		alertDialogBuilder.setMessage("Record Inserted Successfully !!").setCancelable(false).setPositiveButton("Done", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				((Activity) context).finish();
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
	
	public static void showError(final Context context, String message) {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle("Shweta Dental Lab");
		alertDialogBuilder.setMessage(message).setCancelable(false).setPositiveButton("Done", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				((Activity) context).finish();
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	public static void showUpdateMessage(final Context context) {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle("Shweta Dental Lab");
		alertDialogBuilder.setMessage("Record Updated Successfully !!").setCancelable(false).setPositiveButton("Done", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				((Activity) context).finish();
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
}
