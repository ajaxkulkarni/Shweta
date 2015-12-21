package com.rns.shwetalab.mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class AddExpense extends Activity 
{
	Button add;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_expense);

		add = (Button)findViewById(R.id.addbutton);
		add.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(),"Expense Added Successfully!!",Toast.LENGTH_SHORT).show();
			}
		});
	}
}
