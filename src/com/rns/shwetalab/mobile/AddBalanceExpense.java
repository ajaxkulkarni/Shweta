package com.rns.shwetalab.mobile;

import com.rns.shwetalab.mobile.db.CommonUtil;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AddBalanceExpense extends Activity 
{

	Button balance,expense;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_balance_expense);
		
		init();

		balance.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {	
				// TODO Auto-generated method stub
				Intent i =new Intent(AddBalanceExpense.this,ViewMonth.class);
				startActivity(i);
			}
		});


		expense.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {	
				// TODO Auto-generated method stub
				Intent i =new Intent(AddBalanceExpense.this,AddExpense.class);
				startActivity(i);
			}
		});

	}

	private void init() 
	{
		// TODO Auto-generated method stub

		balance = (Button)findViewById(R.id.addbalance_button);
		expense = (Button)findViewById(R.id.addexpense_button);

	}
	
	@Override
	public void onBackPressed() 
	{
		
		Intent i = new Intent(AddBalanceExpense.this,HomePage.class);
		startActivity(i);
		super.onBackPressed();
	}
	
}
