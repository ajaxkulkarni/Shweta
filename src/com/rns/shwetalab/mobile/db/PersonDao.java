package com.rns.shwetalab.mobile.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rns.shwetalab.mobile.domain.Person;

public class PersonDao {

	private SQLiteDatabase personDb;
	private DatabaseHelper personHelper;
	private Context context;
	private static String[] cols = { DatabaseHelper.KEY_ID, DatabaseHelper.PERSON_NAME, DatabaseHelper.PERSON_EMAIL,DatabaseHelper.PERSON_TYPE };

	public PersonDao(Context c) {
		context = c;
	}

	public void openToRead() {
		personHelper = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.VERSION);
		personDb = personHelper.getReadableDatabase();
	}

	public void openToWrite() {
		personHelper = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.VERSION);
		personDb = personHelper.getWritableDatabase();
	}

	public void Close() {
		personDb.close();
	}

	public long insertDetails(Person person) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHelper.PERSON_NAME, person.getName());
		contentValues.put(DatabaseHelper.PERSON_EMAIL, person.getEmail());
		contentValues.put(DatabaseHelper.PERSON_PHONE, person.getPhone());
		contentValues.put(DatabaseHelper.PERSON_TYPE, person.getWorkType());
		openToWrite();
		long val = personDb.insert(DatabaseHelper.PERSON_TABLE, null, contentValues);
		Log.d(DatabaseHelper.DATABASE_NAME, "Person inserted!! Result :" + val);
		Close();
		return val;

	}


	public Cursor queryByName(String name) {
		openToWrite();
		return personDb.query(DatabaseHelper.PERSON_TABLE, cols, DatabaseHelper.PERSON_NAME + " like '" + name + "'", null, null,
				null, null);
	}

	public Person getPerson(Person person) {
		if (person == null || person.getName() == null) {
			return null;
		}
		Cursor c = queryByName(person.getName());
		List<Person> persons = iteratePersonCursor(c);
		if(persons == null || persons.size() == 0) {
			return null;
		}
		return persons.get(0);

	}
	
	public Person getPerson(Integer id) {
		Cursor c = queryById(id);
		List<Person> persons = iteratePersonCursor(c);
		if(persons == null || persons.size() == 0) {
			return null;
		}
		return persons.get(0);

	}

	private Cursor queryById(Integer id) {
		openToWrite();
		return personDb.query(DatabaseHelper.PERSON_TABLE, cols, DatabaseHelper.KEY_ID + " = " + id , null, null,null, null);
	}

	public long updateldetail(int rowId, String fname, String lname) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHelper.PERSON_NAME, fname);
		contentValues.put(DatabaseHelper.PERSON_EMAIL, lname);
		openToWrite();
		long val = personDb.update(DatabaseHelper.PERSON_TABLE, contentValues, DatabaseHelper.KEY_ID + "=" + rowId,
				null);
		Close();
		return val;
	}

	public int deletOneRecord(int rowId) {
		openToWrite();
		int val = personDb.delete(DatabaseHelper.PERSON_TABLE, DatabaseHelper.KEY_ID + "=" + rowId, null);
		Close();
		return val;
	}

	public String[] getDoctorNames() {
		List<Person> persons = getAll();
		if (persons.size() == 0) {
			return new String[0];
		}
		List<String> namesList = new ArrayList<String>();
		for (Person person : persons) {
			namesList.add(person.getName());
		}
		String[] names = new String[namesList.size()];
		names = namesList.toArray(new String[0]);
		return names;
	}

	public List<Person> getAll() {
		openToWrite();
		Cursor cursor = personDb.query(DatabaseHelper.PERSON_TABLE, cols, null, null, null, null, null);

		/*
		 * String selectQuery = "SELECT  * FROM " + DatabaseHelper.PERSON_TABLE;
		 * openToRead(); Cursor cursor = personDb.rawQuery(selectQuery, null);
		 */
		Log.d(DatabaseHelper.DATABASE_NAME, "Records retreived:" + cursor.getCount());
		return iteratePersonCursor(cursor);
	}

	private List<Person> iteratePersonCursor(Cursor cursor) {
		List<Person> persons = new ArrayList<Person>();
		if (cursor.moveToFirst()) {
			do {
				Person person = new Person();
				person.setId(Integer.parseInt(cursor.getString(0)));
				person.setName(cursor.getString(1));
				person.setEmail(cursor.getString(2));
				person.setPhone(cursor.getString(3));
				persons.add(person);
			} while (cursor.moveToNext());
		}

		return persons;
	}

	public List<Person> getAllPeopleByType(String type) {
		openToWrite();
		return iteratePersonCursor(personDb.query(DatabaseHelper.PERSON_TABLE, cols, DatabaseHelper.PERSON_TYPE + " like '" + type + "'", null, null,null, null));
	}

}
