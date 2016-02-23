package com.rns.shwetalab.mobile.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.rns.shwetalab.mobile.MarketingDescriptionList;
import com.rns.shwetalab.mobile.MarketingList;
import com.rns.shwetalab.mobile.R;
import com.rns.shwetalab.mobile.adapter.MarketingAdapter.ViewHolder;
import com.rns.shwetalab.mobile.domain.Marketing;

import android.app.Activity;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MarketingDescriptionListAdapter extends BaseAdapter 
{
	private List<Marketing> marketing;
	Activity context;
	private final ArrayList mData ;
	LayoutInflater inflater;
	private Map<Date, String> name;
	
	@SuppressWarnings("unchecked")
	public MarketingDescriptionListAdapter(MarketingDescriptionList marketingDescriptionList, Map<Date, String> map) 
	{
		mData = new ArrayList();
		this.context = marketingDescriptionList;
		this.inflater = LayoutInflater.from(context);
		this.mData.addAll(map.entrySet());
		
	}
	
	@Override
	public Entry<Date, String> getItem(int position) {
		return (Map.Entry) mData.get(position);
	}

	
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
        View view = convertView;
        if(view == null)
        {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.activity_marketing_description_list_adapter, null);
            holder.tv1 = (TextView)view.findViewById(R.id.description_textView);
            holder.tv2 = (TextView)view.findViewById(R.id.marketdate_textView);
            view.setTag(holder);
        }
        else
        {
        	view = convertView;
        }
        Map.Entry<Date, String> item = getItem(position);
        holder = (ViewHolder)view.getTag();
    	holder.tv1.setText(item.getValue().toString());
    	holder.tv2.setText(item.getKey().toString());
        return view;
	}


}
