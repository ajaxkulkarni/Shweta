package com.rns.shwetalab.mobile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.rns.shwetalab.mobile.db.CommonUtil;
import com.rns.shwetalab.mobile.db.JobsDao;
import com.rns.shwetalab.mobile.db.PersonDao;
import com.rns.shwetalab.mobile.db.WorkPersonMapDao;
import com.rns.shwetalab.mobile.db.WorkTypeDao;
import com.rns.shwetalab.mobile.domain.Job;
import com.rns.shwetalab.mobile.domain.Person;
import com.rns.shwetalab.mobile.domain.WorkType;

public class JobEntry extends Activity implements OnItemSelectedListener,OnClickListener {

	private EditText dateSelected;
	private DatePickerDialog toDatePickerDialog;
	Button addwork, jobentry;
	private SimpleDateFormat dateFormatter;
	
	int count = 0;
	Spinner sp1, sp2;
	private AutoCompleteTextView doctorName;
	private PersonDao personDao;
	private AutoCompleteTextView workType1,workType2, workType3,workType4;
	private WorkTypeDao workTypeDao;
	private JobsDao jobsDao;
	private EditText patientName, shade;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_job_entry);
		dateFormatter = new SimpleDateFormat(CommonUtil.DATE_FORMAT, Locale.US);
		dateSelected = (EditText) findViewById(R.id.CurrentDateeditText);
		setDateTimeField();
		personDao = new PersonDao(getApplicationContext());
		workTypeDao = new WorkTypeDao(getApplicationContext());
		jobsDao = new JobsDao(getApplicationContext());
		new WorkPersonMapDao(getApplicationContext());
		prepareDoctorNames();
		prepareWorkTypes();
		
		addwork = (Button) findViewById(R.id.jobentry_buttonadd);
		jobentry = (Button) findViewById(R.id.jobentrybutton);
	//	workType2 = (EditText) findViewById(R.id.jobentry_worktype1_editText);
	//	workType3 = (EditText) findViewById(R.id.jobentry_worktype2_editText);
		//workType4 = (EditText) findViewById(R.id.jobentry_worktype3_editText);
		sp1 = (Spinner) findViewById(R.id.spinner_position);
		sp2 = (Spinner) findViewById(R.id.spinner_quadrant);
		patientName = (EditText) findViewById(R.id.jobentry_patname_editText);
		shade = (EditText) findViewById(R.id.jobentry_shade_editText);

		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(JobEntry.this, R.array.Position , android.R.layout.simple_spinner_item);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		sp1.setAdapter(adapter);
		
		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(JobEntry.this, R.array.Quadrent , android.R.layout.simple_spinner_item);

		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		sp2.setAdapter(adapter1);
		
		//spinner_quad(sp1);
		//spinner_position(sp2);

		jobentry.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Validations();

				long result = jobsDao.insertDetails(prepareJob());
				if (result < 0) {
					if (result == -10) {
						Toast.makeText(getApplicationContext(), "Invalid DoctorName!!", Toast.LENGTH_LONG).show();
						return;
					}
					if (result == -20) {
						Toast.makeText(getApplicationContext(), "Invalid Work Type!!", Toast.LENGTH_LONG).show();
						return;
					}
					Toast.makeText(getApplicationContext(), "Error while saving data!!", Toast.LENGTH_LONG).show();
				}

				// Validations();
				CommonUtil.showMessage(JobEntry.this);
				// Toast.makeText(getApplicationContext(),
				// "Job Entered Successfully", Toast.LENGTH_LONG).show();

			}

		});
		addwork.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (count == 0) {

					workType2.setVisibility(View.VISIBLE);
					count++;
				}

				else if (count == 1) {

					workType3.setVisibility(View.VISIBLE);
					count++;
				}

				else if (count == 2) {
					workType4.setVisibility(View.VISIBLE);
					count++;
				} else if (count == 3)
					Toast.makeText(getApplicationContext(), "You can't add more than 3 Jobs", Toast.LENGTH_SHORT).show();

			}
		});

	}
	
	private void setDateTimeField() 
	{
		
		SimpleDateFormat dfDate  = new SimpleDateFormat("dd-M-yyyy");   
		Calendar c = Calendar.getInstance();
		String date = dfDate.format(c.getTime());
		dateSelected.setText(date); 

		dateSelected.setOnClickListener(this);

		Calendar newCalendar = Calendar.getInstance();
		toDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				Calendar newDate = Calendar.getInstance();
				newDate.set(year, monthOfYear, dayOfMonth);
				dateSelected.setText(dateFormatter.format(newDate.getTime()));
			}
		}, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
		
		
		
		
	}

	@Override
	public void onClick(View view) {
		toDatePickerDialog.show();
	}

	private Job prepareJob() {
		Job job = new Job();
		job.setDate(CommonUtil.convertDate(dateSelected.getText().toString()));
		job.setPatientName(patientName.getText().toString());
		job.setPosition(sp1.getSelectedItemPosition());
		job.setQuadrent(sp2.getSelectedItemPosition());
		job.setShade(Integer.valueOf(shade.getText().toString()));
		Person doctor = new Person();
		doctor.setName(doctorName.getText().toString());
		WorkType workType = new WorkType();
		
		workType.setName(workType1.getText().toString());
//		workType.setName(workType2.getText().toString());
//		workType.setName(workType3.getText().toString());
		
		job.setDoctor(doctor);
			job.setWorkType(workType);
	
		return job;

	}

	private void prepareWorkTypes() {
		workType1 = (AutoCompleteTextView) findViewById(R.id.jobentry_worktype_autocomplete);
		workType2 = (AutoCompleteTextView) findViewById(R.id.jobentry_worktype1_editText);
		workType3 = (AutoCompleteTextView) findViewById(R.id.jobentry_worktype2_editText);
		workType4 = (AutoCompleteTextView) findViewById(R.id.jobentry_worktype3_editText); 
		ArrayAdapter<String> doctorNames = new ArrayAdapter<String>(JobEntry.this, android.R.layout.simple_dropdown_item_1line, workTypeDao.getWorkTypeNames());
		workType1.setThreshold(1);
		workType2.setThreshold(1);
		workType3.setThreshold(1);
		workType4.setThreshold(1);
		workType1.setAdapter(doctorNames);
		workType2.setAdapter(doctorNames);
		workType3.setAdapter(doctorNames);
		workType4.setAdapter(doctorNames);

	}

	private void prepareDoctorNames() {
		doctorName = (AutoCompleteTextView) findViewById(R.id.jobentry_docname_autocomplete);
		ArrayAdapter<String> doctorNames = new ArrayAdapter<String>(JobEntry.this, android.R.layout.simple_dropdown_item_1line, personDao.getDoctorNames());
		doctorName.setThreshold(1);
		doctorName.setAdapter(doctorNames);
	}

//	private void spinner_quad(Spinner sp_docnm2) {
//
//		sp_docnm2.setPrompt("Quadrent");
//		sp_docnm2.setOnItemSelectedListener(this);
//		List<String> categories = new ArrayList<String>();
//		categories.add("1");
//		categories.add("2");
//		categories.add("3");
//		categories.add("4");
//
//		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(JobEntry.this, android.R.layout.simple_spinner_item, categories);
//
//		sp_docnm2.setAdapter(dataAdapter);
//	}
	
	

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

//	private void spinner_position(Spinner sp_docnm3) {
//
//		sp_docnm3.setOnItemSelectedListener(this);
//		sp_docnm3.setPrompt("Position");
//		List<String> categories = new ArrayList<String>();
//
//		categories.add("1");
//		categories.add("2");
//		categories.add("3");
//		categories.add("4");
//		categories.add("5");
//		categories.add("6");
//		categories.add("7");
//		categories.add("8");
//
//		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(JobEntry.this, android.R.layout.simple_spinner_item, categories);
//
//		sp_docnm3.setAdapter(dataAdapter);
//	}

}