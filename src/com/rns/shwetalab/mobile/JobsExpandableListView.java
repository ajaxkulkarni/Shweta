package com.rns.shwetalab.mobile;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import android.text.Html;
import android.util.Log;
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
	private String dateSelected, new_balance;
	List<Integer> balance_data;
	private BalanceAmountDao amountDao;
	public TextView date, bal, pay_bal_text;
	ImageView pay, mail;
	int id = 0;
	String work;
	BigDecimal amount;
	int a, bal_price;

	int total, current_month, current_year;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jobs_expandable_list_view);
		expListView = (ExpandableListView) findViewById(R.id.jobsexpandableListView);
		bal = (TextView) findViewById(R.id.paid_textView);
		pay = (ImageView) findViewById(R.id.addpayment_imageView);
		mail = (ImageView) findViewById(R.id.mail_imageView1);
		pay_bal_text = (TextView) findViewById(R.id.doctor_pay_balance_textView1);
		final Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
		amountDao = new BalanceAmountDao(getApplicationContext());
		jobsDao = new JobsDao(getApplicationContext());
		Bundle extras = getIntent().getExtras();
		final String month = extras.getString("Month");
		final String name = extras.getString("Name");
		String type = extras.getString("Type");
		final String price = extras.getString("Price");
		prepareListData(month, name);
		getbalance(price);

		current_month = localCalendar.get(Calendar.MONTH) + 1;
		current_year = localCalendar.get(Calendar.YEAR);
		// if(type==CommonUtil.TYPE_DOCTOR)
		// bal.setText("HEllo");

		pay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(JobsExpandableListView.this, DoctorAmountDetails.class);
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

				// email.putExtra(Intent.EXTRA_TEXT, message);
				// email.putExtra(Intent.EXTRA_TEXT, message1);

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

	private void prepareInvoice(String month, String name) {
		jobsDao = new JobsDao(this);
		List<Job> jobs = jobsDao.getJobsByMonthName(month, name);
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
		body.append("<table style = "+"width:100%"+">");
		body.append("<tr>");
		body.append("<td>" +"Case ID" +"</td>");
		body.append(" <td>" +  " Worktype" + "</td>");
		body.append(" <td>" +  "Price" + "</td>");
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
			work = prepareWorks(job);
			amount = job.getPrice();

		}
	}

	private String prepareWorks(Job job) {
		if (job.getWorkTypes() == null || job.getWorkTypes().size() == 0) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		for (WorkType workType : job.getWorkTypes()) {
			builder.append(workType.getName()).append(",");
		}
		builder.replace(builder.lastIndexOf(","), builder.lastIndexOf(","), "");
		return builder.toString();
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
}