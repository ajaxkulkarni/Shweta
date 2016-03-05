package com.rns.shwetalab.mobile.adapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.rns.shwetalab.mobile.DoctorMonthlyBalanceList;
import com.rns.shwetalab.mobile.LabMonthlyBalanceList;
import com.rns.shwetalab.mobile.R;
import com.rns.shwetalab.mobile.domain.Job;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LabListAdapter extends BaseAdapter
{
	
	private Map<String, BigDecimal> name;
	Activity context;
	private final ArrayList mData ;

	private List<Job> jobs;
	LayoutInflater inflater;

//	public DoctorListAdapter(DoctorMonthlyBalanceList doctor_List_Amount, Map<String, BigDecimal> map)
//	{
//		mData = new ArrayList();
//		this.context = doctor_List_Amount;
//		inflater = LayoutInflater.from(context);
//		this.mData.addAll(map.entrySet());
//	}




	//	public DoctorListAdapter(DoctorMonthlyBalanceList doctorMonthlyBalanceList, ArrayList<String> objname, ArrayList<String> objprice) 
	//	{
	//		this.context = doctorMonthlyBalanceList;
	//		this.inflater = LayoutInflater.from(context);
	//		this.name = objname;
	//		this.price = objprice;
	//	}

	public LabListAdapter(LabMonthlyBalanceList labMonthlyBalanceList, Map<String, BigDecimal> map) 
	{
		mData = new ArrayList();
		this.context = labMonthlyBalanceList;
		inflater = LayoutInflater.from(context);
		this.mData.addAll(map.entrySet());
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
			result = LayoutInflater.from(arg2.getContext()).inflate(R.layout.activity_doctorlist_adapter, arg2, false);
		} else {
			result = arg1;
		}
		Map.Entry<String, BigDecimal> item = getItem(arg0);
		((TextView) result.findViewById(R.id.doctorListName_textView)).setText(item.getKey());
		((TextView) result.findViewById(R.id.doctorListAmount_textView)).setText(item.getValue().toString());
		return result;
	}


}
