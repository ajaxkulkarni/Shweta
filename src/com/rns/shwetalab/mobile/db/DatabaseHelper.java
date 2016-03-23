package com.rns.shwetalab.mobile.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "DENTAL";
	public static final int VERSION = 10;
	public static final String KEY_ID = "id";

	public static final String PERSON_TABLE = "person";
	public static final String PERSON_NAME = "name";
	public static final String PERSON_EMAIL = "email";
	public static final String PERSON_PHONE = "phone";
	public static final String PERSON_TYPE = "type";

	public static final String WORKTYPE_TABLE = "worktype";
	public static final String WORKTYPE_NAME = "name";
	public static final String WORKTYPE_PRICE = "default_price";
	public static final String WORKTYPE_QUANTITY = "quantity";

	public static final String WORKTYPE_PERSON_TABLE = "worktype_person";
	public static final String WORKTYPE_PERSON_PERSON_ID = "person_id";
	public static final String WORKTYPE_PERSON_WORK_ID = "work_id";
	public static final String WORKTYPE_PERSON_PRICE = "price";

	public static final String JOB_TABLE = "jobs";
	public static final String JOB_PATIENT_NAME = "patient_name";
	public static final String JOB_SHADE = "shade";
	public static final String JOB_DATE = "job_date";
	public static final String JOB_DOCTOR = "doctor_id";
	public static final String JOB_PRICE = "price";
	public static final String JOB_QUADRENT = "quadrent";
	public static final String JOB_POSITION = "position";

	public static final String JOB_WORKTYPES_TABLE = "worktypes_table";
	public static final String JOB_ID = "job_id";
	public static final String WORKTYPE_ID = "worktype_id";

	public static final String JOB_LABS_TABLE = "labs_table";
	public static final String LAB_JOB_ID = "job_id";
	public static final String LAB_ID = "lab_id";
	public static final String LAB_PRICE = "price";
	

	public static final String MATERIAL_TABLE = "dealer";
	public static final String MATERIAL_NAME = "material_name";
	public static final String MATERIAL_PRICE = "material_price";
	public static final String MATERIAL_AMOUNT_PAID = "material_amount_paid";
	public static final String MATERIAL_DATE = "material_date";
	public static final String DEALER_ID = "dealer_id";
	public static final String DEALER_NAME = "dealer_name";
	public static final String DEALER_BALANCE = "dealer_balance";

	public static final String MARKETING_TABLE = "marketing";
	public static final String MARKETING_PERSON_NAME = "person_name";
	public static final String MARKETING_DATE = "follow_up_date";
	public static final String MARKETING_CONTACT = "person_contact_no";
	public static final String MARKETING_EMAIL = "person_email_id";
	
	public static final String DESCRIPTION_TABLE = "description";
	public static final String DESC_PERSON_NAME = "person_name";
	public static final String DESCRIPTION_DATE = "follow_date";
	public static final String DESCRIPTION_DATA = "descripiotn_data";
	
	public static final String BALANCE_AMOUNT_TABLE = "balance_amount";
	public static final String BALANCE_PERSON_ID = "person_id";
	public static final String BALANCE_AMOUNT_PAID = "amount_paid";
	public static final String BALANCE_AMOUNT_MONTH = "amount_month";
	public static final String BALANCE_AMOUNT_YEAR = "amount_year";
	
	
	public static final String CREATE_TABLE_BALANCE_AMOUNT = "create table " + BALANCE_AMOUNT_TABLE + " (" + KEY_ID
			+ " integer primary key autoincrement, " + BALANCE_PERSON_ID + " integer, " + BALANCE_AMOUNT_PAID
			+ " integer," + BALANCE_AMOUNT_MONTH + " integer," + BALANCE_AMOUNT_YEAR + " integer)";
	
	public static final String CREATE_TABLE_DESCRIPTION = "create table " + DESCRIPTION_TABLE + " (" + KEY_ID
			+ " integer primary key autoincrement, " + DESC_PERSON_NAME + " text not null, " + DESCRIPTION_DATA
			+ " text not null," + DESCRIPTION_DATE + " date)";
	

	public static final String CREATE_TABLE_MARKETING = "create table " + MARKETING_TABLE + " (" + KEY_ID
			+ " integer primary key autoincrement, " + MARKETING_PERSON_NAME + " text not null," + MARKETING_DATE +
			" date," + MARKETING_CONTACT + " integer not null," + MARKETING_EMAIL + " text not null)";

	public static final String CREATE_TABLE_MATERIAL = "create table " + MATERIAL_TABLE + "(" + KEY_ID
			+ " integer primary key autoincrement, " + MATERIAL_NAME + " text not null, " + MATERIAL_PRICE + " integer,"
			+ MATERIAL_AMOUNT_PAID + " integer, " + MATERIAL_DATE + " date," + DEALER_ID + " integer not null," + DEALER_NAME + " text not null," + DEALER_BALANCE + " integer)";

	public static final String CREATE_TABLE_JOB_WORKTYPES = "create table " + JOB_WORKTYPES_TABLE + " (" + KEY_ID
			+ " integer primary key autoincrement, " + JOB_ID + " integer not null, " + WORKTYPE_ID
			+ " integer not null)";
	
	public static final String CREATE_TABLE_LAB_WORKTYPES = "create table " + JOB_LABS_TABLE + " (" + KEY_ID
			+ " integer primary key autoincrement, " + LAB_JOB_ID + " integer not null, " + LAB_ID
			+ " integer not null, " + LAB_PRICE + " integer)";

	public static final String CREATE_TABLE_PERSON = "create table " + PERSON_TABLE + " (" + KEY_ID
			+ " integer primary key autoincrement, " + PERSON_NAME + " text not null, " + PERSON_EMAIL
			+ " text not null, " + PERSON_PHONE + " text not null, " + PERSON_TYPE + " text not null)";

	public static final String CREATE_TABLE_WORK_TYPE = "create table " + WORKTYPE_TABLE + " (" + KEY_ID
			+ " integer primary key autoincrement, " + WORKTYPE_NAME + " text not null, " + WORKTYPE_PRICE
			+ " integer, " + WORKTYPE_QUANTITY + " integer)";

	public static final String CREATE_TABLE_WORK_TYPE_PERSON = "create table " + WORKTYPE_PERSON_TABLE + " (" + KEY_ID
			+ " integer primary key autoincrement, " + WORKTYPE_PERSON_PERSON_ID + " integer ,"
			+ WORKTYPE_PERSON_WORK_ID + " integer, " + WORKTYPE_PERSON_PRICE + " decimal)";

	public static final String CREATE_TABLE_JOB = "create table " + JOB_TABLE + " (" + KEY_ID
			+ " integer primary key autoincrement, " + JOB_DOCTOR + " integer , " + JOB_PATIENT_NAME
			+ " text not null, " + JOB_SHADE + " integer, " + JOB_DATE + " date, " + JOB_PRICE + " integer,"
			+ JOB_QUADRENT + " integer," + JOB_POSITION + " integer)";

	public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_PERSON);
		db.execSQL(CREATE_TABLE_WORK_TYPE);
		db.execSQL(CREATE_TABLE_WORK_TYPE_PERSON);
		db.execSQL(CREATE_TABLE_JOB);
		db.execSQL(CREATE_TABLE_MARKETING);
		db.execSQL(CREATE_TABLE_MATERIAL);
		db.execSQL(CREATE_TABLE_LAB_WORKTYPES);
		db.execSQL(CREATE_TABLE_JOB_WORKTYPES);
		db.execSQL(CREATE_TABLE_DESCRIPTION);
		db.execSQL(CREATE_TABLE_BALANCE_AMOUNT);
		Log.d(DATABASE_NAME, "Tables Created!!");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		/*
		 * db.execSQL("drop table " + PERSON_TABLE); db.execSQL("drop table " +
		 * WORKTYPE_TABLE); db.execSQL("drop table " + WORKTYPE_PERSON_TABLE);
		 * db.execSQL("drop table " + JOB_TABLE); onCreate(db);
		 */
		db.execSQL("ALTER TABLE " + MATERIAL_TABLE + " ADD COLUMN " + DEALER_BALANCE +
		 " integer");
		//db.execSQL(CREATE_TABLE_LAB_WORKTYPES);
		//db.execSQL(CREATE_TABLE_JOB_WORKTYPES);

		Log.d(DATABASE_NAME, "Upgrading the database from :" + oldVersion + " to :" + newVersion);
	}

}
