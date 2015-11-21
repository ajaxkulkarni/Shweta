package com.rns.shwetalab.mobile.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rns.shwetalab.mobile.AddDate;
import com.rns.shwetalab.mobile.R;

public class AddDateListAdapter extends BaseAdapter 
{

	private ArrayList<String> date;
	Activity context;
	LayoutInflater inflater;


	public AddDateListAdapter(AddDate addDate,
			ArrayList<String> objArrayListDate) 
	{
		this.context = addDate ;
		inflater = LayoutInflater.from(context);
		this.date = objArrayListDate;

	}

	@Override
	public int getCount() 
	{
		// TODO Auto-generated method stub
		return date.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public class ViewHolder
	{
		TextView tv1;

	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) 
	{
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		View view = arg1;

		if(view == null)
		{
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.activity_datelist_adapter, null);
			holder.tv1 = (TextView)view.findViewById(R.id.addDateAdapter_textview);

			view.setTag(holder);

		}

		else
			holder = (ViewHolder)view.getTag();

		holder.tv1.setText(date.get(arg0));


		// TODO Auto-generated method stub
		return view;
	}

}
