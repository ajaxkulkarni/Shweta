package com.rns.shwetalab.mobile;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rns.shwetalab.mobile.adapter.DoctorListAdapter;
import com.rns.shwetalab.mobile.db.CommonUtil;
import com.rns.shwetalab.mobile.db.DealerDao;
import com.rns.shwetalab.mobile.db.JobLabMapDao;
import com.rns.shwetalab.mobile.db.JobsDao;
import com.rns.shwetalab.mobile.domain.Dealer;
import com.rns.shwetalab.mobile.domain.Job;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MonthlyDealerList extends Activity 
{
	DealerDao dealerDao;
	ListView lv;
	private Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_monthly_dealer_list);
		Bundle extras = getIntent().getExtras();
		final String month = extras.getString("Month");
		prepareListData(month);
		lv = (ListView) findViewById(R.id.monthlydealerslistView);
		DealersListAdapter Adapter = new DealersListAdapter(this, map);
		lv.setAdapter(Adapter);

	}
	
	
	private void prepareListData(String month) {
		String personType = getIntent().getStringExtra("type");
		dealerDao = new DealerDao(this);
		
		List<Dealer> dealers = dealerDao.getJobsByMonth(month);
		map = new HashMap<String, BigDecimal>();
		
			getDoctorsJobPrice(personType, dealers);
		
	}
	
	private void getDoctorsJobPrice(String personType, List<Dealer> dealers) 
	{
		BigDecimal total = BigDecimal.ZERO;
		for (Dealer dealer : dealers) {
			if (dealer.getDealer() == null || dealer.getPrice() == null
					|| !personType.equals(dealer.getDealer().getWorkType())) {
				continue;
			}
			if (map.get(dealer.getDealer().getName()) == null) {
				map.put(dealer.getDealer().getName(), dealer.getPrice());
			} else {
				total = map.get(dealer.getDealer().getName());
				if (total == null) {
					continue;
				}
				map.put(dealer.getDealer().getName(), total.add(dealer.getPrice()));
			}
		}
	}
	
}
