package com.rns.shwetalab.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class PersonDao {
	
	private DatabaseHelper dbHelper;
	private SQLiteDatabase db;
	
	public PersonDao(Context context) {
		dbHelper = new DatabaseHelper(context, null, null, 1);
		/*db = dbHelper.getWritableDatabase();
		db.execSQL("insert into person(name,email,phone,type)  values('Ajinkya','ajinkyashiva@gnail.com','1212123123','Doctor')");*/
		Log.d("Dental", "Inserted into DB!!!!!");
		Toast.makeText(context, "Created!!!!", Toast.LENGTH_LONG);
		//db.close();
	}
	
	

}
