package com.rns.shwetalab.mobile.db;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rns.shwetalab.mobile.domain.WorkType;

public class WorkTypeDao {

	private DatabaseHelper dbHelper;
	private SQLiteDatabase workTypeDb;
	private Context context;
	private static String[] cols = { DatabaseHelper.KEY_ID, DatabaseHelper.WORKTYPE_NAME, DatabaseHelper.WORKTYPE_PRICE, DatabaseHelper.WORKTYPE_QUANTITY };

	public WorkTypeDao(Context c) {
		context = c;
	}

	public void openToRead() {
		dbHelper = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.VERSION);
		workTypeDb = dbHelper.getReadableDatabase();
	}

	public void openToWrite() {
		dbHelper = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.VERSION);
		workTypeDb = dbHelper.getWritableDatabase();
	}

	public void Close() {
		workTypeDb.close();
	}

	public long insertDetails(WorkType work) {

		ContentValues contentValues = prepareContentValues(work);
		openToWrite();
		long val = workTypeDb.insert(DatabaseHelper.WORKTYPE_TABLE, null, contentValues);
		Log.d(DatabaseHelper.DATABASE_NAME, "Work type inserted!! Result :" + val);
		Close();
		return val;

	}

	public long updateWorkType(WorkType workType) {
		if (workType == null) {
			return -10;
		}
		openToWrite();
		return workTypeDb.update(DatabaseHelper.WORKTYPE_TABLE, prepareContentValues(workType), DatabaseHelper.KEY_ID + " = ?", new String[] { String.valueOf(workType.getId()) });
	}

	private ContentValues prepareContentValues(WorkType work) {
		ContentValues contentValues = new ContentValues();
		//contentValues.put(DatabaseHelper.WORKTYPE_QUANTITY, work.getQuantity());
		contentValues.put(DatabaseHelper.WORKTYPE_NAME, work.getName());
		if (work.getDefaultPrice() != null) {
			contentValues.put(DatabaseHelper.WORKTYPE_PRICE, work.getDefaultPrice().toString());
		}
		return contentValues;
	}

	public String[] getWorkTypeNames() {
		List<WorkType> workTypes = getAll();
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

	public List<WorkType> getAll() {
		openToWrite();
		Cursor cursor = workTypeDb.query(DatabaseHelper.WORKTYPE_TABLE, cols, null, null, null, null, null);
		Log.d(DatabaseHelper.DATABASE_NAME, "Records retreived:" + cursor.getCount());
		return iterateWorkTypes(cursor);
	}

	public WorkType getWorkType(WorkType work) {
		if (work == null || work.getName() == null) {
			return null;
		}
		Cursor c = queryByName(work.getName());
		List<WorkType> workTypes = iterateWorkTypes(c);
		if (workTypes == null || workTypes.size() == 0) {
			return null;
		}
		return workTypes.get(0);
	}

	private List<WorkType> iterateWorkTypes(Cursor cursor) {
		List<WorkType> workTypes = new ArrayList<WorkType>();
		if (cursor.moveToFirst()) {
			do {
				WorkType work = new WorkType();
				work.setId(Integer.parseInt(cursor.getString(0)));
				work.setName(cursor.getString(1));
				work.setDefaultPrice(new BigDecimal(cursor.getString(2)));
		//		work.setQuantity(cursor.getInt(3));
				workTypes.add(work);
			} while (cursor.moveToNext());
		}

		return workTypes;
	}

	public Cursor queryByName(String name) {
		openToWrite();
		return workTypeDb.query(DatabaseHelper.WORKTYPE_TABLE, cols, DatabaseHelper.WORKTYPE_NAME + " like '" + name + "'", null, null, null, null);
	}

	public WorkType getWorkType(int id) {
		Cursor c = queryById(id);
		List<WorkType> workTypes = iterateWorkTypes(c);
		if (workTypes == null || workTypes.size() == 0) {
			return null;
		}
		return workTypes.get(0);
	}

	private Cursor queryById(int id) {
		openToWrite();
		return workTypeDb.query(DatabaseHelper.WORKTYPE_TABLE, cols, DatabaseHelper.KEY_ID + " = " + id, null, null, null, null);
	}

}
