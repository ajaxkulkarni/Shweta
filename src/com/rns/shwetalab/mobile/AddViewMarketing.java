package com.rns.shwetalab.mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AddViewMarketing extends Activity 
{

	Button add,view;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_view_marketing);
		add = (Button)findViewById(R.id.addMArketingPerson);
		view = (Button)findViewById(R.id.viewMarketingPerson);
		add.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				Intent i = new Intent(AddViewMarketing.this,MarketingPersonDetails.class);
				startActivity(i);
			}
		});
		view.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				Intent i = new Intent(AddViewMarketing.this,MarketingList.class );
				startActivity(i);
			}
		});

	}
}
