package com.rns.shwetalab.mobile;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rns.shwetalab.mobile.adapter.MarketingDescriptionListAdapter;
import com.rns.shwetalab.mobile.db.DescriptionDao;
import com.rns.shwetalab.mobile.db.MarketingDao;
import com.rns.shwetalab.mobile.domain.Marketing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MarketingDescriptionList extends Activity 
{
	List<Marketing> list = new ArrayList<Marketing>();
	TextView name;
	ListView lv;
	ImageView addperson;
	MarketingDao marketingDao;
	DescriptionDao descriptionDao;


	Map<Date, String> map = new HashMap<Date, String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_marketing_description_list);
		name = (TextView)findViewById(R.id.market_personname_textView);
		addperson = (ImageView)findViewById(R.id.addperson_imageView);
		Bundle extras = getIntent().getExtras();
		String names = extras.getString("Name");
		name.setText(names);

		addperson.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v){
				Intent i =new Intent(MarketingDescriptionList.this,AddPerson.class);
				startActivity(i);
			}
		});
		descriptionDao = new DescriptionDao(this);
		lv = (ListView)findViewById(R.id.marketing_description_listView);
		//		prepareList(names);
		//marketingDao = new MarketingDao(this);
		final MarketingDescriptionListAdapter Adapter = new MarketingDescriptionListAdapter(MarketingDescriptionList.this, descriptionDao.getPersonName(names));
		if(Adapter.getCount()!=0)
		{
			lv.setAdapter(Adapter);
		}
		else
			Toast.makeText(getApplicationContext(),"No Description Available!",Toast.LENGTH_SHORT).show();

	}
}
