package com.rns.shwetalab.mobile.db;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rns.shwetalab.mobile.domain.Marketing;

@SuppressLint("NewApi")
public class MarketingDao {
	private SQLiteDatabase marketingDb;
	private DatabaseHelper dealerHelper;
	private Context context;

	private static String[] cols = { DatabaseHelper.KEY_ID, DatabaseHelper.MARKETING_PERSON_NAME,DatabaseHelper.MARKETING_DATE,
			DatabaseHelper.MARKETING_CONTACT,DatabaseHelper.MARKETING_EMAIL };

	public MarketingDao(Context c) {
		context = c;
	}

	public void openToRead() {
		dealerHelper = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.VERSION);
		marketingDb = dealerHelper.getReadableDatabase();
	}

	public void openToWrite() {
		dealerHelper = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.VERSION);
		marketingDb = dealerHelper.getWritableDatabase();
	}

	public void Close() {
		marketingDb.close();
	}

	public long insertDetails(Marketing marketing) {
		ContentValues contentValues = prepareContentValues(marketing);

		openToWrite();
		long val = marketingDb.insert(DatabaseHelper.MARKETING_TABLE, null, contentValues);
		Log.d(DatabaseHelper.DATABASE_NAME, "Person inserted!! Result :" + val);
		Close();
		return val;

	}
	private ContentValues prepareContentValues(Marketing marketing) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHelper.MARKETING_PERSON_NAME, marketing.getMarketing_name());
		contentValues.put(DatabaseHelper.MARKETING_DATE, CommonUtil.convertDate(marketing.getDate()));
		contentValues.put(DatabaseHelper.MARKETING_CONTACT, marketing.getContact());
		contentValues.put(DatabaseHelper.MARKETING_EMAIL, marketing.getEmail());
		return contentValues;

	}
	public List<Marketing> getAll() {
		openToWrite();
		Cursor cursor = marketingDb.query(DatabaseHelper.MARKETING_TABLE, cols, null, null, null, null, null, null);
		//	Cursor cursor = marketingDb.query(true, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit, cancellationSignal)(DatabaseHelper.MARKETING_TABLE, cols, null, null, null, null, null);
		Log.d(DatabaseHelper.DATABASE_NAME, "Records retreived:" + cursor.getCount());
		return iterateMarketingName(cursor);
	}

	public List<Marketing>  queryForAll() {
		openToWrite();
		Cursor cursor = marketingDb.query(DatabaseHelper.MARKETING_TABLE, cols, null, null, null, null, null);

		return iterateMarketingName(cursor);
	}

	public Cursor queryByName (String name) {
		openToWrite();
		return marketingDb.query(true,DatabaseHelper.MARKETING_TABLE, cols, DatabaseHelper.MARKETING_PERSON_NAME + " like '" + name + "'", null, null,
				null, null, null, null);
	}

	public List<Marketing> getMarketingName(String name) {
		List<Marketing> market = iterateMarketingName(queryByName(name));
		List<Marketing> marketing = new ArrayList<Marketing>();
		for (Marketing markets : market) {
			if (markets.getMarketing_name() != null) {
				marketing.add(markets);
			}
		}
		return market;
	}


	private List<Marketing> iterateMarketingName(Cursor cursor) {
		List<Marketing> marketings = new ArrayList<Marketing>();
		if (cursor.moveToFirst()) {
			do {
				Marketing marketing = new Marketing();
				marketing.setId(Integer.parseInt(cursor.getString(0)));
				marketing.setMarketing_name(cursor.getString(1));
				//marketing.setDate(CommonUtil.convertDate(cursor.getString(2)));
				marketing.setDate(CommonUtil.convertDate(cursor.getString(2)));
				marketing.setContact(cursor.getString(3));
				marketing.setEmail(cursor.getString(4));
				marketings.add(marketing);
			} while (cursor.moveToNext());
		}
		return marketings;
	}
}
