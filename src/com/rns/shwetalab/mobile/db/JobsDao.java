package com.rns.shwetalab.mobile.db;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rns.shwetalab.mobile.ViewMonth;
import com.rns.shwetalab.mobile.domain.Job;
import com.rns.shwetalab.mobile.domain.Person;
import com.rns.shwetalab.mobile.domain.WorkPersonMap;
import com.rns.shwetalab.mobile.domain.WorkType;

public class JobsDao {

	private DatabaseHelper dbHelper;
	private SQLiteDatabase jobsDb;
	private PersonDao personDao;
	private WorkTypeDao workTypeDao;
	private WorkPersonMapDao workPersonMapDao;
	private Context context;

	private static String[] cols = { DatabaseHelper.KEY_ID, DatabaseHelper.JOB_PATIENT_NAME, DatabaseHelper.JOB_DATE, DatabaseHelper.JOB_SHADE, DatabaseHelper.JOB_DOCTOR,
			DatabaseHelper.JOB_WORK, DatabaseHelper.JOB_PRICE, DatabaseHelper.JOB_QUADRENT, DatabaseHelper.JOB_POSITION };

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
		Person doctor = personDao.getPerson(job.getDoctor());
		if (doctor == null) {
			return -10;
		}
		job.setDoctor(doctor);
		WorkType workType = workTypeDao.getWorkType(job.getWorkType());
		if (workType == null) {
			return -20;
		}
		job.setWorkType(workType);
		job.setQuadrent(job.getQuadrent());
		job.setPosition(job.getPosition());
		WorkPersonMap map = new WorkPersonMap();
		map.setWorkType(job.getWorkType());
		map.setPerson(job.getDoctor());
		map = workPersonMapDao.getWorkPersonMap(map);
		// job.setWorkPersonMap(workPersonMapDao.(cursor.getInt(6)));
		if (map != null) {
			job.setPrice(map.getPrice());
		} else {
			job.setPrice(job.getWorkType().getDefaultPrice());
		}
		
		openToWrite();
		long val = jobsDb.insert(DatabaseHelper.JOB_TABLE, null, prepareContentValues(job));
		
			jobsDb.insert(DatabaseHelper.JOB_WORKTYPES_TABLE,null,getWorkType(job) );
		
		
		
		
		Job labJob = getLabJob(workPersonMapDao.getMapsForWorkType(workType), job);
		if (labJob != null) {
			jobsDb.insert(DatabaseHelper.JOB_TABLE, null, prepareContentValues(labJob));
		}
		Log.d(DatabaseHelper.DATABASE_NAME, "Job inserted!! Result :" + val);
		Close();
		return val;

	}

	private ContentValues getWorkType(Job job) 
	{
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHelper.JOB_ID, job.getId());
//		WorkType type  = new WorkType();
		for(int i=0;i<job.getWorkType().getId();i++)
		{
			contentValues.put(DatabaseHelper.WORKTYPE_ID,job.getWorkType().toString());
		}
		return null;
	}

	private ContentValues prepareContentValues(Job job) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHelper.JOB_PATIENT_NAME, job.getPatientName());
		contentValues.put(DatabaseHelper.JOB_SHADE, job.getShade());
		contentValues.put(DatabaseHelper.JOB_DATE, CommonUtil.convertDate(job.getDate()));
		contentValues.put(DatabaseHelper.JOB_DOCTOR, job.getDoctor().getId());
		contentValues.put(DatabaseHelper.JOB_WORK, job.getWorkType().getId());
		if (job.getPrice() != null) {
			contentValues.put(DatabaseHelper.JOB_PRICE, job.getPrice().toString());
		}
		contentValues.put(DatabaseHelper.JOB_QUADRENT, job.getQuadrent());
		contentValues.put(DatabaseHelper.JOB_POSITION, job.getPosition());
		return contentValues;
	}

	private Job getLabJob(List<WorkPersonMap> mapsForWorkType, Job job) {
		for (WorkPersonMap map : mapsForWorkType) {
			if (map.getPerson() == null) {
				continue;
			}
			if (CommonUtil.TYPE_LAB.equals(map.getPerson().getWorkType())) {
				Job labJob = new Job();
				labJob.setDate(job.getDate());
				labJob.setPatientName(job.getPatientName());
				labJob.setShade(job.getShade());
				labJob.setWorkType(job.getWorkType());
				labJob.setDoctor(map.getPerson());
				labJob.setPrice(map.getPrice());
				return labJob;
			}
		}
		return null;
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

	public List<Job> getJobsByDate(String date, String personType) {
		List<Job> jobs = iterateJobsCursor(queryByDate(date));
		List<Job> jobsByType = new ArrayList<Job>();
		for (Job job : jobs) {
			if (job.getDoctor() != null && personType.equals(job.getDoctor().getWorkType())) {
				jobsByType.add(job);
			}
		}
		return jobsByType;
	}

	public Cursor queryByDate(String date) {
		openToWrite();
		return jobsDb.query(DatabaseHelper.JOB_TABLE, cols, DatabaseHelper.JOB_DATE + " = '" + date + "'", null, null, null, null);
	}

	public List<Job> getJobsByMonth(String month) {

		return iterateJobsCursor(queryForMonth(month));
	}

	public BigDecimal getIncomeForMonth(String month, String personType) {
		// int value = Integer.parseInt(month);
		BigDecimal total = BigDecimal.ZERO;
		List<Job> jobs = getJobsByMonth(month);
		for (Job job : jobs) {
			if (job.getWorkType() == null || job.getPrice() == null || job.getDoctor() == null || !personType.equals(job.getDoctor().getWorkType())) {
				continue;
			}
			total = total.add(job.getPrice());
		}
		return total;
	}

	private Cursor queryForMonth(String month) {
		String date1 = "01-" + ViewMonth.months().get(month) + "-" + "2016";
		String date2 = "30-" + ViewMonth.months().get(month) + "-" + "2016";
		openToWrite();
		return jobsDb.query(DatabaseHelper.JOB_TABLE, cols, DatabaseHelper.JOB_DATE  + " LIKE '%-" + ViewMonth.months().get(month) + "-2016'", null, null, null, null);
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
				job.setPrice(new BigDecimal(cursor.getInt(6)));
				job.setQuadrent(cursor.getInt(7));
				job.setPosition(cursor.getInt(8));
				jobs.add(job);
			} while (cursor.moveToNext());
		}

		return jobs;
	}

}
