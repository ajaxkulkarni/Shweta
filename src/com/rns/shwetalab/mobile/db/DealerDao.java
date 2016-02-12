package com.rns.shwetalab.mobile.db;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.rns.shwetalab.mobile.domain.Dealer;
import com.rns.shwetalab.mobile.domain.Person;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DealerDao 
{
	
	private SQLiteDatabase dealerDb;
	private DatabaseHelper dealerHelper;
	private Context context;
	private static String[] cols = { DatabaseHelper.KEY_ID, DatabaseHelper.DEALER_NAME, DatabaseHelper.DEALER_MATERIAL, DatabaseHelper.DEALER_PRICE, DatabaseHelper.DEALER_AMOUNT_PAID };

	public DealerDao(Context c) {
		context = c;
	}

	public void openToRead() {
		dealerHelper = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.VERSION);
		dealerDb = dealerHelper.getReadableDatabase();
	}

	public void openToWrite() {
		dealerHelper = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.VERSION);
		dealerDb = dealerHelper.getWritableDatabase();
	}

	public void Close() {
		dealerDb.close();
	}

	public long insertDetails(Dealer dealer) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHelper.DEALER_NAME, dealer.getDealer_name());
		contentValues.put(DatabaseHelper.DEALER_MATERIAL, dealer.getMaterial());
		contentValues.put(DatabaseHelper.DEALER_PRICE, dealer.getPrice().toString());
		contentValues.put(DatabaseHelper.DEALER_AMOUNT_PAID, dealer.getAmount_paid().toString());
		openToWrite();
		long val = dealerDb.insert(DatabaseHelper.DEALER_TABLE, null, contentValues);
		Log.d(DatabaseHelper.DATABASE_NAME, "Dealer inserted!! Result :" + val);
		Close();
		return val;

	}
	
	public Cursor queryByName(String name) {
		openToWrite();
		return dealerDb.query(DatabaseHelper.DEALER_TABLE, cols, DatabaseHelper.DEALER_NAME + " like '" + name + "'", null, null,
				null, null);
	}
	
	public Dealer  getDealer(Dealer dealer) {
		if (dealer == null || dealer.getDealer_name() == null) {
			return null;
		}
		Cursor c = queryByName(dealer.getDealer_name());
		List<Dealer> dealers = iteratePersonCursor(c);
		if(dealers == null || dealers.size() == 0) {
			return null;
		}
		return dealers.get(0);

	}
	
	public Dealer getDealer(Integer id) {
		Cursor c = queryById(id);
		List<Dealer> dealer = iteratePersonCursor(c);
		if(dealer == null || dealer.size() == 0) {
			return null;
		}
		return dealer.get(0);

	}
	
	private Cursor queryById(Integer id) {
		openToWrite();
		return dealerDb.query(DatabaseHelper.DEALER_TABLE, cols, DatabaseHelper.KEY_ID + " = " + id , null, null,null, null);
	}
	
	public long updateldetail(int rowId, String material, String price) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHelper.DEALER_NAME, material);
		contentValues.put(DatabaseHelper.DEALER_PRICE, price);
		openToWrite();
		long val = dealerDb.update(DatabaseHelper.DEALER_TABLE, contentValues, DatabaseHelper.KEY_ID + "=" + rowId,
				null);
		Close();
		return val;
	}

	public int deletOneRecord(int rowId) {
		openToWrite();
		int val = dealerDb.delete(DatabaseHelper.DEALER_TABLE, DatabaseHelper.KEY_ID + "=" + rowId, null);
		Close();
		return val;
	}
	
	public String[] getDoctorNames() {
		List<Dealer> dealers = getAllPeopleByType(CommonUtil.TYPE_DEALER);
		if (dealers.size() == 0) {
			return new String[0];
		}
		List<String> namesList = new ArrayList<String>();
		for (Dealer dealer : dealers) {
			namesList.add(dealer.getDealer_name());
		}
		String[] names = new String[namesList.size()];
		names = namesList.toArray(new String[0]);
		return names;
	}
	
	public List<Dealer> getAllPeopleByType(String type) {
		openToRead();
		return iteratePersonCursor(dealerDb.query(DatabaseHelper.DEALER_TABLE, cols, DatabaseHelper.DEALER_TYPE + " like '" + type + "'", null, null,null, null));
	}

	private List<Dealer> iteratePersonCursor(Cursor cursor) {
		List<Dealer> dealers = new ArrayList<Dealer>();
		if (cursor.moveToFirst()) {
			do {
				Dealer dealer = new Dealer();
				dealer.setId(Integer.parseInt(cursor.getString(0)));
				dealer.setDealer_name(cursor.getString(1));
				dealer.setMaterial(cursor.getString(2));
				dealer.setPrice(new BigDecimal(cursor.getString(3)));
				dealer.setAmount_paid(new BigDecimal(cursor.getString(4)));
				dealers.add(dealer);
			} while (cursor.moveToNext());
		}

		return dealers;
	}
	
	
}
