package com.rns.shwetalab.mobile.adapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.rns.shwetalab.mobile.DoctorMonthlyBalanceList;
import com.rns.shwetalab.mobile.MonthlyDealerList;
import com.rns.shwetalab.mobile.R;
import com.rns.shwetalab.mobile.domain.Job;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DealersListAdapter extends BaseAdapter 
{
	private Map<String, BigDecimal> name;
	Activity context;
	private final ArrayList mData ;

	private List<Job> jobs;
	LayoutInflater inflater;

	public DealersListAdapter(MonthlyDealerList monthlyDealerList, Map<String, BigDecimal> map) 
	{
		this.context = monthlyDealerList;
		this.name =map;
		mData = new ArrayList();
		inflater = LayoutInflater.from(context);
	}



	@Override
	public Entry<String, BigDecimal> getItem(int position) {
		return (Map.Entry) mData.get(position);
		//return name.size();
	}



	//	@Override
	//	public Object getItem(int position) {
	//		// TODO Auto-generated method stub
	//		return null;
	//	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}


	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public class ViewHolder
	{
		TextView tv1,tv2;
	}


	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2)
	{
		final View result;
		if (arg1 == null) {
			result = LayoutInflater.from(arg2.getContext()).inflate(R.layout.activity_dealerlist_adapter, arg2, false);
		} else {
			result = arg1;
		}
		Map.Entry<String, BigDecimal> item = getItem(arg0);
		((TextView) result.findViewById(R.id.dealersnametextView)).setText(item.getKey());
		((TextView) result.findViewById(R.id.dealerbalancetextView)).setText(item.getValue().toString());
		return result;
	}
}
