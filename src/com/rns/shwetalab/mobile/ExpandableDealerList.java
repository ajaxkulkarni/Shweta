package com.rns.shwetalab.mobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.rns.shwetalab.mobile.adapter.ExpandableDealerListAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

public class ExpandableDealerList extends Activity {

	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;	

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expandable_dealer_list);

		expListView = (ExpandableListView) findViewById(R.id.planName_expandableListView);

		// preparing list data
		prepareListData();

		listAdapter = new ExpandableDealerListAdapter(this, listDataHeader, listDataChild);

		// setting list adapter
		expListView.setAdapter(listAdapter);
		expListView.setGroupIndicator(null);

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

		// Listview Group expanded listener
		expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				Toast.makeText(getApplicationContext(),
						listDataHeader.get(groupPosition) + " Expanded",
						Toast.LENGTH_SHORT).show();
			}
		});

		// Listview Group collasped listener
		expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
				Toast.makeText(getApplicationContext(),
						listDataHeader.get(groupPosition) + " Collapsed",
						Toast.LENGTH_SHORT).show();

			}
		});

		// Listview on child click listener
		expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(
						getApplicationContext(),
						listDataHeader.get(groupPosition)
						+ " : "
						+ listDataChild.get(
								listDataHeader.get(groupPosition)).get(
										childPosition), Toast.LENGTH_SHORT)
				.show();
				return false;
			}
		});


	}


	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		// Adding child data
		listDataHeader.add("Rajesh Mangale");
		listDataHeader.add("Rohit Wadke");
		listDataHeader.add("Kunal Karanjkar");
		listDataHeader.add("Anand Kore");
		//listDataHeader.add("Marie Gold Plan");


		// Adding child data
		List<String> Rajesh = new ArrayList<String>();
		Rajesh.add("ABC");
		Rajesh.add("Rs.5000/-");

		List<String> Rohit = new ArrayList<String>();
		Rohit.add("PQR");
		Rohit.add("Rs.3500/-");

		List<String> Kunal = new ArrayList<String>();
		Kunal.add("XYZ");
		Kunal.add("Rs.4000/-");

		List<String> Anand = new ArrayList<String>();
		Anand.add("LMN");
		Anand.add("Rs.3000/-");

		listDataChild.put(listDataHeader.get(0), Rajesh); // Header, Child data
		listDataChild.put(listDataHeader.get(1), Rohit);
		listDataChild.put(listDataHeader.get(2), Kunal);
		listDataChild.put(listDataHeader.get(3), Anand);
	}

}


