package com.rns.shwetalab.mobile;

import com.rns.shwetalab.mobile.db.CommonUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class HomePage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);

		Button btn_job = (Button) findViewById(R.id.btn_job);
		Button btn_admin = (Button) findViewById(R.id.btn_admin);
		Button btn_marketing = (Button) findViewById(R.id.btn_marketing);
		Button btn_dealer = (Button) findViewById(R.id.btn_dealer);
		Button btn_bill = (Button) findViewById(R.id.btn_bill);
		Button btn_settings = (Button) findViewById(R.id.btn_settings);

		/**
		 * Handling all button click events
		 */

		// Listening to News Feed button click
		btn_bill.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				SharedPreferences settings = getSharedPreferences(CommonUtil.LABNAME, 0);
				if (settings.getString("logged in", "").toString().equals("logged in")) 
				{
					Intent intent = new Intent(HomePage.this, AddBalanceExpense.class);
					startActivity(intent);
					finish();
				}
				else
					CommonUtil.showNotLogin(HomePage.this,"Not Logged in");
			}
		});

		btn_admin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				Log.d("ddfd", "dfd");
				Intent i = new Intent(getApplicationContext(), AdminLogin.class);
				startActivity(i);
			}
		});
		btn_marketing.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				SharedPreferences settings = getSharedPreferences(CommonUtil.LABNAME, 0);
				if (settings.getString("logged in", "").toString().equals("logged in")) 
				{
					Intent intent = new Intent(HomePage.this, AddViewMarketing.class);
					startActivity(intent);
					finish();
				}
				else
					CommonUtil.showNotLogin(HomePage.this,"Not Logged in");
				
			}
		});
		btn_dealer.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				SharedPreferences settings = getSharedPreferences(CommonUtil.LABNAME, 0);
				if (settings.getString("logged in", "").toString().equals("logged in")) 
				{
					Intent intent = new Intent(HomePage.this, AddViewDealer.class);
					startActivity(intent);
					finish();
				}
				else
					CommonUtil.showNotLogin(HomePage.this,"Not Logged in");
			}
		});

		btn_job.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent i = new Intent(HomePage.this, SelectJob.class);
				startActivity(i);
			}
		});

		btn_settings.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				SharedPreferences settings = getSharedPreferences(CommonUtil.LABNAME, 0);
				if (settings.getString("logged in", "").toString().equals("logged in")) 
				{
					Intent intent = new Intent(HomePage.this, ExportDatabase.class);
					startActivity(intent);
					finish();
				}
				else
					CommonUtil.showNotLogin(HomePage.this,"Not Logged in");
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_page, menu);
		return true;
	}

	@Override
	public void onBackPressed() {

		int i = 0;

		AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomePage.this);
		alertDialog.setTitle("Shweta Dental Lab");
		alertDialog.setMessage("Do you really want to exit?");
		alertDialog.setCancelable(false);
		alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// here you can add functions
				dialog.dismiss();
			}
		});

		alertDialog.create();
		alertDialog.show();

	}
	
	private void alreadylogin() {
		SharedPreferences settings = getSharedPreferences(CommonUtil.LABNAME, 0);
		if (settings.getString("logged in", "").toString().equals("logged in")) {
			Intent intent = new Intent(HomePage.this, AddPersonWork.class);
			startActivity(intent);
			finish();
		}
	}
	

}
