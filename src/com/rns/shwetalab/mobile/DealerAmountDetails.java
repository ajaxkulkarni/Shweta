package com.rns.shwetalab.mobile;

import java.math.BigDecimal;
import java.util.List;

import com.google.gson.Gson;
import com.rns.shwetalab.mobile.db.CommonUtil;
import com.rns.shwetalab.mobile.db.DealerDao;
import com.rns.shwetalab.mobile.domain.Dealer;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DealerAmountDetails extends Activity {
	private EditText material_price, amount_paid, balance_amount;
	private Dealer dealer;
	private DealerDao dealerDao;
	private BigDecimal bp, mp, ap, total;
	BigDecimal balance;
	private Button pay;
	private int result;
	private TextView name;
	BigDecimal balance_left;
	BigDecimal bal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dealer_amount_details);
		name = (TextView) findViewById(R.id.nametextView);
		material_price = (EditText) findViewById(R.id.material_amount_editText);
		amount_paid = (EditText) findViewById(R.id.material_amount_paid_editText);
		balance_amount = (EditText) findViewById(R.id.material_balance_amount_editText);
		pay = (Button) findViewById(R.id.pay_balance_button1);

		material_price.setEnabled(false);
		amount_paid.setEnabled(false);

		dealerDao = new DealerDao(getApplicationContext());
		Bundle extras = getIntent().getExtras();
		String names = extras.getString("Name");
		String data = extras.getString("Data");
		dealer = new Gson().fromJson(data, Dealer.class);
		prepareDetails(names);
		pay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				prepareNewAmount();
				bp = new BigDecimal(balance_amount.getText().toString());
				ap = new BigDecimal(amount_paid.getText().toString());
				mp = new BigDecimal(material_price.getText().toString());
				BigDecimal amountpaid = mp.subtract(ap);
				result = bp.compareTo(amountpaid);
				if (result == 1) {
					balance_amount.setError(Html.fromHtml("<font color='black'>Enter valid amount </font>"));
				} else {
					long result = dealerDao.updateAmount(dealer);
					if (result < 0) {
						Toast.makeText(getApplicationContext(), "Error while updating!!", Toast.LENGTH_SHORT).show();
						return;
					} else
						CommonUtil.showUpdateMessage(DealerAmountDetails.this);
				}
			}
		});
	}

	private void prepareDetails(String names) {
		DealerDao dealerDao = new DealerDao(this);
		List<Dealer> dealers = dealerDao.getDealerName(names);
		for (Dealer dealers1 : dealers)
			if (dealers1.getName() != null) {
				material_price.setText(dealers1.getPrice().toString());
				amount_paid.setText(dealers1.getAmount_paid().toString());
				mp = new BigDecimal(material_price.getText().toString());
				ap = new BigDecimal(amount_paid.getText().toString());
				name.setText(dealers1.getName().toString().toUpperCase());
				result = mp.compareTo(ap);
				if (result == 0) {
					balance_amount.setText("No Balance Amount");
					pay.setVisibility(View.GONE);
				} else if (result == 1) {
					total = mp.subtract(ap);
					balance_amount.setText(total.toString());
				}
			}
	}

	private void prepareNewAmount() {
		if (dealer != null) {
			bp = new BigDecimal(balance_amount.getText().toString());
			total = bp.add(new BigDecimal(amount_paid.getText().toString()));
			balance = new BigDecimal(balance_amount.getText().toString());
			bal = new BigDecimal(material_price.getText().toString()).subtract(total);

			dealer.setAmount_paid(total);
			dealer.setBalance(bal);
		} else
			Toast.makeText(getApplicationContext(), "Dealer not Available!!", Toast.LENGTH_SHORT).show();
	}
}
