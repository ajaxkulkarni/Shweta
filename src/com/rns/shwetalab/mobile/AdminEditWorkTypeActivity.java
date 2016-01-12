package com.rns.shwetalab.mobile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rns.shwetalab.mobile.adapter.AdminEditWorkTypeAdapter;
import com.rns.shwetalab.mobile.db.CommonUtil;
import com.rns.shwetalab.mobile.db.JobsDao;
import com.rns.shwetalab.mobile.db.PersonDao;
import com.rns.shwetalab.mobile.db.WorkPersonMapDao;
import com.rns.shwetalab.mobile.db.WorkTypeDao;
import com.rns.shwetalab.mobile.domain.Job;
import com.rns.shwetalab.mobile.domain.Person;
import com.rns.shwetalab.mobile.domain.WorkPersonMap;
import com.rns.shwetalab.mobile.domain.WorkType;

public class AdminEditWorkTypeActivity extends Activity {

	private EditText workTypeEditText, defaultprice;
	private Button addWorkTypeButton;
	private WorkTypeDao workTypeDao;
	private WorkType work;
	private JobsDao jobsDao;
	private Job job;
	private WorkPersonMap workPersonMap;
	private PersonDao personDao;
	private RadioButton lab, doctor;
	private WorkPersonMapDao workpersonMapDao;
	private ListView doctorsListView, labsListView;
	private List<WorkPersonMap> workPersonMaps;
	private AdminEditWorkTypeAdapter doctorListAdapter;
	private List<String> doctorAmounts = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_edit_work_type);
		personDao = new PersonDao(getApplicationContext());

		workTypeDao = new WorkTypeDao(getApplicationContext());
		workpersonMapDao = new WorkPersonMapDao(getApplicationContext());
		defaultprice = (EditText) findViewById(R.id.edit_worktype_activity_defaultamount_editText);
		workTypeEditText = (EditText) findViewById(R.id.edit_worktype_activity_worktype_editText);
		addWorkTypeButton = (Button) findViewById(R.id.edit_worktype_activity_worktype_add_button);
		doctor = (RadioButton) findViewById(R.id.editworktypeDoctorradioButton1);
		lab = (RadioButton) findViewById(R.id.editworktypeLabradioButton2);
		doctorsListView = (ListView) findViewById(R.id.editworktypedoctorlistView);
		labsListView = (ListView) findViewById(R.id.editworktypelablistView);
		defaultprice.setText("100");

		prepareWorkPersonMaps();

		doctorListAdapter = new AdminEditWorkTypeAdapter(this, workPersonMaps, doctorAmounts);
		doctorsListView.setAdapter(doctorListAdapter);

		addWorkTypeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				prepareWorkType();
				// prepareWorkPersonMaps();
				// workTypeDao.insertDetails(work);
				long result = workTypeDao.updateWorkType(work);
				if (result < 0) {
					Toast.makeText(getApplicationContext(), "Error while updating!!", Toast.LENGTH_SHORT).show();
					return;
				}
				List<WorkPersonMap> workPersonMapList = prepareWorkPersonMapsList();
				if (workPersonMapList == null || workPersonMapList.size() == 0) {
					Toast.makeText(getApplicationContext(), "Updated Successfully!!", Toast.LENGTH_SHORT).show();
					return;
				}
				result = workpersonMapDao.updateWorkPersonMaps(workPersonMapList);
				if (result < 0) {
					Toast.makeText(getApplicationContext(), "Error while updating!!", Toast.LENGTH_SHORT).show();
					return;
				}
				Toast.makeText(getApplicationContext(), "Updated Successfully!!", Toast.LENGTH_SHORT).show();
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

	private void prepareWorkPersonMaps() {

		Bundle extras = getIntent().getExtras();

		String data = extras.getString("Data");
		work = new Gson().fromJson(data, WorkType.class);
		workTypeEditText.setText(work.getName());
		defaultprice.setText(work.getDefaultPrice().toString());

		workPersonMaps = workpersonMapDao.getMapsForWorkType(work);

		// Add remaining doctors

		List<Person> doctors = personDao.getAllPeopleByType(CommonUtil.TYPE_DOCTOR);
		for (Person doctor : doctors) {
			if(isDoctorPresent(doctor)) {
				continue;
			}
			WorkPersonMap map = new WorkPersonMap();
			map.setPerson(doctor);
			map.setWorkType(work);
			workPersonMaps.add(map);
		}

		// TODO: Fill Doctor Amounts array from this list

	}

	private boolean isDoctorPresent(Person doctor) {
		for (WorkPersonMap map : workPersonMaps) {
			if(map.getPerson() != null && map.getPerson().getId() == doctor.getId()) {
				return true;
			}
		}
		return false;
	}

	private List<WorkPersonMap> prepareWorkPersonMapsList() {
		List<WorkPersonMap> maps = new ArrayList<WorkPersonMap>();
		View v;
		List<Person> doctors = personDao.getAllPeopleByType(CommonUtil.TYPE_DOCTOR);
		if (doctors == null || doctors.size() == 0) {
			return null;
		}
		for (int i = 0; i < doctors.size(); i++) {
			v = doctorsListView.getAdapter().getView(i, null, null);
			WorkPersonMap map = (WorkPersonMap) doctorsListView.getAdapter().getItem(i);
			if (map == null) {
				continue;
			}
			EditText editText = (EditText) v.findViewById(R.id.editwork_type_doctorlist_adapter_editText);
			if (!TextUtils.isEmpty(editText.getText())) {
				// workPersonMap.setPerson(doctors.get(i));
				map.setPrice(new BigDecimal(editText.getText().toString()));
				// workPersonMap.setWorkType(work);
			}
			maps.add(map);
		}
		return maps;
	}

	private void prepareWorkType() {
		work.setName(workTypeEditText.getText().toString());
		if (!TextUtils.isEmpty(defaultprice.getText())) {
			work.setDefaultPrice(new BigDecimal(defaultprice.getText().toString()));
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_add_work_type, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
