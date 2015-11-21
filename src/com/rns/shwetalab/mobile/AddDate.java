package com.rns.shwetalab.mobile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.rns.shwetalab.mobile.adapter.AddDateListAdapter;
import com.rns.shwetalab.mobile.db.CommonUtil;
import com.rns.shwetalab.mobile.db.DatabaseHelper;
import com.rns.shwetalab.mobile.db.JobsDao;
import com.rns.shwetalab.mobile.domain.Job;

public class AddDate extends Activity implements OnClickListener
{


	private EditText dateSelected;

	ListView objLv;
	private DatePickerDialog toDatePickerDialog;
	ArrayList<String> objArrayListDate = new ArrayList<String>();
	private SimpleDateFormat dateFormatter;
	private Button submitDate;
	private JobsDao jobsDao;


	Calendar myCalendar = Calendar.getInstance();
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_date);
		jobsDao = new JobsDao(getApplicationContext());
		dateFormatter = new SimpleDateFormat(CommonUtil.DATE_FORMAT, Locale.US);
		dateSelected = (EditText)findViewById(R.id.calender_editText);
		submitDate = (Button) findViewById(R.id.add_date_activity_submit_date);
		
		setDateTimeField();

		objArrayListDate.add("Yesterday");
		objArrayListDate.add("Day Before Yesterday");
		objArrayListDate.add("Last 3 Day");
		objArrayListDate.add("Last 4 Day");
		objArrayListDate.add("Last 5 Day");
		objArrayListDate.add("Last 6 Day");
		objArrayListDate.add("Last 7 Day");
		
		objLv = (ListView) findViewById(R.id.add_date_listView);
		AddDateListAdapter Adapter = new AddDateListAdapter (this, objArrayListDate);
		objLv.setAdapter(Adapter);
		
		/*Needs to be changed*/
		
		submitDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AddDate.this,ExpandableDoctorListView.class);
				intent.putExtra(DatabaseHelper.JOB_DATE, dateSelected.getText().toString());
				startActivity(intent);
			}
		});


	}


	private void setDateTimeField() {

		dateSelected.setOnClickListener(this);

		Calendar newCalendar = Calendar.getInstance();


		toDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() 
		{

			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) 
			{
				Calendar newDate = Calendar.getInstance();
				newDate.set(year, monthOfYear, dayOfMonth);
				dateSelected.setText(dateFormatter.format(newDate.getTime()));
			}

		},newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
	}


	@Override
	public void onClick(View view) 
	{

		toDatePickerDialog.show();

	}

}
