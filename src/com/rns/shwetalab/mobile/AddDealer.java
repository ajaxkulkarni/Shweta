package com.rns.shwetalab.mobile;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.rns.shwetalab.mobile.db.CommonUtil;
import com.rns.shwetalab.mobile.db.DealerDao;
import com.rns.shwetalab.mobile.db.PersonDao;
import com.rns.shwetalab.mobile.domain.Dealer;
import com.rns.shwetalab.mobile.domain.Job;
import com.rns.shwetalab.mobile.domain.Person;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class AddDealer extends Activity implements OnClickListener, OnItemSelectedListener {

	private DatePickerDialog toDatePickerDialog;
	EditText material, price, amount_paid, date;
	Button add;
	private PersonDao personDao;
	AutoCompleteTextView dealername;
	Dealer dealer;
	private SimpleDateFormat dateFormatter;
	DealerDao dealerDao;
	BigDecimal ap, mp;
	int result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_dealer);
		dateFormatter = new SimpleDateFormat(CommonUtil.DATE_FORMAT, Locale.US);
		date = (EditText) findViewById(R.id.dateeditText);

		material = (EditText) findViewById(R.id.dealermaterialeditText);
		price = (EditText) findViewById(R.id.dealerpriceeditText);
		amount_paid = (EditText) findViewById(R.id.dealerpaideditText);
		personDao = new PersonDao(getApplicationContext());
		dealerDao = new DealerDao(getApplicationContext());
		add = (Button) findViewById(R.id.dealerAddbutton);
		setDateTimeField();
		prepareDealerNames();
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dealerDao.insertDetails(prepareMaterial());
				CommonUtil.showMessage(AddDealer.this);
			}
		});
	}

	private void setDateTimeField() {

		SimpleDateFormat dfDate = new SimpleDateFormat("dd-M-yyyy");
		Calendar c = Calendar.getInstance();
		String date1 = dfDate.format(c.getTime());
		date.setText(date1);

		date.setOnClickListener(this);

		Calendar newCalendar = Calendar.getInstance();
		toDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				Calendar newDate = Calendar.getInstance();
				newDate.set(year, monthOfYear, dayOfMonth);
				date.setText(dateFormatter.format(newDate.getTime()));
			}
		}, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

	}

	private Dealer prepareMaterial() {
		Dealer dealer = new Dealer();
		dealer.setDate(CommonUtil.convertDate(date.getText().toString()));
		dealer.setMaterial(material.getText().toString());
		dealer.setPrice(new BigDecimal(price.getText().toString()));
		dealer.setAmount_paid(new BigDecimal(amount_paid.getText().toString()));
		dealer.setName(dealername.getText().toString());
		Person dealers = new Person();
		dealers.setName(dealername.getText().toString());
		dealer.setDealer(dealers);

		return dealer;

	}

	private void prepareDealerNames() {
		dealername = (AutoCompleteTextView) findViewById(R.id.dealernameeditText);
		String type = CommonUtil.TYPE_DEALER;
		ArrayAdapter<String> dealerNames = new ArrayAdapter<String>(AddDealer.this,
				android.R.layout.simple_dropdown_item_1line, personDao.getDoctorNames(type));
		dealername.setThreshold(1);
		dealername.setAdapter(dealerNames);
	}

	private void validate() {
		if (TextUtils.isEmpty(dealername.getText())) {
			dealername.setError(Html.fromHtml("<font color='black'>Enter Name</font>"));
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

	@Override
	public void onClick(View v) {
		toDatePickerDialog.show();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}
}
