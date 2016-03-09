package com.rns.shwetalab.mobile;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import com.google.gson.Gson;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter;
import com.rns.shwetalab.mobile.adapter.JobsExpandableListViewAdapter;
import com.rns.shwetalab.mobile.db.BalanceAmountDao;
import com.rns.shwetalab.mobile.db.JobsDao;
import com.rns.shwetalab.mobile.domain.Balance_Amount;
import com.rns.shwetalab.mobile.domain.Job;
import com.rns.shwetalab.mobile.domain.WorkType;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class JobsExpandableListView extends Activity {
	private JobsExpandableListViewAdapter joblistAdapter;
	private ExpandableListView expListView;
	private List<String> listDataHeader;
	private HashMap<String, List<String>> listDataChild;
	private JobsDao jobsDao;
	private String dateSelected;
	List<Integer> balance_data;
	private BalanceAmountDao amountDao;
	TextView date, bal;
	ImageView pay;
	int id = 0;
	// final Calendar localCalendar =
	// Calendar.getInstance(TimeZone.getDefault());
	// Balance_Amount balanceamount;
	int total, current_month, current_year;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jobs_expandable_list_view);
		expListView = (ExpandableListView) findViewById(R.id.jobsexpandableListView);
		
		final Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
		amountDao = new BalanceAmountDao(getApplicationContext());
		jobsDao = new JobsDao(getApplicationContext());
		Bundle extras = getIntent().getExtras();
		String month = extras.getString("Month");
		String name = extras.getString("Name");
		String type = extras.getString("Type");
		final String price = extras.getString("Price");
		prepareListData(month, name);
		getbalance(price);
		current_month = localCalendar.get(Calendar.MONTH) + 1;
		current_year = localCalendar.get(Calendar.YEAR);
		// if(type==CommonUtil.TYPE_DOCTOR)

		pay = (ImageView) findViewById(R.id.addpayment_imageView);

		pay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(JobsExpandableListView.this, DoctorAmountDetails.class);
				i.putExtra("Price", price);
				i.putExtra("ID", id);
				startActivity(i);
			}
		});

		joblistAdapter = new JobsExpandableListViewAdapter(this, jobsDao.getJobsByMonthName(month, name));
		expListView.setAdapter(joblistAdapter);

		// else
		// {
		// joblistAdapter = new JobsExpandableListViewAdapter(this,
		// jobsDao.getLabJobsByMonth(month,name));
		// expListView.setAdapter(joblistAdapter);
		// }
		expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				return false;
			}
		});

		expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {
			}
		});

		expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
			@Override
			public void onGroupCollapse(int groupPosition) {
			}
		});

		// Listview on child click listener
		expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition,
					long id) {

				return false;

			}
		});

		expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {
			}
		});

		expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
			@Override
			public void onGroupCollapse(int groupPosition) {
			}
		});

		// Listview on child click listener
		expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition,
					long id) {

				return false;
			}
		});

	}

	private void getbalance(String price) 
	{
		bal = (TextView) findViewById(R.id.doctoramountpaid_textView);
		List<Balance_Amount> amountbalance = new ArrayList<Balance_Amount>();
		balance_data = new ArrayList<Integer>();
		amountbalance = amountDao.getDealerName(id);
		if (amountbalance.isEmpty()) {
			bal.setText(price);
			// balanceamountdao.insertDetails(prepare_balance_amount_details());
		} else 
		{
			for (Balance_Amount amount : amountbalance) 
			{
				if (amount.getPerson_id() == id) 
				{
			//		bal.setText(amount.getAmount_paid());
			//		bal.setText(amount.getAmount_paid());
				} else
					bal.setText(price);
			}
		}
	}

	private void prepareListData(String month, String name) {
		jobsDao = new JobsDao(this);
		List<Job> jobs = jobsDao.getJobsByMonthName(month, name);
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();
		for (Job job : jobs) {
			if (job.getDoctor() == null) {
				continue;
			}
			id = job.getDoctor().getId();
		}
	}

}