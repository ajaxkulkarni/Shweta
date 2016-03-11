package com.rns.shwetalab.mobile;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import com.rns.shwetalab.mobile.db.BalanceAmountDao;
import com.rns.shwetalab.mobile.db.CommonUtil;
import com.rns.shwetalab.mobile.domain.Balance_Amount;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DoctorAmountDetails extends Activity {
	EditText price, balance;
	Button pay;
	Balance_Amount balanceamount;
	int total, current_month, current_year;
	int id;
	List<Integer> balance_data;
	BalanceAmountDao balanceamountdao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctor_amount_details);
		final Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
		price = (EditText) findViewById(R.id.doctor_amount_totalprice_editText);
		balance = (EditText) findViewById(R.id.doctor_amount_balance_editText);
		pay = (Button) findViewById(R.id.pay_balance_amount_button);
		balanceamountdao = new BalanceAmountDao(getApplicationContext());
		Bundle extras = getIntent().getExtras();
		// final String amount = extras.getString("Price");
		String new_amount = extras.getString("New_Balance");
		id = extras.getInt("ID");
		price.setText(new_amount);
		balanceamount = new Balance_Amount();
		current_month = localCalendar.get(Calendar.MONTH) + 1;
		current_year = localCalendar.get(Calendar.YEAR);
		final int total_price = Integer.parseInt(price.getText().toString());

		pay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final int balance_price = Integer.parseInt(balance.getText().toString());
				List<Balance_Amount> amountbalance = new ArrayList<Balance_Amount>();
				balance_data = new ArrayList<Integer>();
				amountbalance = balanceamountdao.getDealerName(id);
				if (amountbalance.isEmpty()) {
					if (balance_price > total_price) {
						balance.setError(Html.fromHtml("<font color='black'>Enter valid amount </font>"));
						//CommonUtil.showLoginErrorMessage(DoctorAmountDetails.this);
					} else 
						balanceamountdao.insertDetails(prepare_balance_amount_details(balance_price));
						
					
				} else {

					for (Balance_Amount amount : amountbalance) {
						if (amount.getPerson_id() == id && amount.getMonth() == current_month
								&& amount.getYear() == current_year) 
						{
							calculate_amount(amount.getAmount_paid());
							
							if(balance_price > total_price)
							{
								balance.setError(Html.fromHtml("<font color='black'>Enter valid amount </font>"));
							}
							else
							{
							balanceamount.setId(amount.getId());
							balanceamount.setMonth(amount.getMonth());
							balanceamount.setYear(amount.getYear());
							balanceamount.setPerson_id(amount.getPerson_id());
							long result = balanceamountdao.updateAmount(balanceamount);
							if (result < 0) {
								Toast.makeText(getApplicationContext(), "Error while updating!!", Toast.LENGTH_SHORT)
								.show();
								return;
							} else
								CommonUtil.showUpdateMessage(DoctorAmountDetails.this);
							}
						} else {
							balanceamountdao.insertDetails(prepare_balance_amount_details(balance_price));
						}
					}
				}
			}
		});
	}

	private Balance_Amount prepare_balance_amount_details(int balance_price2) {
		Balance_Amount balance_Amount = new Balance_Amount();
		balance_Amount.setPerson_id(id);
		balance_Amount.setMonth(current_month);
		balance_Amount.setYear(current_year);
		balance_Amount.setAmount_paid(balance_price2);
		CommonUtil.showMessage(DoctorAmountDetails.this);
		return balance_Amount;
	}

	private void calculate_amount(int i) {
		int balance_price = Integer.parseInt(balance.getText().toString());
//		if (balance_price > i) {
//			balance.setError(Html.fromHtml("<font color='black'>Enter valid amount </font>"));
//		} else {
			total = i + balance_price;
			balanceamount.setAmount_paid(total);
	//	}
	}
}
