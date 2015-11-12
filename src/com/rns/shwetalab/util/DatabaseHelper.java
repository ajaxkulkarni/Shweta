package com.rns.shwetalab.util;

import java.io.File;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	

	public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, "/sdcard" + "/" + "dental.db", factory, version);
		//SQLiteDatabase.openOrCreateDatabase(prepareDbDirectory(), null);
		//getWritableDatabase().openDatabase(path, factory, flags)
		Toast.makeText(context, "Created!!", Toast.LENGTH_LONG);
	}

	private static File prepareDbDirectory() {
		File path = new File(Environment.getExternalStorageDirectory() + "/shweta-labs/Db");
		if(!path.exists()) {
			path.mkdirs();
		}
		return path;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE person (name  VARCHAR (50),email VARCHAR (50),phone VARCHAR (15),type  VARCHAR (10),id INTEGER,PRIMARY KEY (id));");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
	/*@Override
	public SQLiteDatabase getWritableDatabase() {
		SQLiteDatabase db = SQLiteDatabase.openDatabase("/dental", null, SQLiteDatabase.OPEN_READWRITE);
		return db;
	}*/

}
