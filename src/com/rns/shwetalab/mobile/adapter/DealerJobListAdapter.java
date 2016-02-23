package com.rns.shwetalab.mobile.adapter;

import java.util.ArrayList;
import java.util.List;

import com.rns.shwetalab.mobile.DealerJobsList;
import com.rns.shwetalab.mobile.R;
import com.rns.shwetalab.mobile.adapter.DoctorListAdapter.ViewHolder;
import com.rns.shwetalab.mobile.domain.Dealer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DealerJobListAdapter extends BaseAdapter 
{
	
	private List<Dealer> name;
    Activity context;
    LayoutInflater inflater;


	public DealerJobListAdapter(DealerJobsList dealerJobsList, List<Dealer> list) 
	{
		this.context = dealerJobsList;
		this.inflater = LayoutInflater.from(context);
		this.name = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return name.size();
	}

	@Override
	public Object getItem(int position) {
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
	public View getView(int arg0, View avg1, ViewGroup parent) 
	{
		 ViewHolder holder = null;
	        View view = avg1;

	        if(view == null)
	        {
	            holder = new ViewHolder();
	            view = inflater.inflate(R.layout.activity_dealerlist_adapter, null);
	            holder.tv1 = (TextView)view.findViewById(R.id.dealersnametextView);
	            //holder.tv2 = (TextView)view.findViewById(R.id.doctorListAmount_textView);
	            view.setTag(holder);
	        }
	        else
	            holder = (ViewHolder)view.getTag();
	        	holder.tv1.setText(name.get(arg0).getDealer().getName());
	        	
	        return view;
	}

}
