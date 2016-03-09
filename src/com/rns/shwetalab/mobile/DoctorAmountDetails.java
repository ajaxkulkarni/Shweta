package com.rns.shwetalab.mobile;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import com.google.gson.Gson;
import com.rns.shwetalab.mobile.db.BalanceAmountDao;
import com.rns.shwetalab.mobile.db.CommonUtil;
import com.rns.shwetalab.mobile.domain.Balance_Amount;
import com.rns.shwetalab.mobile.domain.Dealer;
import com.rns.shwetalab.mobile.domain.Job;

import android.app.Activity;
import android.os.Bundle;
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
		final String amount = extras.getString("Price");
		id = extras.getInt("ID");
		price.setText(amount);
		balanceamount = new Balance_Amount();
		current_month = localCalendar.get(Calendar.MONTH) + 1;
		current_year = localCalendar.get(Calendar.YEAR);

		pay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				List<Balance_Amount> amountbalance = new ArrayList<Balance_Amount>();
				balance_data = new ArrayList<Integer>();
				amountbalance = balanceamountdao.getDealerName(id);
				if (amountbalance.isEmpty()) {
					balanceamountdao.insertDetails(prepare_balance_amount_details());
				} else {
					for (Balance_Amount amount : amountbalance) {
						if (amount.getPerson_id() == id && amount.getMonth() == current_month
								&& amount.getYear() == current_year) {
							calculate_amount(amount.getAmount_paid());
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
								CommonUtil.showMessage(DoctorAmountDetails.this);
							Toast.makeText(DoctorAmountDetails.this, "Amount Updated Successfully", Toast.LENGTH_SHORT)
									.show();
						}

						else {
							// break;
							balanceamountdao.insertDetails(prepare_balance_amount_details());
						}
					}
				}
			}
		});

	}

	private Balance_Amount prepare_balance_amount_details() {
		Balance_Amount balance_Amount = new Balance_Amount();
		int balance_price = Integer.parseInt(balance.getText().toString());
		balance_Amount.setPerson_id(id);
		// if(balance_Amount.getAmount_paid()!=0)
		// {
		balance_Amount.setMonth(current_month);
		balance_Amount.setYear(current_year);
		// List<Balance_Amount> amountbalance = new ArrayList<Balance_Amount>();
		// balance_data = new ArrayList<Integer>();
		// amountbalance = balanceamountdao.getDealerName(id);
		//
		// for (Balance_Amount amount : amountbalance)
		// {
		// if(amount.getAmount_paid() !=0)
		// {
		// balance_Amount.setAmount_paid(amount.getAmount_paid());
		// }
		// else
		balance_Amount.setAmount_paid(balance_price);
		// }
		return balance_Amount;
	}

	private void calculate_amount(int i) {
		// Balance_Amount balance_Amount = new Balance_Amount();
		int balance_price = Integer.parseInt(balance.getText().toString());
		total = i + balance_price;
		balanceamount.setAmount_paid(total);
	}
}
