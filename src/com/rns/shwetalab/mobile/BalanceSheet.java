package com.rns.shwetalab.mobile;

import com.rns.shwetalab.mobile.db.JobsDao;
import com.rns.shwetalab.mobile.domain.WorkPersonMap;
import com.rns.shwetalab.mobile.domain.WorkType;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class BalanceSheet extends Activity 
{
	TextView doctor_price,lab_price,doctor;
	WorkType worktype;
	WorkPersonMap workpersonmap;
	private JobsDao jobsDao;
	Button next;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_balance_sheet);
		jobsDao = new JobsDao(this);
		Bundle extras = getIntent().getExtras();
		final String month = extras.getString("Month");
		doctor_price = (TextView)findViewById(R.id.activity_billingsheet_doctor_textView); 
		doctor_price.setText(jobsDao.getDoctorIncomeForMonth(month).toString());
		next = (Button)findViewById(R.id.balancesheet_submit_button);
		doctor = (TextView)findViewById(R.id.activity_billingsheetdoctortextView);

		doctor.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				Intent i = new Intent(BalanceSheet.this,Doctor_List_Amount.class);
				i.putExtra("Month",month);
				startActivity(i);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.balance_sheet, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
