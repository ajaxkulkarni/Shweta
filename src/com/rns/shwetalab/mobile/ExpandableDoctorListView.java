package com.rns.shwetalab.mobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

/**
 * Created by Rajesh on 8/28/2015.
 */
public class ExpandableDoctorListView extends Activity {
	ExpandableListViewAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expandable_doctor_list_view);

		expListView = (ExpandableListView) findViewById(R.id.myjobsexpandable_listview);

		prepareListData();

		listAdapter = new ExpandableListViewAdapter(this, listDataHeader, listDataChild);


		expListView.setAdapter(listAdapter);

		// Listview Group click listener
		expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// Toast.makeText(getApplicationContext(),
				// "Group Clicked " + listDataHeader.get(groupPosition),
				// Toast.LENGTH_SHORT).show();
				return false;
			}
		});


		expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				//                Toast.makeText(getApplicationContext(),
				//                        listDataHeader.get(groupPosition) + " Expanded",
				//                        Toast.LENGTH_SHORT).show();
			}
		});

		expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
				//                Toast.makeText(getApplicationContext(),
				//                        listDataHeader.get(groupPosition) + " Collapsed",
				//                        Toast.LENGTH_SHORT).show();

			}
		});

		// Listview on child click listener
		expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				//                Toast.makeText(
				//                        getApplicationContext(),
				//                        listDataHeader.get(groupPosition)
				//                                + " : "
				//                                + listDataChild.get(
				//                                listDataHeader.get(groupPosition)).get(
				//                                childPosition), Toast.LENGTH_SHORT)
				//                        .show();
				return false;
			}
		});

	}



	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		// Adding child data
		listDataHeader.add("Dr.Ajinkya Kulkarni");
		listDataHeader.add("Dr.Ajinkya Kulkarni ");
		listDataHeader.add("Dr.Ajinkya Kulkarni");
		listDataHeader.add("Dr.Ajinkya Kulkarni");
		listDataHeader.add("Dr.Ajinkya Kulkarni");
		listDataHeader.add("Dr.Ajinkya Kulkarni");
		listDataHeader.add("Dr.Ajinkya Kulkarni");

		// Adding child data
		List<String> patientinfo1 = new ArrayList<String>();
		patientinfo1.add("Rohit Wadke");
		patientinfo1.add("Type of Work-Shade");
		patientinfo1.add("28/08/2015");

		List<String> patientinfo2 = new ArrayList<String>();
		patientinfo2.add("Rajesh Mangale");
		patientinfo2.add("Type of Work-Shade");
		patientinfo2.add("02/08/2015");


		List<String> patientinfo3 = new ArrayList<String>();
		patientinfo3.add("Abhishek Patil");
		patientinfo3.add("Type of Work-Shade");
		patientinfo3.add("08/08/2015");


		List<String> patientinfo4 = new ArrayList<String>();
		patientinfo4.add("Kunal Karanjkar");
		patientinfo4.add("Type of Work-Shade");
		patientinfo4.add("18/08/2015");

		List<String> patientinfo5 = new ArrayList<String>();
		patientinfo5.add("Apoorv Tailang");
		patientinfo5.add("Type of Work-Shade");
		patientinfo5.add("28/07/2015");

		List<String> patientinfo6 = new ArrayList<String>();
		patientinfo6.add("Ayush Dhar");
		patientinfo6.add("Type of Work-Shade");
		patientinfo6.add("08/07/2015");

		List<String> patientinfo7 = new ArrayList<String>();
		patientinfo7.add("Apoorv Tailang");
		patientinfo7.add("Type of Work-Shade");
		patientinfo7.add("18/07/2015");


		listDataChild.put(listDataHeader.get(0), patientinfo1); // Header, Child data
		listDataChild.put(listDataHeader.get(1), patientinfo2);
		listDataChild.put(listDataHeader.get(2), patientinfo3);
		listDataChild.put(listDataHeader.get(3), patientinfo4);
		listDataChild.put(listDataHeader.get(4), patientinfo5);
		listDataChild.put(listDataHeader.get(5), patientinfo6);
		listDataChild.put(listDataHeader.get(6), patientinfo7);


	}


}
