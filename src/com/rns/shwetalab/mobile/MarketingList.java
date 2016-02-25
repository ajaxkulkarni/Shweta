package com.rns.shwetalab.mobile;

import java.util.ArrayList;

import com.rns.shwetalab.mobile.adapter.MarketingAdapter;
import com.rns.shwetalab.mobile.db.MarketingDao;
import com.rns.shwetalab.mobile.domain.Marketing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MarketingList extends Activity {

	ArrayList<String> objArrayListName = new ArrayList<String>();
	ListView lv;
	Marketing marketing;
	MarketingDao marketingDAO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_marketing_list);

		lv = (ListView) findViewById(R.id.marketinglistView);
		marketingDAO = new MarketingDao(this);
		final MarketingAdapter Adapter = new MarketingAdapter(MarketingList.this, marketingDAO.queryForAll());
		lv.setAdapter(Adapter);

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
			{

				Marketing marketing  = (Marketing)Adapter.getItem(position);
				String personname = marketing.getMarketing_name();
				Intent i = new Intent(MarketingList.this, AddViewDescription.class);
				i.putExtra("personname", personname);
				startActivity(i);
			}
		});
	}
}
