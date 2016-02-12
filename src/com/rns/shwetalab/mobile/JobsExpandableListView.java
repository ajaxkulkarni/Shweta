package com.rns.shwetalab.mobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.rns.shwetalab.mobile.adapter.ExpandableListViewAdapter;
import com.rns.shwetalab.mobile.db.JobsDao;
import com.rns.shwetalab.mobile.domain.Job;
import com.rns.shwetalab.mobile.domain.WorkType;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class JobsExpandableListView extends Activity 
{
	private JobsExpandableListViewAdapter joblistAdapter;
	private ExpandableListView expListView;
	private List<String> listDataHeader;
	private HashMap<String, List<String>> listDataChild;
	private JobsDao jobsDao;
	private String dateSelected;
	TextView date;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jobs_expandable_list_view);
		expListView = (ExpandableListView) findViewById(R.id.jobsexpandableListView);
		Bundle extras = getIntent().getExtras();
		String month = extras.getString("Month");
		prepareListData(month);
		joblistAdapter = new JobsExpandableListViewAdapter(this, listDataHeader, listDataChild);
		expListView.setAdapter(joblistAdapter);
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
			//	Toast.makeText(getApplicationContext(), "Group Clicked " + listDataHeader.get(groupPosition), Toast.LENGTH_SHORT).show();
				return false;
			}
		});

		expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
			//	Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " Expanded", Toast.LENGTH_SHORT).show();
			}
		});

		expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
				//Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " Collapsed", Toast.LENGTH_SHORT).show();

			}
		});

		// Listview on child click listener
		expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//				Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " : " + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition),
//						Toast.LENGTH_SHORT).show();

				return false;
			}
		});

		
	}
	
	private void prepareListData(String month) 
	{
		jobsDao = new JobsDao(this);
		List<Job> jobs = jobsDao.getJobsByMonth(month);
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();
		for (Job job : jobs) {
			if (job.getDoctor() == null) {
				continue;
			}
			List<String> jobDetails = new ArrayList<String>();
			
			jobDetails.add("Case Id :" + job.getId());
			
			jobDetails.add("Patient :" + job.getPatientName());
			if (job.getShade() != null) {
			jobDetails.add("Shade :" + job.getShade().toString());
			}
			if (job.getWorkTypes() != null) 
			{
				jobDetails.add("Work :" + ((WorkType) job.getWorkTypes()).getName());
			}
			if (job.getPrice() != null) {
				jobDetails.add("Price :" + job.getPrice().toString());
			}
			if (job.getQuadrent() != null) {
				jobDetails.add("Tooth Quadrent :" + job.getQuadrent().toString());
			}
			if (job.getPosition() != null) {
				jobDetails.add("Tooth Position :" + job.getPosition().toString());
			}
			listDataHeader.add(job.getDoctor().getName());
			listDataChild.put(job.getDoctor().getName(), jobDetails);
		}
	}
}
