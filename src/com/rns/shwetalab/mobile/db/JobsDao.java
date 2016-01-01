package com.rns.shwetalab.mobile.db;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.rns.shwetalab.mobile.domain.Job;
import com.rns.shwetalab.mobile.domain.Person;
import com.rns.shwetalab.mobile.domain.WorkPersonMap;
import com.rns.shwetalab.mobile.domain.WorkType;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class JobsDao {

	private DatabaseHelper dbHelper;
	private SQLiteDatabase jobsDb;
	private PersonDao personDao;
	private WorkTypeDao workTypeDao;
	private WorkPersonMapDao workPersonMapDao;
	private Context context;



	private static String[] cols = { DatabaseHelper.KEY_ID, DatabaseHelper.JOB_PATIENT_NAME, DatabaseHelper.JOB_DATE,
			DatabaseHelper.JOB_SHADE, DatabaseHelper.JOB_DOCTOR, DatabaseHelper.JOB_WORK };

	public JobsDao(Context c) {
		context = c;
		personDao = new PersonDao(context);
		workTypeDao = new WorkTypeDao(context);
		workPersonMapDao = new WorkPersonMapDao(context);
	}

	public void openToRead() {
		dbHelper = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.VERSION);
		jobsDb = dbHelper.getReadableDatabase();
	}

	public void openToWrite() {
		dbHelper = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.VERSION);
		jobsDb = dbHelper.getWritableDatabase();
	}

	public void Close() {
		jobsDb.close();
	}

	public long insertDetails(Job job) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHelper.JOB_PATIENT_NAME, job.getPatientName());
		contentValues.put(DatabaseHelper.JOB_SHADE, job.getShade());
		contentValues.put(DatabaseHelper.JOB_DATE, CommonUtil.convertDate(job.getDate()));
		Person doctor = personDao.getPerson(job.getDoctor());
		if (doctor == null) {
			return -10;
		}
		WorkType workType = workTypeDao.getWorkType(job.getWorkType());
		if (workType == null) 
		{
			return -20;
		}

		contentValues.put(DatabaseHelper.JOB_DOCTOR, doctor.getId());
		contentValues.put(DatabaseHelper.JOB_WORK, workType.getId());

		openToWrite();
		long val = jobsDb.insert(DatabaseHelper.JOB_TABLE, null, contentValues);
		Log.d(DatabaseHelper.DATABASE_NAME, "Job inserted!! Result :" + val);
		Close();
		return val;

	}

	public String[] getWorkTypeNames() {
		List<WorkType> workTypes = workTypeDao.getAll();
		if (workTypes.size() == 0) {
			return new String[0];
		}
		List<String> namesList = new ArrayList<String>();
		for (WorkType work : workTypes) {
			namesList.add(work.getName());
		}
		String[] names = new String[namesList.size()];
		names = namesList.toArray(new String[0]);
		return names;
	}

	public List<Job> getJobsByDate(String date) {
		return iterateJobsCursor(queryByDate(date));
	}

	public Cursor queryByDate(String date) {
		openToWrite();
		return jobsDb.query(DatabaseHelper.JOB_TABLE, cols, DatabaseHelper.JOB_DATE + " = '" + date + "'", null, null,
				null, null);
	}

	public List<Job> getJobsByMonth(String month) {
		//TODO: Convert to int
		return iterateJobsCursor(queryForMonth(1));
	}

	public BigDecimal getDoctorIncomeForMonth(String month)
	{

		BigDecimal total = BigDecimal.ZERO;
		List<Job> jobs = getJobsByMonth(month);
		for (Job job: jobs) 
		{
			if(job.getWorkType()==null || job.getWorkType().getDefaultPrice()==null)
				continue;

			total = total.add(job.getWorkType().getDefaultPrice());

		}
		return total;

	}

	private Cursor queryForMonth(int month) {
		String date1 = "01-" + month + "2016";
		String date2 = "30-" + month + "2016";
		openToRead();
		return jobsDb.query(DatabaseHelper.JOB_TABLE, cols, "'" + date1 + "'<=" + DatabaseHelper.JOB_DATE + " <= '" + date2 + "'", null, null,
				null, null);
	}

	private List<Job> iterateJobsCursor(Cursor cursor) {
		List<Job> jobs = new ArrayList<Job>();
		if (cursor.moveToFirst()) {
			do {
				Job job = new Job();
				job.setId(Integer.parseInt(cursor.getString(0)));
				job.setPatientName(cursor.getString(1));
				job.setDate(CommonUtil.convertDate(cursor.getString(2)));
				job.setShade(cursor.getInt(3));
				Person person = personDao.getPerson(cursor.getInt(4));
				job.setDoctor(person);
				WorkType workType = workTypeDao.getWorkType(cursor.getInt(5));
				job.setWorkType(workType);
				WorkPersonMap map = new WorkPersonMap();
				if (job.getWorkType() != null) 
				{
					if(map.getPrice()!=null)
					{
						job.setPrice(map.getPrice());
					}
					//if(job.setPrice(map.getPrice()))
					job.setPrice(job.getWorkType().getDefaultPrice());
				}
				if(workType == null || person == null) {
					continue;
				}

				map.setWorkType(job.getWorkType());
				map.setPerson(job.getDoctor());
				map = workPersonMapDao.getWorkPersonMap(map);
				//job.setWorkPersonMap(workPersonMapDao.(cursor.getInt(6)));
				if(map!=null)
				{
					job.getWorkType().setDefaultPrice(map.getPrice());
				}

				jobs.add(job);
			} while (cursor.moveToNext());
		}

		return jobs;
	}

}
