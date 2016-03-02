package com.rns.shwetalab.mobile.db;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.rns.shwetalab.mobile.domain.Dealer;
import com.rns.shwetalab.mobile.domain.FollowUpMessage;
import com.rns.shwetalab.mobile.domain.Person;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.EventLogTags.Description;

public class DescriptionDao {

	private SQLiteDatabase followUpDb;
	private DatabaseHelper followUpHelper;
	private Context context;

	private static String[] cols = { DatabaseHelper.KEY_ID, DatabaseHelper.DESC_PERSON_NAME,
			DatabaseHelper.DESCRIPTION_DATA, DatabaseHelper.DESCRIPTION_DATE };

	public DescriptionDao(Context c) {
		context = c;

	}

	public void openToRead() {
		followUpHelper = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.VERSION);
		followUpDb = followUpHelper.getReadableDatabase();
	}

	public void openToWrite() {
		followUpHelper = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.VERSION);
		followUpDb = followUpHelper.getWritableDatabase();
	}

	public void Close() {
		followUpDb.close();
	}

	public long insertDetails(FollowUpMessage description) {

		description.setName(description.getName());
		;
		description.setDescription(description.getDescription());
		description.setDate(description.getDate());
		openToWrite();
		long val = followUpDb.insert(DatabaseHelper.DESCRIPTION_TABLE, null, prepareContentValues(description));
		Close();
		return val;
	}

	private ContentValues prepareContentValues(FollowUpMessage description) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHelper.DESC_PERSON_NAME, description.getName());
		contentValues.put(DatabaseHelper.DESCRIPTION_DATA, description.getDescription());
		contentValues.put(DatabaseHelper.DESCRIPTION_DATE, CommonUtil.convertDate(description.getDate()));
		return contentValues;

	}

	public List<FollowUpMessage> getPersonName(String name) {
		List<FollowUpMessage> followup = iterateMaterial(queryByName(name));
		List<FollowUpMessage> mesgs = new ArrayList<FollowUpMessage>();
		for (FollowUpMessage follow : followup) {
			if (follow.getName() != null) {
				mesgs.add(follow);
			}
		}
		return followup;
	}

	public Cursor queryByName(String name) {
		openToWrite();
		return followUpDb.query(DatabaseHelper.DESCRIPTION_TABLE, cols,
				DatabaseHelper.DESC_PERSON_NAME + " like '" + name + "'", null, null, null, null);
	}

	private List<FollowUpMessage> iterateMaterial(Cursor cursor) {
		List<FollowUpMessage> mesgs = new ArrayList<FollowUpMessage>();
		if (cursor.moveToFirst()) {
			do {
				FollowUpMessage follow = new FollowUpMessage();
				follow.setId(Integer.parseInt(cursor.getString(0)));
				follow.setName(cursor.getString(1));
				follow.setDescription(cursor.getString(2));
				follow.setDate(CommonUtil.convertDate(cursor.getString(3)));
				mesgs.add(follow);
			} while (cursor.moveToNext());
		}
		return mesgs;
	}
}
