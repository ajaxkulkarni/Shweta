package com.example.dentallab;

import com.example.detallab.R;
import com.example.detallab.R.id;
import com.example.detallab.R.layout;
import com.example.detallab.R.menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class JobEntry extends Activity 
{

	Button addwork,jobentry;
	EditText e1,e2,e3,e4;
	int count=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_job_entry);


		addwork = (Button)findViewById(R.id.jobentry_buttonadd);
		jobentry = (Button)findViewById(R.id.jobentrybutton);
		e1 = (EditText)findViewById(R.id.jobentry_worktype_editText);
		e2 = (EditText)findViewById(R.id.jobentry_worktype1_editText);
		e3 = (EditText)findViewById(R.id.jobentry_worktype2_editText);
		e4 = (EditText)findViewById(R.id.jobentry_worktype3_editText);



		jobentry.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub


				Toast.makeText(getApplicationContext(), "Job Entered Successfully",Toast.LENGTH_SHORT).show();

			}
		});		
		addwork.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				if(count==0)
				{

					e2.setVisibility(View.VISIBLE);
					count++;
				}


				if(count==1)
				{

					e3.setVisibility(View.VISIBLE);
					count++;
				}

				else if(count==2)
				{
					e4.setVisibility(View.VISIBLE);
					count++;
				}
				else if(count==3)
					Toast.makeText(getApplicationContext(),"You can't add more than 3 Jobs",Toast.LENGTH_SHORT).show();


			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.job_entry, menu);
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
