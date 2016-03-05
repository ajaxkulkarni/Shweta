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
import com.rns.shwetalab.mobile.domain.WorkPersonMap;
import com.rns.shwetalab.mobile.domain.WorkType;

public class JobWorkTypeMapDao {

	private DatabaseHelper dbHelper;
	private SQLiteDatabase jobMapDb;
	private Context context;
	private static String[] cols = { DatabaseHelper.KEY_ID, DatabaseHelper.JOB_ID, DatabaseHelper.WORKTYPE_ID };
	private PersonDao personDao;
	private WorkTypeDao workTypeDao;

	public JobWorkTypeMapDao(Context c) {
		context = c;
		personDao = new PersonDao(c);
		workTypeDao = new WorkTypeDao(c);
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
		if (job == null || job.getWorkTypes() == null) {
			return -10;
		}
		//setWorkTypes(job);
		openToWrite();
		long val = insertJobWorktypeMap(job);
		Log.d(DatabaseHelper.DATABASE_NAME, "Work type person mapping inserted!! Result :" + val);
		Close();
		return val;

	}

	private long insertJobWorktypeMap(Job job) {
		long val = 0;
		for (WorkType workType : job.getWorkTypes()) {
			val = jobMapDb.insert(DatabaseHelper.JOB_WORKTYPES_TABLE, null, prepareContentValues(job, workType));
		}
		return val;
	}

	private ContentValues prepareContentValues(Job job, WorkType workType) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHelper.JOB_ID, job.getId());
		contentValues.put(DatabaseHelper.WORKTYPE_ID, workType.getId());
		return contentValues;
	}

	private void setWorkTypes(Job job) {
		if (job.getWorkTypes() == null || job.getWorkTypes().size() == 0) {
			return;
		}
		List<WorkType> workTypes = new ArrayList<WorkType>();
		for (WorkType workType : job.getWorkTypes()) {
			workTypes.add(workTypeDao.getWorkType(workType));
		}
		job.setWorkTypes(workTypes);
	}

	private Cursor queryByJob(Job job) {
		openToWrite();
		return jobMapDb.query(DatabaseHelper.JOB_WORKTYPES_TABLE, cols, DatabaseHelper.JOB_ID + " = " + job.getId(), null, null, null, null);
	}

	//TODO: Use this to get worktypes for View Jobs
	public List<WorkType> getWorktypesForJob(Job job) {
		List<WorkType> worktypes = new ArrayList<WorkType>();
		Cursor cursor = queryByJob(job);
		while (cursor.moveToNext()) {
			WorkType worktype = new WorkType();
			worktype = workTypeDao.getWorkType(Integer.parseInt(cursor.getString(2)));
			worktypes.add(worktype);
		}
		return worktypes;
	}

}
