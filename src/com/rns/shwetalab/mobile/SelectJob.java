package com.rns.shwetalab.mobile;

import com.rns.shwetalab.mobile.db.CommonUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SelectJob extends Activity 
{

	Button add,viewDentistJobs;
	private Button viewLabsJobs;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_job);

		init();
		add.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				Intent i = new Intent(SelectJob.this,JobEntry.class);
				startActivity(i);
			}
		});


		viewDentistJobs.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) {
				Intent i = new Intent(SelectJob.this,AddDate.class);
				i.putExtra("type", CommonUtil.TYPE_DOCTOR);
				startActivity(i);
			}
		});
		
		viewLabsJobs.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) {
				Intent i = new Intent(SelectJob.this,AddDate.class);
				i.putExtra("type", CommonUtil.TYPE_LAB);
				startActivity(i);
			}
		});
	}

	private void init() 
	{
		add = (Button)findViewById(R.id.addjob_button);
		viewDentistJobs = (Button)findViewById(R.id.view_dentists_jobs_button);
		viewLabsJobs = (Button)findViewById(R.id.view_labs_jobs_button);
		
	}
}
