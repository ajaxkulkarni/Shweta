package com.rns.shwetalab.mobile.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rns.shwetalab.mobile.domain.Job;
import com.rns.shwetalab.mobile.domain.Person;
import com.rns.shwetalab.mobile.domain.WorkType;

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

	private List<Job> iterateJobsCursor(Cursor cursor) {
		List<Job> jobs = new ArrayList<Job>();
		if (cursor.moveToFirst()) {
			do {
				Job job = new Job();
				job.setId(Integer.parseInt(cursor.getString(0)));
				job.setPatientName(cursor.getString(1));
				job.setDate(CommonUtil.convertDate(cursor.getString(2)));
				job.setShade(cursor.getInt(3));
				job.setDoctor(personDao.getPerson(cursor.getInt(4)));
				job.setWorkType(workTypeDao.getWorkType(cursor.getInt(5)));
				//job.setWorkPersonMap(workPersonMapDao.(cursor.getInt(6)));
				if (job.getWorkType() != null) 
				{
//					if(job.getWorkPersonMap().getPrice()!=null)
//					{
//				    job.setPrice(job.getWorkPersonMap().getPrice());
//					}
//					else
						job.setPrice(job.getWorkType().getDefaultPrice());
				}
				jobs.add(job);
			} while (cursor.moveToNext());
		}

		return jobs;
	}

}
