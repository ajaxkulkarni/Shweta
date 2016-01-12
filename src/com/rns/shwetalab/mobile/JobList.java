package com.rns.shwetalab.mobile;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rns.shwetalab.mobile.adapter.JobListAdapter;

public class JobList extends Activity 
{

	ListView objLv;
	ArrayList<String> objArrayListName = new ArrayList<String>();	

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_job_list);

		objArrayListName.add("Ajinkya Kulkarni");
		objArrayListName.add("Rajesh Mangale");
		objArrayListName.add("Rohit Wadke");
		objArrayListName.add("Abhishek Patil");
		objArrayListName.add("Kunal Karanjkar");
		objArrayListName.add("Apoorv Tailang");
		objArrayListName.add("Ayush Dhar");
		objArrayListName.add("Rajesh Mangale");

		 objLv = (ListView) findViewById(R.id.Job_listView);

	        JobListAdapter Adapter = new JobListAdapter(this, objArrayListName);
	        objLv.setAdapter(Adapter);


	        objLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	            {
	                if(position==0)
	                {
	                    Intent i = new Intent(JobList.this,ExpandableDoctorListView.class);
	                    startActivity(i);
	                }



	            }
	        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.job_list, menu);
		return true;
	}


}
