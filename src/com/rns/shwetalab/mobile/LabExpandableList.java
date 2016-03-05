package com.rns.shwetalab.mobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.rns.shwetalab.mobile.adapter.LabExpandableListAdapter;
import com.rns.shwetalab.mobile.adapter.JobsExpandableListViewAdapter;
import com.rns.shwetalab.mobile.db.JobsDao;

public class LabExpandableList extends Activity 
{

	private LabExpandableListAdapter joblistAdapter;
	private ExpandableListView expListView;
	private List<String> listDataHeader;
	private HashMap<String, List<String>> listDataChild;
	private JobsDao jobsDao;
	private String dateSelected;
	TextView date;
	
	ListView objLv;
	ArrayList<String> objArrayListName = new ArrayList<String>();	

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_job_list);

		expListView = (ExpandableListView) findViewById(R.id.labjobsexpandableListView);
		jobsDao = new JobsDao(getApplicationContext());
		Bundle extras = getIntent().getExtras();
		String month = extras.getString("Month");
		String name = extras.getString("Name");
		String type = extras.getString("Type");

		joblistAdapter = new LabExpandableListAdapter(this, jobsDao.getLabJobsByMonth(month));
		expListView.setAdapter(joblistAdapter);
		
		 objLv = (ListView) findViewById(R.id.Job_listView);

//	        LabExpandableListAdapter Adapter = new LabExpandableListAdapter(this, jobsDao.getJobsByMonthName(month,name));
//	        objLv.setAdapter(Adapter);


	        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
				@Override
				public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
					return false;
				}
			});

			expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
				@Override
				public void onGroupExpand(int groupPosition) {
				}
			});

			expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
				@Override
				public void onGroupCollapse(int groupPosition) {
				}
			});

			// Listview on child click listener
			expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
				@Override
				public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
					return false;
				}
			});

			expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
				@Override
				public void onGroupExpand(int groupPosition) {
				}
			});

			expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
				@Override
				public void onGroupCollapse(int groupPosition) {
				}
			});

			// Listview on child click listener
			expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
				@Override
				public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
					return false;
				}
			});

	}

	


}
