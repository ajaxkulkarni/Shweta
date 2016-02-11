package com.rns.shwetalab.mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class BalanceDetails extends Activity 
{
	Button add;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_balance_details);
	
		add = (Button)findViewById(R.id.addamountbutton );
		add.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				Toast.makeText(getApplicationContext(),"Amount Inserted!!",Toast.LENGTH_SHORT).show();
				Intent i =new  Intent(BalanceDetails.this,DoctorMonthlyBalanceList.class);
				startActivity(i);
			}
		});
	}
}
