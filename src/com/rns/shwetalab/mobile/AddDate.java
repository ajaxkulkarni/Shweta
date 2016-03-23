package com.rns.shwetalab.mobile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.rns.shwetalab.mobile.adapter.AddDateListAdapter;
import com.rns.shwetalab.mobile.db.CommonUtil;
import com.rns.shwetalab.mobile.db.DatabaseHelper;
import com.rns.shwetalab.mobile.db.JobsDao;

public class AddDate extends Activity implements OnClickListener {

	private EditText dateSelected;
	private ListView daysListView;
	private DatePickerDialog toDatePickerDialog;
	private ArrayList<String> days = new ArrayList<String>();
	private SimpleDateFormat dateFormatter;
	private Button submitDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_date);
		new JobsDao(getApplicationContext());
		dateFormatter = new SimpleDateFormat(CommonUtil.DATE_FORMAT, Locale.US);
		dateSelected = (EditText) findViewById(R.id.calender_editText);
		dateSelected.setText(CommonUtil.convertDate(new Date()));
		submitDate = (Button) findViewById(R.id.add_date_activity_submit_date);

		setDateTimeField();

		days.add("Yesterday");
		days.add("Day Before Yesterday");
		days.add("3 Days before");
		days.add("4 Days before");
		days.add("5 Days before");
		days.add("6 Days before");
		days.add("A Week before");

		daysListView = (ListView) findViewById(R.id.add_date_listView);
		AddDateListAdapter Adapter = new AddDateListAdapter(this, days);
		daysListView.setAdapter(Adapter);

		// hello
		/* Needs to be changed */

		submitDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			//	Intent intent = nextActivity(dateSelected.getText().toString());
				Intent intent = new Intent(AddDate.this, ExpandableDoctorListView.class);
				intent.putExtra(DatabaseHelper.JOB_DATE, dateSelected.getText().toString());
				intent.putExtra("type", getIntent().getStringExtra("type"));
				startActivity(intent);
			}

		});
		
		daysListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, -position - 1);
				Intent intent = nextActivity(CommonUtil.convertDate(cal.getTime()));
				startActivity(intent);
			}
			
		}); 

	}
	
	private Intent nextActivity(String date) {
		Intent intent = new Intent(AddDate.this, ExpandableDoctorListView.class);
		intent.putExtra(DatabaseHelper.JOB_DATE, date);
		intent.putExtra("type", getIntent().getStringExtra("type"));
		return intent;
	}

	private void setDateTimeField() {

		dateSelected.setOnClickListener(this);
		Calendar newCalendar = Calendar.getInstance();
		toDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				Calendar newDate = Calendar.getInstance();
				newDate.set(year, monthOfYear, dayOfMonth);
				dateSelected.setText(dateFormatter.format(newDate.getTime()));
			}

		}, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
	}

	@Override
	public void onClick(View view) {

		toDatePickerDialog.show();

	}

}
