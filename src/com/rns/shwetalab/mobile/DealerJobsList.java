package com.rns.shwetalab.mobile;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.rns.shwetalab.mobile.adapter.DealerJobListAdapter;
import com.rns.shwetalab.mobile.db.DealerDao;
import com.rns.shwetalab.mobile.domain.Dealer;
import com.rns.shwetalab.mobile.domain.Marketing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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

		lv.setOnItemClickListener(new OnItemClickListener() 
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
			{
				Dealer dealer  = (Dealer)Adapter.getItem(position);
				String personname = dealer.getDealer().getName();
				Intent i = new Intent(DealerJobsList.this,DealerAmountDetails.class);
				i.putExtra("Data",new Gson().toJson(Adapter.getItem(position)));
				i.putExtra("Name",personname);
				startActivity(i);
			}
		});
	}
}
