package com.rns.shwetalab.mobile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
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
	private PersonDao personDao;
	private RadioButton lab, doctor;
	private WorkPersonMapDao workpersonMapDao;
	private ListView personsListView, labsListView;
	private ArrayList<WorkPersonMap> workPersonMaps;
	private AddWorkTypeDoctorListAdapter personListAdapter;
	Context context = this;
	private List<String> doctorAmount = new ArrayList<String>();

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
		personsListView = (ListView) findViewById(R.id.addworktypedoctorlistView);
		labsListView = (ListView) findViewById(R.id.addworktypelablistView);
		defaultprice.setText("100");
		preparePersonMaps();

		addWorkTypeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) 
			{
			//	validations();
				prepareWorkType();
				long result = workTypeDao.insertDetails(work);
				if (result < 0) {
					CommonUtil.showError(AdminAddWorkTypeActivity.this, "Error inserting record!");
					return;
				}
				workpersonMapDao.insertDetails(prepareWorkPersonMapsList());
				CommonUtil.showMessage(AdminAddWorkTypeActivity.this);
			}

			
		});

		
		doctor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				lab.setChecked(false);
				doctor.setChecked(true);
				preparePersonMaps();
				// labsListView.setVisibility(View.GONE);
				// doctorsListView.setVisibility(View.VISIBLE);
			}
		});

		lab.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doctor.setChecked(false);
				preparePersonMaps();
				// doctorsListView.setVisibility(View.GONE);
				// labsListView.setVisibility(View.VISIBLE);
			}
		});

	}

	
	private boolean validations() 
	{
		
		if (TextUtils.isEmpty(workTypeEditText.getText())) 
		{
			workTypeEditText.setError(Html.fromHtml("<font color = 'red'>Enter Worktype!</font>"));
			return false;
		}
		return true;
	}
	
	private void preparePersonMaps() {
		prepareWorkPersonMaps();
		personListAdapter = new AddWorkTypeDoctorListAdapter(this, workPersonMaps, doctorAmount);
		personsListView.setAdapter(personListAdapter);
	}

	private void prepareWorkPersonMaps() {
		workPersonMaps = new ArrayList<WorkPersonMap>();
		List<Person> persons = getPersonsByType();
		if (persons == null || persons.size() == 0) {
			return;
		}
		for (Person person : persons) {
			WorkPersonMap map = new WorkPersonMap();
			map.setPerson(person);
			workPersonMaps.add(map);
		}
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

	private List<WorkPersonMap> prepareWorkPersonMapsList() {
		List<WorkPersonMap> maps = new ArrayList<WorkPersonMap>();
		View v;
		List<Person> persons = getPersonsByType();
		if (persons == null || persons.size() == 0) {
			return null;
		}
		for (int i = 0; i < persons.size(); i++) {
			WorkPersonMap map = (WorkPersonMap) personsListView.getAdapter().getItem(i);
			v = personsListView.getAdapter().getView(i, null, null);
			v = personsListView.getChildAt(i);
			EditText editText = (EditText) v.findViewById(R.id.addwork_type_doctorlist_adapter_editText);
			if (!TextUtils.isEmpty(editText.getText().toString())) {
				map.setPrice(new BigDecimal(editText.getText().toString()));
			}
			maps.add(map);
		}
		return maps;
	}

	private void prepareWorkType() 
	{
		work = new WorkType();
		if (!TextUtils.isEmpty(workTypeEditText.getText())) 
		{
			//workTypeEditText.setError(Html.fromHtml("<font color = 'red'>Enter Worktype!</font>"));
			work.setName(workTypeEditText.getText().toString());	
		}
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
