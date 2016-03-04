package com.rns.shwetalab.mobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.rns.shwetalab.mobile.adapter.ExpandableListViewAdapter;
import com.rns.shwetalab.mobile.adapter.JobsExpandableListViewAdapter;
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
		String name = extras.getString("Name");
		prepareListData(month,name);
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

	private void prepareListData(String month, String name) 
	{
		jobsDao = new JobsDao(this);
		List<Job> jobs = jobsDao.getJobsByMonth(month);
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();
		for (Job job : jobs) {
			if (job.getDoctor() == null) {
				continue;
			}
			List<Job> jobslist = jobsDao.getJobsByMonthName(month,job.getId());
			for (Job joblist : jobslist) {
				if (joblist.getDoctor() == null) {
					continue;
				}
				List<String> jobDetails = new ArrayList<String>();
				jobDetails.add("Patient :" + joblist.getPatientName());
				if (joblist.getShade() != null) {
					jobDetails.add("Shade :" + joblist.getShade().toString());
				}
				if (joblist.getWorkTypes() != null) 
				{
					jobDetails.add("Work :" + ((WorkType) joblist.getWorkTypes()).getName());
				}
				if (joblist.getPrice() != null) {
					jobDetails.add("Price :" + joblist.getPrice().toString());
				}
				if (joblist.getQuadrent() != null) {
					jobDetails.add("Tooth Quadrent :" + joblist.getQuadrent().toString());
				}
				if (joblist.getPosition() != null) {
					jobDetails.add("Tooth Position :" + joblist.getPosition().toString());
				}
				listDataHeader.add(joblist.getDate().toString());
				listDataChild.put(joblist.getDoctor().getName(), jobDetails);
			}
		}
	}
}