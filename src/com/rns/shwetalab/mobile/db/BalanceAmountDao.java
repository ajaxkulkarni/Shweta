package com.rns.shwetalab.mobile.db;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.rns.shwetalab.mobile.domain.Balance_Amount;
import com.rns.shwetalab.mobile.domain.Balance_Amount;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BalanceAmountDao 
{
	
	private SQLiteDatabase balance_amountDb;
	private DatabaseHelper balance_amountHelper;
	private Context context;
	private static String[] cols = { DatabaseHelper.KEY_ID, DatabaseHelper.BALANCE_PERSON_ID, DatabaseHelper.BALANCE_AMOUNT_PAID, 
			DatabaseHelper.BALANCE_AMOUNT_MONTH,DatabaseHelper.BALANCE_AMOUNT_YEAR};
	
	
	public BalanceAmountDao(Context c) {
		context = c;
	}

	
	public void openToRead() {
		balance_amountHelper = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.VERSION);
		balance_amountDb = balance_amountHelper.getReadableDatabase();
	}

	public void openToWrite() {
		balance_amountHelper = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.VERSION);
		balance_amountDb = balance_amountHelper.getWritableDatabase();
	}

	public void Close() {
		balance_amountDb.close();
	}
	
	
	
	public long insertDetails(Balance_Amount balance_Amount) 
	{
		balance_Amount.setPerson_id(balance_Amount.getPerson_id());
		balance_Amount.setAmount_paid(balance_Amount.getAmount_paid());
		balance_Amount.setMonth(balance_Amount.getMonth());
		balance_Amount.setYear(balance_Amount.getYear());
		openToWrite();
		long val = balance_amountDb.insert(DatabaseHelper.BALANCE_AMOUNT_TABLE, null, prepareContentValues(balance_Amount));
		Close();
		return val;
	}
	
	private ContentValues prepareContentValues(Balance_Amount balance_Amount) 
	{
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHelper.BALANCE_PERSON_ID, balance_Amount.getPerson_id());
		contentValues.put(DatabaseHelper.BALANCE_AMOUNT_PAID, balance_Amount.getAmount_paid());
		contentValues.put(DatabaseHelper.BALANCE_AMOUNT_MONTH, balance_Amount.getMonth());
		contentValues.put(DatabaseHelper.BALANCE_AMOUNT_YEAR, balance_Amount.getYear() );
		return contentValues;

	}
	
	
	public long updateAmount(Balance_Amount balance_Amount) {
		if (balance_Amount == null) {
			return -10;
		}
		openToWrite();
		return balance_amountDb.update(DatabaseHelper.BALANCE_AMOUNT_TABLE, prepareContentValues(balance_Amount), DatabaseHelper.BALANCE_PERSON_ID + " = ?", new String[] { String.valueOf(balance_Amount.getPerson_id()) });
	}

	public List<Balance_Amount> getAll() {
		openToWrite();
		Cursor cursor = balance_amountDb.query(DatabaseHelper.BALANCE_AMOUNT_TABLE, cols, null, null, null, null, null);
		Log.d(DatabaseHelper.DATABASE_NAME, "Records retreived:" + cursor.getCount());
		return iterateMaterial(cursor);
	}
	
	private List<Balance_Amount> iterateMaterial(Cursor cursor) {
		List<Balance_Amount> balance = new ArrayList<Balance_Amount>();
		if (cursor.moveToFirst()) {
			do {
				Balance_Amount balance_Amount = new Balance_Amount();
			//	balance_Amount.setK
				balance_Amount.setId(cursor.getInt(0));
				balance_Amount.setPerson_id(cursor.getInt(1));
				balance_Amount.setAmount_paid(cursor.getInt(2));
				balance_Amount.setMonth(cursor.getInt(3));
				balance_Amount.setYear(cursor.getInt(4));
				balance.add(balance_Amount);
			} while (cursor.moveToNext());
		}
		return balance;
	}
	
	public List<Balance_Amount> getDealerName(int id) {
		List<Balance_Amount> balance = iterateMaterial(queryByName(id));
		List<Balance_Amount> amount = new ArrayList<Balance_Amount>();
		for (Balance_Amount dealers : balance) {
			if (dealers.getPerson_id() != 0) {
				amount.add(dealers);
			}
		}
		return balance;
	}
	
	public Cursor queryByName (int id) {
		openToWrite();
		return balance_amountDb.query(DatabaseHelper.BALANCE_AMOUNT_TABLE, cols, DatabaseHelper.BALANCE_PERSON_ID + " = '" + id + "'", null, null,
				null, null);
	}
	
}
