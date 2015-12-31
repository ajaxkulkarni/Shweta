package com.rns.shwetalab.mobile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

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
import android.widget.TextView;
import android.widget.Toast;

import com.rns.shwetalab.mobile.adapter.AddWorkTypeDoctorListAdapter;
import com.rns.shwetalab.mobile.db.CommonUtil;
import com.rns.shwetalab.mobile.db.PersonDao;
import com.rns.shwetalab.mobile.db.WorkPersonMapDao;
import com.rns.shwetalab.mobile.db.WorkTypeDao;
import com.rns.shwetalab.mobile.domain.Person;
import com.rns.shwetalab.mobile.domain.WorkPersonMap;
import com.rns.shwetalab.mobile.domain.WorkType;

public class AdminAddWorkTypeActivity extends Activity {

	private EditText workTypeEditText, defaultprice;
	private Button addWorkTypeButton;
	private WorkTypeDao workTypeDao;
	private WorkType work;
	private WorkPersonMap workPersonMap;
	private PersonDao personDao;
	private RadioButton lab, doctor;
	private WorkPersonMapDao workpersonMapDao;
	private ListView doctorsListView, labsListView;
	private ArrayList<WorkPersonMap> workPersonMaps;
	private AddWorkTypeDoctorListAdapter doctorListAdapter;
	// private List<String> doctorNames = new ArrayList<String>();
	private List<String> doctorAmounts = new ArrayList<String>();
	// private List<String> labNames = new ArrayList<String>();
	// private List<String> labAmounts = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_add_work_type);
		personDao = new PersonDao(getApplicationContext());
		workTypeDao = new WorkTypeDao(getApplicationContext());
		workpersonMapDao = new WorkPersonMapDao(getApplicationContext());
		defaultprice = (EditText) findViewById(R.id.add_worktype_activity_defaultamount_editText);
		workTypeEditText = (EditText) findViewById(R.id.add_worktype_activity_worktype_editText);
		addWorkTypeButton = (Button) findViewById(R.id.add_worktype_activity_worktype_add_button);
		doctor = (RadioButton) findViewById(R.id.addworktypeDoctorradioButton1);
		lab = (RadioButton) findViewById(R.id.addworktypeLabradioButton2);
		doctorsListView = (ListView) findViewById(R.id.addworktypedoctorlistView);
		labsListView = (ListView) findViewById(R.id.addworktypelablistView);
		defaultprice.setText("100");
		defaultprice.setEnabled(false);

		prepareWorkPersonMaps();
		doctorListAdapter = new AddWorkTypeDoctorListAdapter(this, workPersonMaps, doctorAmounts);
		doctorsListView.setAdapter(doctorListAdapter);

		// doctorListAdapter.notifyDataSetChanged();
		// AddWorkTypeLabListAdapter labListAdapter = new
		// AddWorkTypeLabListAdapter(this, labNames, labAmounts);
		// objlv2.setAdapter(labListAdapter);

		addWorkTypeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				prepareWorkType();
				workTypeDao.insertDetails(work);
				// prepareWorkPersonMaps();
				getAmount();
				workpersonMapDao.insertDetails(workPersonMap);
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






	private void prepareWorkPersonMaps() {
		workPersonMaps = new ArrayList<WorkPersonMap>();
		workPersonMap = new WorkPersonMap();
		List<Person> doctors = personDao.getAllPeopleByType(CommonUtil.TYPE_DOCTOR);

		if (doctors == null || doctors.size() == 0) {
			return;
		}
		for (Person doctor : doctors) {
			WorkPersonMap map = new WorkPersonMap();
			map.setPerson(doctor);
			workPersonMaps.add(map);
		}
	}

	private void getAmount() {
		View v;
		List<Person> doctors = personDao.getAllPeopleByType(CommonUtil.TYPE_DOCTOR);
		// WorkType workType = new WorkType();
		// WorkPersonMap map = new WorkPersonMap();

		for (int i = 0; i < doctors.size(); i++) {

			v = doctorsListView.getAdapter().getView(i, null, null);
			v = doctorsListView.getChildAt(i);
			EditText editText = (EditText) v.findViewById(R.id.addwork_type_doctorlist_adapter_editText);
			if (!TextUtils.isEmpty(editText.getText().toString())) {

				workPersonMap.setPerson(doctors.get(i));
				workPersonMap.setPrice(new BigDecimal(editText.getText().toString()));
				workPersonMap.setWorkType(work);
			}
		}
	}

	private void prepareWorkType() {
		work = new WorkType();
		workPersonMap = new WorkPersonMap();
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
