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
	private static String[] cols = { DatabaseHelper.KEY_ID, DatabaseHelper.WORKTYPE_PERSON_PERSON_ID, DatabaseHelper.WORKTYPE_PERSON_WORK_ID, DatabaseHelper.WORKTYPE_PERSON_PRICE };
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

	public long insertDetails(List<WorkPersonMap> list) {
		for (WorkPersonMap map : list) {
			insertDetails(map);
		}
		return 0;
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
		return iterateWorkPersonMaps(cursor);
	}

	public WorkPersonMap getWorkPersonMap(WorkPersonMap map) {
		if (map == null || map.getPerson() == null || map.getWorkType() == null) {
			return null;
		}
		Cursor c = queryByMapping(map);
		List<WorkPersonMap> workTypes = iterateWorkPersonMaps(c);
		if (workTypes == null || workTypes.size() == 0) {
			return null;
		}
		return workTypes.get(0);
	}

	private List<WorkPersonMap> iterateWorkPersonMaps(Cursor cursor) {
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
		return workPersonMapDb.query(DatabaseHelper.WORKTYPE_PERSON_TABLE, cols, DatabaseHelper.WORKTYPE_PERSON_PERSON_ID + " = " + map.getPerson().getId() + " AND "
				+ DatabaseHelper.WORKTYPE_PERSON_WORK_ID + " = " + map.getWorkType().getId(), null, null, null, null);
	}

	private Cursor queryByWorkType(long workTypeId) {
		openToWrite();
		return workPersonMapDb.query(DatabaseHelper.WORKTYPE_PERSON_TABLE, cols, DatabaseHelper.WORKTYPE_PERSON_WORK_ID + " = " + workTypeId, null, null, null, null);
	}

	public List<WorkPersonMap> getMapsForWorkType(WorkType workType) {
		if (workType == null) {
			return null;
		}
		return iterateWorkPersonMaps(queryByWorkType(workType.getId()));
	}

	public long updateWorkPersonMaps(List<WorkPersonMap> workPersonMaps) {
		if (workPersonMaps == null || workPersonMaps.size() == 0) {
			return -10;
		}
		for (WorkPersonMap map : workPersonMaps) {
			long result = 0;
			if (map.getId() == null || map.getId() == 0) {
				result = insertDetails(map);
			} else {
				result = updateWorkPersonMap(map);
			}
			if (result < 0) {
				return result;
			}
		}
		return 0;
	}

	public long updateWorkPersonMap(WorkPersonMap map) {
		if (map == null) {
			return -10;
		}
		openToWrite();
		return workPersonMapDb
				.update(DatabaseHelper.WORKTYPE_PERSON_TABLE, prepareContentValues(map), DatabaseHelper.KEY_ID + " = ?", new String[] { String.valueOf(map.getId()) });
	}

	private ContentValues prepareContentValues(WorkPersonMap map) {
		ContentValues contentValues = new ContentValues();
		if (map.getPrice() != null) {
			contentValues.put(DatabaseHelper.WORKTYPE_PERSON_PRICE, map.getPrice().toString());
		}
		return contentValues;
	}

	public List<WorkPersonMap> getMapsForWorkType(WorkType work, String personType) {
		if (work == null) {
			return null;
		}
		List<WorkPersonMap> maps = iterateWorkPersonMaps(queryByWorkType(work.getId()));
		List<WorkPersonMap> typeMaps = new ArrayList<WorkPersonMap>();
		for (WorkPersonMap map : maps) {
			if (map.getPerson() != null && map.getPerson().getWorkType()!=null && map.getPerson().getWorkType().equals(personType)) {
				typeMaps.add(map);
			}
		}
		return typeMaps;
	}

}
