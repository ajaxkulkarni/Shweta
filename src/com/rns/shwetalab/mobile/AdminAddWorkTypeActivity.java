package com.rns.shwetalab.mobile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.rns.shwetalab.mobile.adapter.AddWorkTypeDoctorListAdapter;
import com.rns.shwetalab.mobile.db.CommonUtil;
import com.rns.shwetalab.mobile.db.PersonDao;
import com.rns.shwetalab.mobile.db.WorkTypeDao;
import com.rns.shwetalab.mobile.domain.Person;
import com.rns.shwetalab.mobile.domain.WorkPersonMap;
import com.rns.shwetalab.mobile.domain.WorkType;

public class AdminAddWorkTypeActivity extends Activity {

	private EditText workTypeEditText;
	private Button addWorkTypeButton;
	private WorkTypeDao workTypeDao;
	private WorkType work;
	private PersonDao personDao;
	private RadioButton lab, doctor;

	private ListView doctorsListView, labsListView;
	private ArrayList<WorkPersonMap> workPersonMaps;
	//private List<String> doctorNames = new ArrayList<String>();
	//private List<String> doctorAmounts = new ArrayList<String>();
	//private List<String> labNames = new ArrayList<String>();
	//private List<String> labAmounts = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_add_work_type);
		personDao = new PersonDao(getApplicationContext());
		workTypeDao = new WorkTypeDao(getApplicationContext());
		workTypeEditText = (EditText) findViewById(R.id.add_worktype_activity_worktype_editText);
		addWorkTypeButton = (Button) findViewById(R.id.add_worktype_activity_worktype_add_button);
		doctor = (RadioButton) findViewById(R.id.addworktypeDoctorradioButton1);
		lab = (RadioButton) findViewById(R.id.addworktypeLabradioButton2);
		doctorsListView = (ListView) findViewById(R.id.addworktypedoctorlistView);
		labsListView = (ListView) findViewById(R.id.addworktypelablistView);
		prepareWorkPersonMaps();
		AddWorkTypeDoctorListAdapter doctorListAdapter = new AddWorkTypeDoctorListAdapter(this,workPersonMaps);
		doctorsListView.setAdapter(doctorListAdapter);

		//AddWorkTypeLabListAdapter labListAdapter = new AddWorkTypeLabListAdapter(this, labNames, labAmounts);
		//objlv2.setAdapter(labListAdapter);

		addWorkTypeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				prepareWorkType();
				workTypeDao.insertDetails(work);
				//prepareWorkPersonMaps();
				workPersonMaps.size();
				Toast.makeText(getApplicationContext(), "Record inserted successfully!", Toast.LENGTH_LONG).show();
			}


		});

		doctor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				lab.setChecked(false);
				labsListView.setVisibility(View.GONE);
				doctorsListView.setVisibility(View.VISIBLE);

			}
		});

		lab.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doctor.setChecked(false);
				doctorsListView.setVisibility(View.GONE);
				labsListView.setVisibility(View.VISIBLE);

			}
		});

	}
	
	/*private void prepareWorkPersonMaps() {
		//for(WorkPersonMap map:objlv1.getAdapter().get)
	}*/

	private void prepareWorkPersonMaps() {
		workPersonMaps = new ArrayList<WorkPersonMap>();
		List<Person> doctors = personDao.getAllPeopleByType(CommonUtil.TYPE_DOCTOR);
		if(doctors == null || doctors.size() == 0) {
			return;
		}
		for(Person doctor:doctors) {
			WorkPersonMap map = new WorkPersonMap();
			map.setPerson(doctor);
			workPersonMaps.add(map);
		}
	}

	private void prepareWorkType() {
		work = new WorkType();
		work.setName(workTypeEditText.getText().toString());
		work.setDefaultPrice(BigDecimal.TEN);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_add_work_type, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
