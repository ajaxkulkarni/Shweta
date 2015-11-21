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
import com.rns.shwetalab.mobile.adapter.AddWorkTypeLabListAdapter;
import com.rns.shwetalab.mobile.db.PersonDao;
import com.rns.shwetalab.mobile.db.WorkTypeDao;
import com.rns.shwetalab.mobile.domain.WorkType;

public class AdminAddWorkTypeActivity extends Activity 
{

	private EditText workTypeEditText;
	private Button addWorkTypeButton;
	private WorkTypeDao workTypeDao;
	private WorkType work;
	private PersonDao personDao;
	private RadioButton lab,doctor;


	private ListView objlv1,objlv2;
	private List<String> doctorNames = new ArrayList<String>();
	private List<String> doctorAmounts = new ArrayList<String>();
	private List<String> labNames = new ArrayList<String>();
	private List<String> labAmounts = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_add_work_type);
		workTypeDao = new WorkTypeDao(getApplicationContext());
		workTypeEditText = (EditText) findViewById(R.id.add_worktype_activity_worktype_editText);
		addWorkTypeButton = (Button) findViewById(R.id.add_worktype_activity_worktype_add_button);
		doctor = (RadioButton)findViewById(R.id.addworktypeDoctorradioButton1);
		lab = (RadioButton)findViewById(R.id.addworktypeLabradioButton2);
		objlv1 = (ListView) findViewById(R.id.addworktypedoctorlistView);
		objlv2 = (ListView) findViewById(R.id.addworktypelablistView);

		
		doctorNames.add("Ajinkya Kulkarni");
		doctorAmounts.add("Rs.200/-");
		labNames.add("Shewta Lab");



		AddWorkTypeDoctorListAdapter doctorListAdapter = new AddWorkTypeDoctorListAdapter(this,doctorNames,doctorAmounts);
		objlv1.setAdapter(doctorListAdapter);

		AddWorkTypeLabListAdapter labListAdapter = new AddWorkTypeLabListAdapter (this,labNames ,labAmounts);
		objlv2.setAdapter(labListAdapter);

		addWorkTypeButton.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View v) {
				prepareWorkType();
				workTypeDao.insertDetails(work);
				Toast.makeText(getApplicationContext(), "Record inserted successfully!", Toast.LENGTH_LONG).show();
			}

		});


		doctor.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View v) 
			{

				lab.setChecked(false);
				objlv2.setVisibility(View.GONE);
				objlv1.setVisibility(View.VISIBLE);	

			}
		});

		lab.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View v) 
			{
				doctor.setChecked(false);
				objlv1.setVisibility(View.GONE);
				objlv2.setVisibility(View.VISIBLE);	

			}
		});

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
