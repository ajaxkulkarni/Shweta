package com.rns.shwetalab.mobile.db;

import java.math.BigDecimal;
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

public class JobLabMapDao {

	private DatabaseHelper dbHelper;
	private SQLiteDatabase jobMapDb;
	private Context context;
	private static String[] cols = { DatabaseHelper.KEY_ID, DatabaseHelper.LAB_JOB_ID, DatabaseHelper.LAB_ID, DatabaseHelper.LAB_PRICE};
	private PersonDao personDao;
	private JobsDao jobsDao;

	public JobLabMapDao(Context c) {
		context = c;
		personDao = new PersonDao(c);
		//jobsDao = new JobsDao(c);
	}

	public void openToRead() {
		dbHelper = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.VERSION);
		jobMapDb = dbHelper.getReadableDatabase();
	}

	public void openToWrite() {
		dbHelper = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.VERSION);
		jobMapDb = dbHelper.getWritableDatabase();
	}

	public void Close() {
		jobMapDb.close();
	}

	public long insertDetails(Job job) {
		if (job == null || job.getDoctor() == null) {
			return -10;
		}
		openToWrite();
		long val = jobMapDb.insert(DatabaseHelper.JOB_LABS_TABLE, null, prepareContentValues(job));
		Log.d(DatabaseHelper.DATABASE_NAME, "Work type person mapping inserted!! Result :" + val);
		Close();
		return val;
	}


	private ContentValues prepareContentValues(Job job) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHelper.JOB_ID, job.getId());
		contentValues.put(DatabaseHelper.LAB_ID, job.getDoctor().getId());
		if(job.getPrice() != null) {
			contentValues.put(DatabaseHelper.LAB_PRICE, job.getPrice().toString());
		}
		return contentValues;
	}


	private Cursor queryByJob(Job job) {
		openToWrite();
		return jobMapDb.query(DatabaseHelper.JOB_LABS_TABLE, cols, DatabaseHelper.LAB_JOB_ID + " = " + job.getId(), null, null, null, null);
	}

	//TODO: Use this to get Lab jobs for View Jobs
	public List<Job> getLabJobsForJob(Job job) {
		List<Job> labJobs = new ArrayList<Job>();
		Cursor cursor = queryByJob(job);
		while(cursor.moveToNext()) {
			Job labJob = new Job();
			labJob.setPatientName(job.getPatientName());
			labJob.setDate(job.getDate());
			labJob.setPosition(job.getPosition());
			labJob.setQuadrent(job.getQuadrent());
			labJob.setShade(job.getShade());
			labJob.setId(job.getId());
			labJob.setDoctor(personDao.getPerson(cursor.getInt(2)));
			labJob.setPrice(new BigDecimal(cursor.getDouble(3)));
			labJobs.add(labJob);
		} 
		return labJobs;
	}

}
