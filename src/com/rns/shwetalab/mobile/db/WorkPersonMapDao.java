package com.rns.shwetalab.mobile.db;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rns.shwetalab.mobile.domain.Person;
import com.rns.shwetalab.mobile.domain.WorkPersonMap;
import com.rns.shwetalab.mobile.domain.WorkType;

public class WorkPersonMapDao {

	private DatabaseHelper dbHelper;
	private SQLiteDatabase workPersonMapDb;
	private Context context;
	private static String[] cols = { DatabaseHelper.KEY_ID, DatabaseHelper.WORKTYPE_PERSON_PERSON_ID,DatabaseHelper.WORKTYPE_PERSON_WORK_ID, DatabaseHelper.WORKTYPE_PERSON_PRICE };
	private PersonDao personDao;
	private WorkTypeDao workTypeDao;

	public WorkPersonMapDao(Context c) {
		context = c;
		personDao = new PersonDao(c);
		workTypeDao = new WorkTypeDao(c);
	}

	public void openToRead() {
		dbHelper = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.VERSION);
		workPersonMapDb = dbHelper.getReadableDatabase();
	}

	public void openToWrite() {
		dbHelper = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.VERSION);
		workPersonMapDb = dbHelper.getWritableDatabase();
	}

	public void Close() {
		workPersonMapDb.close();
	}

	public long insertDetails(WorkPersonMap map) {
		if (map.getPerson() == null || map.getWorkType() == null) {
			return -10;
		}
		ContentValues contentValues = new ContentValues();
		Person doctor = personDao.getPerson(map.getPerson());
		if (doctor == null || doctor.getId() == null) {
			return -10;
		}
		contentValues.put(DatabaseHelper.WORKTYPE_PERSON_PERSON_ID, doctor.getId().toString());
		WorkType work = workTypeDao.getWorkType(map.getWorkType());
		if (work == null || work.getId() == null) {
			return -10;
		}
		contentValues.put(DatabaseHelper.WORKTYPE_PERSON_WORK_ID, work.getId().toString());
		if (map.getPrice() == null || BigDecimal.ZERO.equals(map.getPrice())) {
			return 0;
		}
		if (map.getPrice() != null) {
			contentValues.put(DatabaseHelper.WORKTYPE_PERSON_PRICE, map.getPrice().toString());
		}
		openToWrite();
		long val = workPersonMapDb.insert(DatabaseHelper.WORKTYPE_PERSON_TABLE, null, contentValues);
		Log.d(DatabaseHelper.DATABASE_NAME, "Work type person mapping inserted!! Result :" + val);
		Close();
		return val;

	}

	public List<WorkPersonMap> getAll() {
		openToWrite();
		Cursor cursor = workPersonMapDb.query(DatabaseHelper.WORKTYPE_PERSON_TABLE, cols, null, null, null, null, null);
		Log.d(DatabaseHelper.DATABASE_NAME, "Records retreived:" + cursor.getCount());
		return iterateWorkTypes(cursor);
	}

	public WorkPersonMap getWorkPersonMap(WorkPersonMap map) {
		if (map == null || map.getPerson() == null || map.getWorkType() == null) {
			return null;
		}
		Cursor c = queryByMapping(map);
		List<WorkPersonMap> workTypes = iterateWorkTypes(c);
		if (workTypes == null || workTypes.size() == 0) {
			return null;
		}
		return workTypes.get(0);
	}

	private List<WorkPersonMap> iterateWorkTypes(Cursor cursor) {
		List<WorkPersonMap> workPersonMaps = new ArrayList<WorkPersonMap>();
		if (cursor.moveToFirst()) {
			do {
				WorkPersonMap workPersonMap = new WorkPersonMap();
				workPersonMap.setId(Integer.parseInt(cursor.getString(0)));
				workPersonMap.setPerson(personDao.getPerson(Integer.parseInt(cursor.getString(1))));
				workPersonMap.setWorkType(workTypeDao.getWorkType(Integer.parseInt(cursor.getString(2))));
				workPersonMap.setPrice(new BigDecimal(cursor.getDouble(3)));
				workPersonMaps.add(workPersonMap);
			} while (cursor.moveToNext());
		}

		return workPersonMaps;
	}

	public Cursor queryByMapping(WorkPersonMap map) {
		openToWrite();
		return workPersonMapDb.query(DatabaseHelper.WORKTYPE_PERSON_TABLE, cols,
		DatabaseHelper.WORKTYPE_PERSON_PERSON_ID + " = " + map.getPerson().getId() + " AND "+ 
		DatabaseHelper.WORKTYPE_PERSON_WORK_ID + " = " + map.getWorkType().getId(), null, null, null,null);
	}

	/*
	 * public WorkType getWorkType(int id) { Cursor c = queryById(id);
	 * List<WorkType> workTypes = iterateWorkTypes(c); if (workTypes == null ||
	 * workTypes.size() == 0) { return null; } return workTypes.get(0); }
	 * 
	 * private Cursor queryById(int id) { openToWrite(); return
	 * workPersonMapDb.query(DatabaseHelper.WORKTYPE_TABLE, cols,
	 * DatabaseHelper.KEY_ID + " = " + id , null,null, null, null); }
	 */

}
