package com.rns.shwetalab.mobile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rns.shwetalab.mobile.adapter.DoctorListAdapter;
import com.rns.shwetalab.mobile.adapter.JobListAdapter;
import com.rns.shwetalab.mobile.db.CommonUtil;
import com.rns.shwetalab.mobile.db.JobsDao;
import com.rns.shwetalab.mobile.db.PersonDao;
import com.rns.shwetalab.mobile.domain.Job;
import com.rns.shwetalab.mobile.domain.Person;
import com.rns.shwetalab.mobile.domain.WorkType;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class Doctor_List_Amount extends Activity {

	private List<String> listDataHeader;
	private JobsDao jobsDao;
	private PersonDao personDao;
	private WorkType workType;

	ListView lv;
	private ArrayList<String> objArrayListDoctorName = new ArrayList<String>();
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

		jobsDao = new JobsDao(this);
		List<Job> jobs = jobsDao.getJobsByMonth(month);
		BigDecimal total = BigDecimal.ZERO;
		// for(Job job: jobs)
		Job job2;
		for (int i = 0; i < jobs.size(); i++) {
			Job job = new Job();
			job = jobs.get(i);
			total = BigDecimal.ZERO;
			total = job.getWorkType().getDefaultPrice();
			for (int j = i + 1; j < jobs.size(); j++) {
				job2 = new Job();
				job2 = jobs.get(j);
				if (job.getDoctor().getName().equals(job2.getDoctor().getName()) == true) 
				{
					if (job2.getWorkPersonMap().getPrice() != null) {
						total = total.add(job2.getWorkType().getDefaultPrice());
						// job2.getDoctor().setName("no");
					} else
						job2.getWorkPersonMap().setPrice(workType.getDefaultPrice());
					total = total.add(job2.getPrice());
				}
			}
			map.put(job.getDoctor().getName(), total);
		}
	}
}
