package com.rns.shwetalab.mobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

		/**
		 * Creating all buttons instances
		 * */
		// Dashboard News feed button
		Button btn_job = (Button) findViewById(R.id.btn_job);

		// Dashboard Friends button
		Button btn_admin = (Button) findViewById(R.id.btn_admin);

		// Dashboard Messages button
		Button btn_marketing = (Button) findViewById(R.id.btn_marketing);

		// Dashboard Places button
		Button btn_dealer = (Button) findViewById(R.id.btn_dealer);

		// Dashboard Events button
		Button btn_bill = (Button) findViewById(R.id.btn_bill);

		// Dashboard Photos button
		Button btn_settings = (Button) findViewById(R.id.btn_settings);

		/**
		 * Handling all button click events
		 * */

		// Listening to News Feed button click
		btn_bill.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent i = new Intent(HomePage.this, AddBalanceExpense.class);
				startActivity(i);
			}
		});

		// Listening Friends button click
		btn_admin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// Launching News Feed Screen
				Log.d("ddfd", "dfd");
				Intent i = new Intent(getApplicationContext(), AdminLogin.class);
				startActivity(i);

			}
		});

		// Listening Messages button click
		btn_marketing.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				
				Intent i = new Intent(HomePage.this,MarketingPersonDetails.class);
				startActivity(i);
			}
		});

		// Listening to Places button click
		btn_dealer.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// Launching News Feed Screen
				// Intent i = new Intent(getApplicationContext(),
				// PlacesActivity.class);
				// startActivity(i);

				AlertDialog.Builder alert = new AlertDialog.Builder(HomePage.this);
				alert.setTitle("Select Your Option:");

				alert.setPositiveButton("Add Job",

				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						// Intent i = new Intent(HomePage.this, JobEntry.class);
						// startActivity(i);
					}

				});

				alert.setNegativeButton("View Job", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						// Intent i = new Intent(HomePage.this, JobsList.class);
						// startActivity(i);
					}
				});
				alert.show();

			}
		});

		// Listening to Events button click
		btn_job.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent i = new Intent(HomePage.this, SelectJob.class);
				startActivity(i);
			}
		});

		// Listening to Photos button click
		btn_settings.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) 
			{
				Intent i = new Intent(HomePage.this, ExportDatabase.class);
				startActivity(i);
				
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_page, menu);
		return true;
	}

}
