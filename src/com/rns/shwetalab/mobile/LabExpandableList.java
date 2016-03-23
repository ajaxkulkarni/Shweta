package com.rns.shwetalab.mobile;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
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
import com.rns.shwetalab.mobile.db.CommonUtil;
import com.rns.shwetalab.mobile.db.JobWorkTypeMapDao;
import com.rns.shwetalab.mobile.db.JobsDao;
import com.rns.shwetalab.mobile.db.WorkPersonMapDao;
import com.rns.shwetalab.mobile.domain.Balance_Amount;
import com.rns.shwetalab.mobile.domain.Job;
import com.rns.shwetalab.mobile.domain.WorkPersonMap;
import com.rns.shwetalab.mobile.domain.WorkType;

public class LabExpandableList extends Activity {

	private LabExpandableListAdapter joblistAdapter;
	private ExpandableListView expListView;
	private List<String> listDataHeader;
	private HashMap<String, List<String>> listDataChild;
	private JobsDao jobsDao;
	private String dateSelected, new_balance;
	TextView date, bal, pay_bal_text;
	private BalanceAmountDao amountDao;
	int id, total;
	ListView objLv;
	ImageView pay, mail;
	String work;
	BigDecimal amount;
	ArrayList<String> objArrayListName = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_job_list);
		pay = (ImageView) findViewById(R.id.lab_addpayment_imageView);
		pay_bal_text = (TextView) findViewById(R.id.lab_add_payment_textView1);
		bal = (TextView) findViewById(R.id.amount_textView2);
		pay = (ImageView) findViewById(R.id.lab_addpayment_imageView);
		mail = (ImageView) findViewById(R.id.labs_mail_imageView1);
		expListView = (ExpandableListView) findViewById(R.id.labjobsexpandableListView);
		jobsDao = new JobsDao(getApplicationContext());
		amountDao = new BalanceAmountDao(getApplicationContext());
		Bundle extras = getIntent().getExtras();
		final String month = extras.getString("Month");
		final String name = extras.getString("Name");
		String type = extras.getString("Type");
		final String price = extras.getString("Price");
		prepareListData(month, name);
		getbalance(price);
		joblistAdapter = new LabExpandableListAdapter(this, jobsDao.getLabJobsByMonth(month, name));
		expListView.setAdapter(joblistAdapter);
		objLv = (ListView) findViewById(R.id.Job_listView);

		pay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(LabExpandableList.this, DoctorAmountDetails.class);
				i.putExtra("Price", price);
				i.putExtra("ID", id);
				i.putExtra("New_Balance", new_balance);
				startActivity(i);
				finish();
			}
		});

		mail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				prepareInvoice(month, name);
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
			new_balance = bal.getText().toString();
		} else {
			total = Integer.parseInt(price) - amountbalance.get(amountbalance.size() - 1).getAmount_paid();
			bal.setText("" + total);
			new_balance = bal.getText().toString();
			if (total == 0) {
				pay.setVisibility(View.GONE);
				pay_bal_text.setVisibility(View.GONE);
			}
		}

	}

	private void prepareInvoice(String month, String name) {
		jobsDao = new JobsDao(this);
		List<Job> jobs = jobsDao.getLabJobsByMonth(month, name);
		listDataHeader = new ArrayList<String>();
		// TODO Auto-generated method stub
		String to = "rajeshmangale0802@gmail.com";
		String subject = "Dental Invoice";
		String message1 = " " + id + " " + work + " " + amount;
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat(" MM-dd-yyyy ");
		StringBuilder body = new StringBuilder();
		String message = " Case Id " + " Worktype " + " Price ";
		body.append("<html>");
		body.append("<body>");
		body.append("<table style = " + "width:100%" + ">");
		body.append("<tr>");
		body.append("<td>" + "Case ID" + "</td>");
		body.append(" <td>" + " Worktype" + "</td>");
		body.append(" <td>" + "Price" + "</td>");
		body.append("</tr>");

		for (Job job : jobs) {
			if (job.getDoctor() == null) {
				continue;
			}
			body.append("<tr>");
			body.append("<td>" + job.getId() + "</td>");
			body.append("<td>" + prepareWorks(job) + "</td>");
			body.append("<td>" + job.getPrice() + "</td>");
			body.append("</tr");

		}

		body.append("</table");
		body.append("</body");
		body.append("</html");

		Intent email = new Intent(Intent.ACTION_SEND);
		email.putExtra(Intent.EXTRA_EMAIL, new String[] { to });
		email.putExtra(Intent.EXTRA_SUBJECT, subject);
		email.putExtra(Intent.EXTRA_TEXT,
				Html.fromHtml(new StringBuilder().append("<p><b>Shweta Dental Laboratory</b></p>")
						.append("<small><p>522, Narayan Peth,Subhadra Co-op.Hsg.Soc,1st Floor,Pune-30</p></small>")
						.append("<small><p>MOBILE.:9764004512 EMAIL-shwetadentallaboratory@gmail.com</p></small>")
						.append("<small><p>To - Shweta Dental Laboratory</p></small>")
						.append("<small><p></p></small>" + ft.format(dNow)).append("<p></p>" + body).toString()));
		// email.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(new
		// StringBuilder().append("<p></p>"+body).toString()));

		email.setType("message/rfc822");
		startActivity(Intent.createChooser(email, "Select Email Client"));
	}

	private String prepareWorks(Job job) 
	{
		int count = 0;
		StringBuilder builder = new StringBuilder();
		WorkPersonMap personMap = new WorkPersonMap();
		WorkPersonMapDao workPersonMapDao = new WorkPersonMapDao(LabExpandableList.this);
		JobWorkTypeMapDao jobWorkTypeMapDao = new JobWorkTypeMapDao(LabExpandableList.this);
		if (job.getDoctor().getWorkType().equals(CommonUtil.TYPE_LAB)) {
			for (WorkType workType : job.getWorkTypes())

			{
				personMap.setPerson(job.getDoctor());
				personMap.setWorkType(workType);
				WorkPersonMap a = workPersonMapDao.getWorkPersonMap(personMap);
				if(a!=null)
				{
					builder.append(workType.getName()).append(",");
				}
			}

		} 

		return builder.toString();
	}
}
