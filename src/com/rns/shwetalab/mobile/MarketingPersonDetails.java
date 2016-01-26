package com.rns.shwetalab.mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MarketingPersonDetails extends Activity 
{
		
	Button add;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_marketing_person_details);
		
		add = (Button)findViewById(R.id.AddPersonbutton);
		add.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
					Intent i = new Intent(MarketingPersonDetails.this,MarketingList.class );
					startActivity(i);
			}
		});
	
	}
}
