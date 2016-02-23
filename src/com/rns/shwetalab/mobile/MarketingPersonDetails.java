package com.rns.shwetalab.mobile;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.rns.shwetalab.mobile.db.CommonUtil;
import com.rns.shwetalab.mobile.db.DealerDao;
import com.rns.shwetalab.mobile.db.MarketingDao;
import com.rns.shwetalab.mobile.domain.Dealer;
import com.rns.shwetalab.mobile.domain.Marketing;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class MarketingPersonDetails extends Activity implements OnClickListener
{
	private DatePickerDialog toDatePickerDialog;
	private SimpleDateFormat dateFormatter;
	EditText name,email,contact,date,description;;
	Button add;
	Marketing marketing;
	MarketingDao marketingDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_marketing_person_details);
		dateFormatter = new SimpleDateFormat(CommonUtil.DATE_FORMAT, Locale.US);
		name = (EditText)findViewById(R.id.marketing_nameeditText);
		
		date = (EditText)findViewById(R.id.marketdateeditText);
		description = (EditText)findViewById(R.id.descriptioneditText);
		setDateTimeField();
		marketing = new Marketing();
		marketingDao = new MarketingDao(getApplicationContext());
		add = (Button)findViewById(R.id.AddMarketingPersonbutton);
		add.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View v) 
			{
				//preparedealer();
				marketingDao.insertDetails(preparedealer());
				CommonUtil.showMessage(MarketingPersonDetails.this);
			}
		});
	}
	private Marketing preparedealer() 
	{
		marketing.setMarketing_name(name.getText().toString());
		marketing.setDate(CommonUtil.convertDate(date.getText().toString()));
		marketing.setDescription(description.getText().toString()); 
		return marketing;
	}
	
	
	private void setDateTimeField() 
	{
		SimpleDateFormat dfDate  = new SimpleDateFormat("dd-M-yyyy");   
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
	@Override
	public void onClick(View v) 
	{
		toDatePickerDialog.show();		
	}
}
