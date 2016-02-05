package com.rns.shwetalab.mobile;

import java.util.ArrayList;

import com.rns.shwetalab.mobile.adapter.DealerJobListAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class DealerJobsList extends Activity 
{
	
	ListView lv;
	ArrayList<String> dealername = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dealer_jobs_list);
 
		dealername.add("Rajesh Mangale");
		dealername.add("Kunal Karanjkar");
		dealername.add("Rohit Wadke");

		lv = (ListView)findViewById(R.id.dealerlistlistView);
		DealerJobListAdapter Adapter = new DealerJobListAdapter(DealerJobsList.this,dealername);
		lv.setAdapter(Adapter);
	}
}
