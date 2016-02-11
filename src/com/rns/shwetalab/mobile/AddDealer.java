package com.rns.shwetalab.mobile;

import java.math.BigDecimal;

import com.rns.shwetalab.mobile.db.CommonUtil;
import com.rns.shwetalab.mobile.db.DealerDao;
import com.rns.shwetalab.mobile.domain.Dealer;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddDealer extends Activity 
{
	
	
	EditText name,material,price,amount_paid;
	Button add;
	Dealer dealer;
	DealerDao dealerDao;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_dealer);
		
		dealer = new Dealer();
		dealerDao = new DealerDao(getApplicationContext());
		add = (Button)findViewById(R.id.dealerAddbutton);
		
		add.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				//validate();
				preparedealer();
				dealerDao.insertDetails(dealer);
				CommonUtil.showMessage(AddDealer.this);
			}

			
		});
		
		
		
		
	}

	
	private void preparedealer() 
	{
		name = (EditText)findViewById(R.id.dealernameeditText);
		material = (EditText)findViewById(R.id.dealermaterialeditText);
		price = (EditText)findViewById(R.id.dealerpriceeditText);
		amount_paid = (EditText)findViewById(R.id.dealerpaideditText);

		dealer.setDealer_name(name.getText().toString());
		dealer.setMaterial(material.getText().toString());
		dealer.setPrice(new BigDecimal(price.getText().toString()));
		dealer.setAmount_paid(new BigDecimal(amount_paid.getText().toString()));
		
		
		
	}

	
	
	private void validate() {
		if (TextUtils.isEmpty(name.getText())) {
			name.setError(Html.fromHtml("<font color='black'>Enter Name</font>"));
		}
		if (TextUtils.isEmpty(material.getText())) {
			material.setError(Html.fromHtml("<font color='black'>Enter Material</font>"));
		}
		if (TextUtils.isEmpty(price.getText())) {
			price.setError(Html.fromHtml("<font color='black'>Enter Price</font>"));
		}
		if (TextUtils.isEmpty(amount_paid.getText())) {
			amount_paid.setError(Html.fromHtml("<font color='black'>Enter Amount Paid</font>"));
		}

	}
}
