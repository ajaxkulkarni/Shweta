package com.rns.shwetalab.mobile;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rns.shwetalab.mobile.adapter.DoctorListAdapter;
import com.rns.shwetalab.mobile.adapter.LabListAdapter;
import com.rns.shwetalab.mobile.db.CommonUtil;
import com.rns.shwetalab.mobile.db.JobLabMapDao;
import com.rns.shwetalab.mobile.db.JobsDao;
import com.rns.shwetalab.mobile.domain.Job;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class LabMonthlyBalanceList extends Activity {


	private JobsDao jobsDao;
	private ListView lv;
	private Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();

	private JobLabMapDao JobLabMapDao;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lab_monthly_balance_list);
		
		Bundle extras = getIntent().getExtras();
		final String month = extras.getString("Month");
		final String type = extras.getString("type");
		prepareListData(month);
		
		lv = (ListView) findViewById(R.id.lab_listView);
		LabListAdapter Adapter = new LabListAdapter(this, map);
		lv.setAdapter(Adapter);

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
			{
				String price = ((TextView)view.findViewById(R.id.doctorListAmount_textView)).getText().toString();
				String name = ((TextView)view.findViewById(R.id.doctorListName_textView)).getText().toString();
				Intent i = new Intent(LabMonthlyBalanceList.this,LabExpandableList.class);
				i.putExtra("Name", name);
				i.putExtra("Month",month);
				i.putExtra("Type", type);
				i.putExtra("Price", price);
				startActivity(i);

			}
		});

	}

	private void prepareListData(String month) {
		String personType = getIntent().getStringExtra("type");
		jobsDao = new JobsDao(this);
		JobLabMapDao = new JobLabMapDao(this);
		List<Job> jobs = jobsDao.getJobsByMonth(month);
		map = new HashMap<String, BigDecimal>();
		if (CommonUtil.TYPE_LAB.equals(personType)) {
			getLabJobsPrice(jobs);
		} else {
			getDoctorsJobPrice(personType, jobs);
		}
	}

	private void getDoctorsJobPrice(String personType, List<Job> jobs) {
		if(jobs == null || jobs.size() == 0) {
			return;
		}
		for (Job job : jobs) {
			if (job.getDoctor() == null || job.getPrice() == null || !personType.equals(job.getDoctor().getWorkType())) {
				continue;
			}
			calculatePrice(job);
		}
	}

	private void calculatePrice(Job job) {
		BigDecimal total = BigDecimal.ZERO;
		if (map.get(job.getDoctor().getName()) == null) {
			map.put(job.getDoctor().getName(), job.getPrice());
		} else {
			total = map.get(job.getDoctor().getName());
			if (total == null) {
				return;
			}
			map.put(job.getDoctor().getName(), total.add(job.getPrice()));
		}
	}

	private void getLabJobsPrice(List<Job> jobs) {
		if(jobs == null || jobs.size() == 0) {
			return;
		}
		for (Job job : jobs) {
			List<Job> labJobs = JobLabMapDao.getLabJobsForJob(job);
			if(labJobs == null || labJobs.size() == 0) {
				continue;
			}
			for (Job labJob : labJobs) {
				if (labJob.getPrice() == null) {
					continue;
				}
				calculatePrice(labJob);
			}
		}	
		
	}
}
