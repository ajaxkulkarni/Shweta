package com.rns.shwetalab.mobile.adapter;

import java.util.ArrayList;
import java.util.List;

import com.rns.shwetalab.mobile.MarketingList;
import com.rns.shwetalab.mobile.R;
import com.rns.shwetalab.mobile.adapter.JobListAdapter.ViewHolder;
import com.rns.shwetalab.mobile.domain.Marketing;
import com.rns.shwetalab.mobile.domain.WorkType;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MarketingAdapter extends BaseAdapter 
{
	 private List<Marketing> name;
	    Activity context;
	    LayoutInflater inflater;

	
	public MarketingAdapter(MarketingList marketingList, List<Marketing> list) 
	{
	
		this.context = marketingList;
		this.inflater = LayoutInflater.from(context);
		this.name = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return name.size();
	}

	@Override
	public Object getItem(int position) 
	{
		if(name == null || name.size() == 0) {
			return null;
		}		
		return name.get(position);
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
	        ViewHolder holder = null;
	        View view = arg1;

	        if(view == null)
	        {
	            holder = new ViewHolder();
	            view = inflater.inflate(R.layout.activity_marketing_list_adapter, null);
	            holder.tv1 = (TextView)view.findViewById(R.id.nametextView);
	            view.setTag(holder);
	        }
	        else
	            holder = (ViewHolder)view.getTag();
	        	holder.tv1.setText(name.get(arg0).getMarketing_name());
	        return view;

	    }
	

}
