package com.rns.shwetalab.mobile;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class JobEntry extends Activity implements OnItemSelectedListener 
{

	Button addwork,jobentry;
	EditText e1,e2,e3,e4;
	int count=0;
	Spinner sp1,sp2;

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
		sp1= (Spinner)findViewById(R.id.spinner_position);
		sp2= (Spinner)findViewById(R.id.spinner_quadrant);
		
		spinner_quad(sp1);
	//	spinner_position(sp2);

		

		
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

	private void spinner_docname(Spinner sp_docnm2) {


		sp_docnm2.setOnItemSelectedListener(this);

		List<String> categories = new ArrayList<String>();
		categories.add("ABC");
		categories.add("XYZ");
		categories.add("Computer");
		categories.add("ABC");
		categories.add("XYZ");

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

		sp_docnm2.setAdapter(dataAdapter);

	}

	private void spinner_quad(Spinner sp_docnm2) {


		sp_docnm2.setOnItemSelectedListener(this);

		List<String> categories = new ArrayList<String>();
		categories.add("1");
		categories.add("2");
		categories.add("3");
		categories.add("4");


		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

	
	

}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
}