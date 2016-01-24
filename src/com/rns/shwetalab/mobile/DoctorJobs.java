package com.rns.shwetalab.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DoctorJobs extends Activity {

	TextView doctorName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctor_jobs);
		doctorName = (TextView) findViewById(R.id.doctor_jobs_nametextView1);
		Bundle extras = getIntent().getExtras();
		String name = extras.getString("Name");
		doctorName.setText(name);
	}
}
