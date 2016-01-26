package com.rns.shwetalab.mobile;

import java.util.ArrayList;

import com.rns.shwetalab.mobile.adapter.MarketingAdapter;

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

public class MarketingList extends Activity 
{
	
	ArrayList<String> objArrayListName  = new ArrayList<String>();
	ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_marketing_list);
		
		objArrayListName.add("Rajesh");
		objArrayListName.add("Rohit");
		objArrayListName.add("Kunal");
		objArrayListName.add("Anand");
		objArrayListName.add("Ajinkya");
		
	
		lv = (ListView)findViewById(R.id.marketinglistView);
		MarketingAdapter Adapter = new  MarketingAdapter (MarketingList.this,objArrayListName);
		lv.setAdapter(Adapter);
		
		
		lv.setOnItemClickListener(new OnItemClickListener() 
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
			{
				if(position == 0)
				{
					Intent i = new Intent(MarketingList.this,DetailMarketing.class);
					startActivity(i);
				}
				if(position == 1)
				{
					Intent i = new Intent(MarketingList.this,DetailMarketing.class);
					startActivity(i);
				}
				if(position == 2)
				{
					Intent i = new Intent(MarketingList.this,DetailMarketing.class);
					startActivity(i);
				}
				
			}
			
		});
	
	}
}
