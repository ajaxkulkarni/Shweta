package com.rns.shwetalab.mobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.rns.shwetalab.mobile.adapter.LabExpandableListAdapter;
import com.rns.shwetalab.mobile.adapter.JobsExpandableListViewAdapter;
import com.rns.shwetalab.mobile.db.BalanceAmountDao;
import com.rns.shwetalab.mobile.db.JobsDao;
import com.rns.shwetalab.mobile.domain.Balance_Amount;
import com.rns.shwetalab.mobile.domain.Job;

public class LabExpandableList extends Activity {

	private LabExpandableListAdapter joblistAdapter;
	private ExpandableListView expListView;
	private List<String> listDataHeader;
	private HashMap<String, List<String>> listDataChild;
	private JobsDao jobsDao;
	private String dateSelected;
	TextView date, bal, pay_bal_text;
	private BalanceAmountDao amountDao;
	int id, total;
	ListView objLv;
	ImageView pay;
	ArrayList<String> objArrayListName = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_job_list);
		pay = (ImageView) findViewById(R.id.lab_addpayment_imageView);
		pay_bal_text = (TextView) findViewById(R.id.lab_add_payment_textView1);
		bal = (TextView) findViewById(R.id.amount_textView2);
		expListView = (ExpandableListView) findViewById(R.id.labjobsexpandableListView);
		jobsDao = new JobsDao(getApplicationContext());
		amountDao = new BalanceAmountDao(getApplicationContext());
		Bundle extras = getIntent().getExtras();
		String month = extras.getString("Month");
		String name = extras.getString("Name");
		String type = extras.getString("Type");
		final String price = extras.getString("Price");
		prepareListData(month, name);
		getbalance(price);
		joblistAdapter = new LabExpandableListAdapter(this, jobsDao.getLabJobsByMonth(month, name));
		expListView.setAdapter(joblistAdapter);

		objLv = (ListView) findViewById(R.id.Job_listView);

		// LabExpandableListAdapter Adapter = new LabExpandableListAdapter(this,
		// jobsDao.getJobsByMonthName(month,name));
		// objLv.setAdapter(Adapter);
		pay = (ImageView) findViewById(R.id.lab_addpayment_imageView);

		pay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// Toast.makeText(JobsExpandableListView.this, "data inserted" ,
				// Toast.LENGTH_SHORT).show();
				Intent i = new Intent(LabExpandableList.this, DoctorAmountDetails.class);

				i.putExtra("Price", price);
				i.putExtra("ID", id);
				// i.putExtra("Data",new Gson().toJson(id));
				startActivity(i);

			}

		});

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

	private void prepareListData(String month, String name) {
		jobsDao = new JobsDao(this);
		List<Job> jobs = jobsDao.getLabJobsByMonth(month, name);
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();
		for (Job job : jobs) {
			if (job.getDoctor() == null) {
				continue;
			}
			id = job.getDoctor().getId();
		}
	}

	private void getbalance(String price) {

		List<Balance_Amount> amountbalance = new ArrayList<Balance_Amount>();
		// balance_data = new ArrayList<Integer>();
		amountbalance = amountDao.getDealerName(id);
		if (amountbalance.isEmpty()) {
			bal.setText("" + price);
		} else
			total = Integer.parseInt(price) - amountbalance.get(amountbalance.size() - 1).getAmount_paid();
		bal.setText("" + total);
		if (total == 0) {
			pay.setVisibility(View.GONE);
			pay_bal_text.setVisibility(View.GONE);
		}
	}

}
