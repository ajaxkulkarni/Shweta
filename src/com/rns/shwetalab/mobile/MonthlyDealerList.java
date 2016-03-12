package com.rns.shwetalab.mobile;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.rns.shwetalab.mobile.adapter.DealersListAdapter;
import com.rns.shwetalab.mobile.db.DealerDao;
import com.rns.shwetalab.mobile.domain.Dealer;
import com.rns.shwetalab.mobile.domain.Job;

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

		getDealerMaterialPrice(personType, dealers);

	}

	private void getDealerMaterialPrice(String personType, List<Dealer> dealers) {
		if(dealers == null || dealers.size() == 0) {
			return;
		}
		for (Dealer dealer : dealers) {
			if (dealer.getName() == null || dealer.getPrice() == null || !personType.equals(dealer.getDealer().getWorkType())) {
				continue;
			}
			calculatePrice(dealer);
		}
	}
	
	
	private void calculatePrice( Dealer dealers) 
	{
//		BigDecimal total = BigDecimal.ZERO;
//		for (Dealer dealer : dealers) {
//			if (dealer.getDealer() == null || dealer.getPrice() == null
//					|| !personType.equals(dealer.getDealer().getWorkType())) {
//				continue;
//			}
//			if (dealer.getDealer().getName() == null) {
//				map.put(dealer.getDealer().getName(), dealer.getBalance());
//			} 
//			else 
//			{
//				total = new BigDecimal(dealer.getDealer().getName());
//				if (total == null) {
//					continue;
//				}
//				map.put(dealer.getDealer().getName(), total.add(dealer.getBalance()));
//			}
//		}
		
		BigDecimal total = BigDecimal.ZERO;
		if (map.get(dealers.getDealer().getName()) == null) {
			map.put(dealers.getDealer().getName(), dealers.getPrice());
		} else {
			total = map.get(dealers.getDealer().getName());
			if (total == null) {
				return;
			}
			map.put(dealers.getDealer().getName(), total.add(dealers.getPrice()));
		}
	}
		
		

}
