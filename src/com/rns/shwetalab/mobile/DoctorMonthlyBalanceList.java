package com.rns.shwetalab.mobile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.rns.shwetalab.mobile.adapter.DoctorListAdapter;
import com.rns.shwetalab.mobile.db.CommonUtil;
import com.rns.shwetalab.mobile.db.JobLabMapDao;
import com.rns.shwetalab.mobile.db.JobsDao;
import com.rns.shwetalab.mobile.db.PersonDao;
import com.rns.shwetalab.mobile.domain.Job;
import com.rns.shwetalab.mobile.domain.Person;

public class DoctorMonthlyBalanceList extends Activity {

	private JobsDao jobsDao;
	private ListView lv;
	private Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();

	private JobLabMapDao JobLabMapDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctor__list__amount);
		Bundle extras = getIntent().getExtras();
		final String month = extras.getString("Month");
		prepareListData(month);
		lv = (ListView) findViewById(R.id.doctor_listView);
		DoctorListAdapter Adapter = new DoctorListAdapter(this, map);
		lv.setAdapter(Adapter);

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//				Intent i = new Intent(DoctorMonthlyBalanceList.this, JobsExpandableListView.class);
//					String name = ((TextView) view.findViewById(R.id.doctorListName_textView)).getText().toString();
//				i.putExtra("Month", month);
//				
//				//	i.putExtra("Name", name);
//				i.putExtra("Id", id);
//			//	i.putExtra("Id",person_id);
//				startActivity(i);

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
		BigDecimal total = BigDecimal.ZERO;
		for (Job job : jobs) {
			if (job.getDoctor() == null || job.getPrice() == null
					|| !personType.equals(job.getDoctor().getWorkType())) {
				continue;
			}
			if (map.get(job.getDoctor().getName()) == null) {
				map.put(job.getDoctor().getName(), job.getPrice());
			} else {
				total = map.get(job.getDoctor().getName());
				if (total == null) {
					continue;
				}
				map.put(job.getDoctor().getName(), total.add(job.getPrice()));
			}
		}
	}

	private Object getLabJobsPrice(List<Job> jobs) {
		BigDecimal total = BigDecimal.ZERO;
		for (Job job : jobs) {
			List<Job> labJobs = JobLabMapDao.getLabJobsForJob(job);
			for (Job labJob : labJobs) {
				if (labJob.getPrice() == null) {
					continue;
				}
				total = total.add(labJob.getPrice());
			}
		}
		return total;
	}
}
