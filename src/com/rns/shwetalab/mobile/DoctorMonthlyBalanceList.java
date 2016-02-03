package com.rns.shwetalab.mobile;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.rns.shwetalab.mobile.adapter.DoctorListAdapter;
import com.rns.shwetalab.mobile.db.JobsDao;
import com.rns.shwetalab.mobile.domain.Job;

public class DoctorMonthlyBalanceList extends Activity {

	private JobsDao jobsDao;
	ListView lv;
	Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctor__list__amount);
		Bundle extras = getIntent().getExtras();
		String month = extras.getString("Month");
		prepareListData(month);
		lv = (ListView) findViewById(R.id.doctor_listView);
		DoctorListAdapter Adapter = new DoctorListAdapter(this, map);
		lv.setAdapter(Adapter);

	}

	private void prepareListData(String month) {
		String personType = getIntent().getStringExtra("type");
		jobsDao = new JobsDao(this);
		List<Job> jobs = jobsDao.getJobsByMonth(month);
		BigDecimal total = BigDecimal.ZERO;
		for (Job job : jobs) {
			if (job.getDoctor() == null || job.getWorkTypes() == null || job.getPrice() == null || !personType.equals(job.getDoctor().getWorkType())) {
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
}
