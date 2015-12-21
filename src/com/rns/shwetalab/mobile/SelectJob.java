package com.rns.shwetalab.mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SelectJob extends Activity 
{

	Button add,view;

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


		view.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) {
				Intent i = new Intent(SelectJob.this,JobList.class);
				startActivity(i);
			}
		});
	}

	private void init() 
	{
		add = (Button)findViewById(R.id.addjob_button);
		view = (Button)findViewById(R.id.viewjob_button);
	}
}
