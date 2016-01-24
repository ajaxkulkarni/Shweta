package com.rns.shwetalab.mobile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.rns.shwetalab.mobile.db.PersonDao;
import com.rns.shwetalab.mobile.db.WorkPersonMapDao;
import com.rns.shwetalab.mobile.db.WorkTypeDao;
import com.rns.shwetalab.mobile.domain.Person;
import com.rns.shwetalab.mobile.domain.WorkPersonMap;
import com.rns.shwetalab.mobile.domain.WorkType;

public class AdminEditWorkTypeActivity extends Activity {

	private EditText workTypeEditText, defaultprice;
	private Button addWorkTypeButton;
	private WorkTypeDao workTypeDao;
	private WorkType work;
	private PersonDao personDao;
	private RadioButton lab, doctor;
	private WorkPersonMapDao workpersonMapDao;
	private ListView personsListView, labsListView;
	private List<WorkPersonMap> workPersonMaps;
	private AdminEditWorkTypeAdapter personListAdapter;

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
		personsListView = (ListView) findViewById(R.id.editworktypedoctorlistView);
		labsListView = (ListView) findViewById(R.id.editworktypelablistView);
		preparePersonMaps();
		addWorkTypeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				prepareWorkType();
				long result = workTypeDao.updateWorkType(work);
				if (result < 0) {
					Toast.makeText(getApplicationContext(), "Error while updating!!", Toast.LENGTH_SHORT).show();
					return;
				}
				List<WorkPersonMap> workPersonMapList = prepareWorkPersonMapsList();
				if (workPersonMapList == null || workPersonMapList.size() == 0) {
					CommonUtil.showUpdateMessage(AdminEditWorkTypeActivity.this);
					return;
				}
				result = workpersonMapDao.updateWorkPersonMaps(workPersonMapList);
				if (result < 0) {
					Toast.makeText(getApplicationContext(), "Error while updating!!", Toast.LENGTH_SHORT).show();
					return;
				}
				CommonUtil.showUpdateMessage(AdminEditWorkTypeActivity.this);
			}
		});

		doctor.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				lab.setChecked(false);
				preparePersonMaps();
			}
		});

		lab.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doctor.setChecked(false);
				preparePersonMaps();
			}
		});
	}

	private void preparePersonMaps() {
		prepareWorkPersonMaps();
		personListAdapter = new AdminEditWorkTypeAdapter(this, workPersonMaps);
		personsListView.setAdapter(personListAdapter);
	}

	private void prepareWorkPersonMaps() {
		Bundle extras = getIntent().getExtras();
		String data = extras.getString("Data");
		work = new Gson().fromJson(data, WorkType.class);
		workTypeEditText.setText(work.getName());
		defaultprice.setText(work.getDefaultPrice().toString());
		workPersonMaps = workpersonMapDao.getMapsForWorkType(work, getPersonType());
		List<Person> persons = getPersonsByType();
		for (Person person : persons) {
			if (isPersonPresent(person)) {
				continue;
			}
			WorkPersonMap map = new WorkPersonMap();
			map.setPerson(person);
			map.setWorkType(work);
			workPersonMaps.add(map);
		}
	}
	
	private String getPersonType() {
		if(lab.isChecked()) {
			return CommonUtil.TYPE_LAB;
		}
		return CommonUtil.TYPE_DOCTOR;
	}

	private List<Person> getPersonsByType() {
		List<Person> persons = new ArrayList<Person>();
		if (lab.isChecked()) {
			persons = personDao.getAllPeopleByType(CommonUtil.TYPE_LAB);
		} else {
			persons = personDao.getAllPeopleByType(CommonUtil.TYPE_DOCTOR);
		}
		return persons;
	}

	private boolean isPersonPresent(Person person) {
		for (WorkPersonMap map : workPersonMaps) {
			if(map.getPerson() == null) {
				return false;
			}
			if (map.getPerson().getId() == person.getId()) {
				return true;
			}
		}
		return false;
	}

	private List<WorkPersonMap> prepareWorkPersonMapsList() {
		List<WorkPersonMap> maps = new ArrayList<WorkPersonMap>();
		View v;
		List<Person> doctors = getPersonsByType();
		if (doctors == null || doctors.size() == 0) {
			return null;
		}
		for (int i = 0; i < doctors.size(); i++) {
			WorkPersonMap map = (WorkPersonMap) personsListView.getAdapter().getItem(i);
			v = personsListView.getAdapter().getView(i, null, null);
			v = personsListView.getChildAt(i);
			EditText editText = (EditText) v.findViewById(R.id.editwork_type_doctorlist_adapter_editText);
			if (!TextUtils.isEmpty(editText.getText().toString())) {
				map.setPrice(new BigDecimal(editText.getText().toString()));
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
}
