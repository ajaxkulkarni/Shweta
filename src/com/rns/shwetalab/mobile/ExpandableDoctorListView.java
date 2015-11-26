package com.rns.shwetalab.mobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.rns.shwetalab.mobile.adapter.ExpandableListViewAdapter;
import com.rns.shwetalab.mobile.db.DatabaseHelper;
import com.rns.shwetalab.mobile.db.JobsDao;
import com.rns.shwetalab.mobile.db.WorkPersonMapDao;
import com.rns.shwetalab.mobile.domain.Job;
import com.rns.shwetalab.mobile.domain.WorkPersonMap;

/**
 * Created by Rajesh on 8/28/2015.
 */
public class ExpandableDoctorListView extends Activity {
	private ExpandableListViewAdapter listAdapter;
	private ExpandableListView expListView;
	private List<String> listDataHeader;
	private HashMap<String, List<String>> listDataChild;
	private JobsDao jobsDao;
	private String dateSelected;
	private WorkPersonMapDao workPersonMapDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expandable_doctor_list_view);
		jobsDao = new JobsDao(getApplicationContext());
		workPersonMapDao = new WorkPersonMapDao(getApplicationContext());
		
		dateSelected = getIntent().getStringExtra(DatabaseHelper.JOB_DATE);
		expListView = (ExpandableListView) findViewById(R.id.myjobsexpandable_listview);

		prepareListData();

		listAdapter = new ExpandableListViewAdapter(this, listDataHeader, listDataChild);

		expListView.setAdapter(listAdapter);
		// Listview Group click listener
		expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				// Toast.makeText(getApplicationContext(),
				// "Group Clicked " + listDataHeader.get(groupPosition),
				// Toast.LENGTH_SHORT).show();
				return false;
			}
		});

		expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				// Toast.makeText(getApplicationContext(),
				// listDataHeader.get(groupPosition) + " Expanded",
				// Toast.LENGTH_SHORT).show();
			}
		});

		expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
				// Toast.makeText(getApplicationContext(),
				// listDataHeader.get(groupPosition) + " Collapsed",
				// Toast.LENGTH_SHORT).show();

			}
		});

		// Listview on child click listener
		expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				// Toast.makeText(
				// getApplicationContext(),
				// listDataHeader.get(groupPosition)
				// + " : "
				// + listDataChild.get(
				// listDataHeader.get(groupPosition)).get(
				// childPosition), Toast.LENGTH_SHORT)
				// .show();
				return false;
			}
		});

	}

	private void prepareListData() {

		List<Job> jobs = jobsDao.getJobsByDate(dateSelected);
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		for (Job job : jobs) {
			if(job.getDoctor() == null) {
				continue;
			}
			List<String> jobDetails = new ArrayList<String>();
			jobDetails.add(job.getPatientName());
			if (job.getShade() != null) {
				jobDetails.add(job.getShade().toString());
			}
			if (job.getWorkType() != null) {
				jobDetails.add(job.getWorkType().getName());
			}
			prepareJobPrice(job);
			if (job.getPrice() != null) {
				jobDetails.add(job.getPrice().toString());
			}
			listDataHeader.add(job.getDoctor().getName());
			listDataChild.put(job.getDoctor().getName(), jobDetails);
		}

	}

	private void prepareJobPrice(Job job) {
		WorkPersonMap map = new WorkPersonMap();
		map.setPerson(job.getDoctor());
		map.setWorkType(job.getWorkType());
		map = workPersonMapDao.getWorkPersonMap(map);
		if(map == null) {
			return;
		}
		job.setPrice(map.getPrice());
	}

}
