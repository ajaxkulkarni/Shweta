package com.rns.shwetalab.mobile;

import com.rns.shwetalab.mobile.db.CommonUtil;

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
		
//		if(CommonUtil.FLAG!=1)
//		{
//			CommonUtil.showNotLogin(AddViewDealer.this, "Not Logged in");
//		}
		
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
				Intent i = new Intent(AddViewDealer.this,DealerJobsList.class);
				startActivity(i);
		}
	});

		
		
	}
	
	@Override
	public void onBackPressed() 
	{
		
		Intent i = new Intent(AddViewDealer.this,HomePage.class);
		startActivity(i);
		super.onBackPressed();
	}
	
}
