package com.rns.shwetalab.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.rns.shwetalab.mobile.adapter.ExpandableListViewAdapter;
import com.rns.shwetalab.mobile.db.DatabaseHelper;
import com.rns.shwetalab.mobile.db.JobsDao;

/**
 * Created by Rajesh on 8/28/2015.
 */
public class ExpandableDoctorListView extends Activity {
	private ExpandableListViewAdapter listAdapter;
	private ExpandableListView expListView;
	private JobsDao jobsDao;
	private String dateSelected;
	TextView date;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expandable_doctor_list_view);
		jobsDao = new JobsDao(getApplicationContext());
		date = (TextView) findViewById(R.id.expandabledoctorlistdate_textview);
		dateSelected = getIntent().getStringExtra(DatabaseHelper.JOB_DATE);
		expListView = (ExpandableListView) findViewById(R.id.myjobsexpandable_listview);
		date.setText(dateSelected);
		//prepareListData();
		listAdapter = new ExpandableListViewAdapter(this, jobsDao.getJobsByDate(dateSelected, getIntent().getStringExtra("type")));
		expListView.setAdapter(listAdapter);
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
				// Toast.makeText(getApplicationContext(), "Group Clicked " +
				// listDataHeader.get(groupPosition),
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
				// Toast.makeText(getApplicationContext(),
				// listDataHeader.get(groupPosition) + " : " +
				// listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition),
				// Toast.LENGTH_SHORT).show();

				return false;
			}
		});

	}

}
