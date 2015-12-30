package com.rns.shwetalab.mobile.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "DENTAL";
	public static final int VERSION = 2;
	public static final String KEY_ID = "id";

	public static final String PERSON_TABLE = "person";
	public static final String PERSON_NAME = "name";
	public static final String PERSON_EMAIL = "email";
	public static final String PERSON_PHONE = "phone";
	public static final String PERSON_TYPE = "type";

	public static final String WORKTYPE_TABLE = "worktype";
	public static final String WORKTYPE_NAME = "name";
	public static final String WORKTYPE_PRICE = "default_price";

	public static final String WORKTYPE_PERSON_TABLE = "worktype_person";
	public static final String WORKTYPE_PERSON_PERSON_ID = "person_id";
	public static final String WORKTYPE_PERSON_WORK_ID = "work_id";
	public static final String WORKTYPE_PERSON_PRICE = "price";

	public static final String JOB_TABLE = "jobs";
	public static final String JOB_PATIENT_NAME = "patient_name";
	public static final String JOB_SHADE = "shade";
	public static final String JOB_DATE = "job_date";
	public static final String JOB_DOCTOR = "doctor_id";
	public static final String JOB_WORK = "work_type_id";

	public static final String CREATE_TABLE_PERSON = "create table " + PERSON_TABLE + " (" + KEY_ID
			+ " integer primary key autoincrement, " + PERSON_NAME + " text not null, " + PERSON_EMAIL
			+ " text not null, " + PERSON_PHONE + " text not null, " + PERSON_TYPE + " text not null)";

	public static final String CREATE_TABLE_WORK_TYPE = "create table " + WORKTYPE_TABLE + " (" + KEY_ID
			+ " integer primary key autoincrement, " + WORKTYPE_NAME + " text not null, " + WORKTYPE_PRICE
			+ " integer)";

	public static final String CREATE_TABLE_WORK_TYPE_PERSON = "create table " + WORKTYPE_PERSON_TABLE + " (" + KEY_ID
			+ " integer primary key autoincrement, " + WORKTYPE_PERSON_PERSON_ID + " integer ,"
			+ WORKTYPE_PERSON_WORK_ID + " integer, " + WORKTYPE_PERSON_PRICE + " decimal)";

	public static final String CREATE_TABLE_JOB = "create table " + JOB_TABLE + " (" + KEY_ID
			+ " integer primary key autoincrement, " + JOB_DOCTOR + " integer ," + JOB_WORK + " integer, "
			+ JOB_PATIENT_NAME + " text not null, " + JOB_SHADE + " integer, " + JOB_DATE + " date)";

	public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_PERSON);
		db.execSQL(CREATE_TABLE_WORK_TYPE);
		db.execSQL(CREATE_TABLE_WORK_TYPE_PERSON);
		db.execSQL(CREATE_TABLE_JOB);
		Log.d(DATABASE_NAME, "Tables Created!!");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		/*db.execSQL("drop table " + PERSON_TABLE);
		db.execSQL("drop table " + WORKTYPE_TABLE);
		db.execSQL("drop table " + WORKTYPE_PERSON_TABLE);
		db.execSQL("drop table " + JOB_TABLE);
		onCreate(db);*/
		Log.d(DATABASE_NAME, "Upgrading the database from :" + oldVersion + " to :" + newVersion);
	}

}
