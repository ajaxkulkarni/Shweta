package com.rns.shwetalab.mobile.db;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rns.shwetalab.mobile.ViewMonth;
import com.rns.shwetalab.mobile.domain.Dealer;
import com.rns.shwetalab.mobile.domain.Job;
import com.rns.shwetalab.mobile.domain.Marketing;
import com.rns.shwetalab.mobile.domain.Person;
import com.rns.shwetalab.mobile.domain.WorkType;

import android.annotation.SuppressLint;
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
	private PersonDao personDao;
	private static String[] cols = { DatabaseHelper.KEY_ID, DatabaseHelper.MATERIAL_NAME, DatabaseHelper.MATERIAL_PRICE, 
			DatabaseHelper.MATERIAL_AMOUNT_PAID,DatabaseHelper.MATERIAL_DATE,DatabaseHelper.DEALER_ID,DatabaseHelper.DEALER_NAME,DatabaseHelper.DEALER_BALANCE };

	public DealerDao(Context c) {
		context = c;
		personDao = new PersonDao(context);
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

	public long insertDetails(Dealer dealer) 
	{
		Person dealers = personDao.getPerson(dealer.getDealer());
		if (dealers == null) {
			return -10;
		}
		dealer.setDealer(dealers);
		dealer.setMaterial(dealer.getMaterial());
		dealer.setDate(dealer.getDate());
		dealer.setAmount_paid(dealer.getAmount_paid());
		dealer.setPrice(dealer.getPrice());
		dealer.setName(dealer.getName());
		dealer.setBalance(dealer.getBalance());
		openToWrite();
		long val = dealerDb.insert(DatabaseHelper.MATERIAL_TABLE, null, prepareContentValues(dealer));
		Close();
		return val;

	}


	private ContentValues prepareContentValues(Dealer dealer) 
	{
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHelper.MATERIAL_NAME, dealer.getMaterial());
		contentValues.put(DatabaseHelper.MATERIAL_PRICE, dealer.getPrice().toString());
		contentValues.put(DatabaseHelper.MATERIAL_AMOUNT_PAID, dealer.getAmount_paid().toString());
		contentValues.put(DatabaseHelper.MATERIAL_DATE, CommonUtil.convertDate(dealer.getDate()));
		contentValues.put(DatabaseHelper.DEALER_ID, dealer.getDealer().getId() );
		contentValues.put(DatabaseHelper.DEALER_NAME, dealer.getName().toString());
		contentValues.put(DatabaseHelper.DEALER_BALANCE,dealer.getBalance().toString());
		return contentValues;

	}

	public List<Dealer> getAll() {
		openToWrite();
		Cursor cursor = dealerDb.query(DatabaseHelper.MATERIAL_TABLE, cols, null, null, null, null, null);
		Log.d(DatabaseHelper.DATABASE_NAME, "Records retreived:" + cursor.getCount());
		return iterateMaterial(cursor);
	}

	private List<Dealer> iterateMaterial(Cursor cursor) {
		List<Dealer> dealers = new ArrayList<Dealer>();
		if (cursor.moveToFirst()) {
			do {
				Dealer dealer = new Dealer();
				dealer.setId(Integer.parseInt(cursor.getString(0)));
				dealer.setMaterial(cursor.getString(1));
				dealer.setPrice(new BigDecimal(cursor.getString(2)));
				dealer.setAmount_paid(new BigDecimal(cursor.getString(3)));
				dealer.setDate(CommonUtil.convertDate(cursor.getString(4)));
				Person person = personDao.getPerson(cursor.getInt(5));
				dealer.setName(cursor.getString(6));
				dealer.setBalance(new BigDecimal(cursor.getString(7)));
				dealer.setDealer(person);
				dealers.add(dealer);
			} while (cursor.moveToNext());
		}
		return dealers;
	}


	public long updateAmount(Dealer dealer) {
		if (dealer == null) {
			return -10;
		}
		openToWrite();
		return dealerDb.update(DatabaseHelper.MATERIAL_TABLE, prepareContentValues(dealer), DatabaseHelper.KEY_ID + " = ?", new String[] { String.valueOf(dealer.getId()) });
	}


	public List<Dealer> getDealerName(String name) {
		List<Dealer> dealer = iterateMaterial(queryByName(name));
		List<Dealer> materilas = new ArrayList<Dealer>();
		for (Dealer dealers : dealer) {
			if (dealers.getDealer().getName() != null) {
				materilas.add(dealers);
			}
		}
		return dealer;
	}
	@SuppressLint("NewApi")
	public Cursor queryByName (String name) {
		openToWrite();
		return dealerDb.query(true,DatabaseHelper.MATERIAL_TABLE, cols, DatabaseHelper.DEALER_NAME + " like '" + name + "'", null, null,
				null, null, null, null);
	}


	public BigDecimal getIncomeForMonth(String month, String personType) {
		// int value = Integer.parseInt(month);
		BigDecimal total = BigDecimal.ZERO;
		List<Dealer> jobs = getJobsByMonth(month);
		for (Dealer job : jobs) {
			if (job.getPrice() == null || job.getDealer() == null
					|| !personType.equals(job.getDealer().getWorkType())) {
				continue;
			}
			total = total.add(job.getPrice());
		}
		return total;
	}
	public List<Dealer> getJobsByMonth(String month) {
		List<Dealer> dealers = iterateMaterial(queryForMonth(month));
		List<Dealer> jobsByType = new ArrayList<Dealer>();
		for (Dealer dealer : dealers) {
			if (dealer.getDealer() != null){
				jobsByType.add(dealer);
			}
		}
		return dealers;
	}
	private Cursor queryForMonth(String month) {
		openToWrite();
		return dealerDb.query(DatabaseHelper.MATERIAL_TABLE, cols,
				DatabaseHelper.MATERIAL_DATE + " LIKE '%-" + ViewMonth.months().get(month) + "-2016'", null, null, null,
				null);
	}

}
