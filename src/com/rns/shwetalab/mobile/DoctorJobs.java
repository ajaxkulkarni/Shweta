package com.rns.shwetalab.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DoctorJobs extends Activity 
{

	TextView doctor_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctor_jobs);

		doctor_name = (TextView)findViewById(R.id.doctor_jobs_nametextView1);
		Bundle extras = getIntent().getExtras();
		String name = extras.getString("Name");
		doctor_name.setText(name);
		
		
		

	}
}
