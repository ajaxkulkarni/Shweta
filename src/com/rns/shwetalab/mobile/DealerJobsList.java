package com.rns.shwetalab.mobile;

import java.util.ArrayList;

import com.rns.shwetalab.mobile.adapter.DealerJobListAdapter;
import com.rns.shwetalab.mobile.db.DealerDao;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class DealerJobsList extends Activity 
{
	
	ListView lv;
	ArrayList<String> dealername = new ArrayList<String>();
	DealerDao dealerDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dealer_jobs_list);
		dealerDao = new DealerDao(this);
		lv = (ListView)findViewById(R.id.dealerlistlistView);
		final DealerJobListAdapter Adapter = new DealerJobListAdapter(DealerJobsList.this,dealerDao.getAll());
		lv.setAdapter(Adapter);
	}
}
