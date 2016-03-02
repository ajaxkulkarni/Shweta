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
import com.rns.shwetalab.mobile.db.CommonUtil;
import com.rns.shwetalab.mobile.domain.FollowUpMessage;
import com.rns.shwetalab.mobile.domain.Marketing;

import android.app.Activity;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MarketingDescriptionListAdapter extends BaseAdapter {
	private List<FollowUpMessage> marketing;
	Activity context;
	LayoutInflater inflater;


	public MarketingDescriptionListAdapter(MarketingDescriptionList marketingDescriptionList,
			List<FollowUpMessage> list) {

		this.context = marketingDescriptionList;
		this.inflater = LayoutInflater.from(context);
		this.marketing = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return marketing.size();
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

	public class ViewHolder {
		TextView tv1, tv2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		View view = convertView;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.activity_marketing_description_list_adapter, null);
			holder.tv1 = (TextView) view.findViewById(R.id.description_textView);
			holder.tv2 = (TextView) view.findViewById(R.id.marketdate_textView);
			view.setTag(holder);
		} else {
			view = convertView;
		}

		holder = (ViewHolder) view.getTag();
		holder.tv1.setText(marketing.get(position).getDescription());
		holder.tv2.setText(CommonUtil.convertDate(marketing.get(position).getDate()).toString());
		
		return view;
	}

}
