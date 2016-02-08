package com.rns.shwetalab.mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AddViewDealer extends Activity 
{

	Button add,view;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_view_dealer);
		
		add = (Button)findViewById(R.id.addDealer);
		view = (Button)findViewById(R.id.viewDealer);
		
		add.setOnClickListener(new OnClickListener() 
			{
			
			@Override
			public void onClick(View v) {
					Intent i = new Intent(AddViewDealer.this,AddDealer.class);
					startActivity(i);
			}
		});;

		view.setOnClickListener(new OnClickListener() 
		{
		
		@Override
		public void onClick(View v) {
				Intent i = new Intent(AddViewDealer.this,ExpandableDealerList.class);
				startActivity(i);
		}
	});;

		
		
	}
}
