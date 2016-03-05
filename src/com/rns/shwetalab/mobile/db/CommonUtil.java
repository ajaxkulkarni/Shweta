package com.rns.shwetalab.mobile.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.rns.shwetalab.mobile.AddPersonWork;
import com.rns.shwetalab.mobile.AdminLogin;
import com.rns.shwetalab.mobile.ExportDatabase;
import com.rns.shwetalab.mobile.SplashScreen;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

public class CommonUtil {

	public static final String DATE_FORMAT = "dd-MM-yyyy";
	public static final String TYPE_DOCTOR = "Doctor";
	public static final String TYPE_LAB = "Lab";
	public static final String TYPE_DEALER = "Dealer";
	public static final String USERNAME = "admin";
	public static final String PASSWORD = "admin";
	public static final String LABNAME = "shwetalab";
	public static Integer FLAG = 0;
	private static SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	static AlertDialog alertDialog;

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
	
	public static void showLoginErrorMessage(final Context context) {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle("Shweta Dental Lab");
		alertDialogBuilder.setMessage("Enter valid credentials!!").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				alertDialog.dismiss();
			}
		});
		alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
	
//	public static void showLogoutMessage(final Context context) {
//
//		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
//		alertDialogBuilder.setTitle("Shweta Dental Lab");
//		alertDialogBuilder.setMessage("Logout Successfully !!").setCancelable(false).setPositiveButton("Done", new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int id) {
//				((Activity) context).finish();
//			}
//		});
//		AlertDialog alertDialog = alertDialogBuilder.create();
//		alertDialog.show();
//	}
	
	public static void login(Context context) 
	{
		SharedPreferences settings = context.getSharedPreferences(CommonUtil.LABNAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("logged in", "logged in");
		CommonUtil.FLAG = 1;
		editor.commit();
	}
	
	public static void logout(Context context) {
		SharedPreferences settings = context.getSharedPreferences(CommonUtil.LABNAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.remove("logged in");
		FLAG = 0;
		editor.commit();
		
	}
}
