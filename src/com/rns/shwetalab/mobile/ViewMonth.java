package com.rns.shwetalab.mobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class ViewMonth extends Activity implements OnItemSelectedListener {
	Spinner month;
	Button next;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_month);
		final HashMap<String, String> MYMONTH = months();
		month = (Spinner) findViewById(R.id.month_spinner);
		next = (Button) findViewById(R.id.view_bal_button);
		Map<String, String> sortedMap = sortByValue(MYMONTH);
		final List<String> monthlist = new ArrayList<String>(sortedMap.keySet());
		month.setOnItemSelectedListener(this);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ViewMonth.this, android.R.layout.simple_spinner_item, monthlist);
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		month.setAdapter(arrayAdapter);

		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(ViewMonth.this, BalanceSheet.class);
				// Integer value = MYMONTH.get(month);
				i.putExtra("Month", month.getSelectedItem().toString());
				// i.putExtra("Month",value);
				startActivity(i);
			}
		});

	}

	public static HashMap<String, String> months() {
		final HashMap<String, String> months = new HashMap<String, String>();
		months.put("January", "01");
		months.put("February", "02");
		months.put("March", "03");
		months.put("April", "04");
		months.put("May", "05");
		months.put("June", "06");
		months.put("July", "07");
		months.put("August", "08");
		months.put("September", "09");
		months.put("October", "10");
		months.put("November", "11");
		months.put("December", "12");
		return months;
	}

	public static Map<String, String> sortByValue(HashMap<String, String> mYMONTH) {
		@SuppressWarnings("unchecked")
		Map<String, String> sortedMap = new TreeMap<String, String>(new ValueComparator(mYMONTH));
		sortedMap.putAll(mYMONTH);
		return sortedMap;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		String item = parent.getItemAtPosition(position).toString();
		Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}

}
