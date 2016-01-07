package com.rns.shwetalab.mobile.adapter;

import java.util.List;

import com.rns.shwetalab.mobile.AdminEditWorkTypeActivity;
import com.rns.shwetalab.mobile.R;
import com.rns.shwetalab.mobile.adapter.AddWorkTypeDoctorListAdapter.ViewHolder;
import com.rns.shwetalab.mobile.domain.WorkPersonMap;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class AdminEditWorkTypeAdapter extends BaseAdapter
{

	private List<String> amount;
	private List<WorkPersonMap> workPersonMaps;
	Activity context;
	LayoutInflater inflater;

	public AdminEditWorkTypeAdapter(AdminEditWorkTypeActivity adminEditWorkTypeActivity,
			List<WorkPersonMap> workPersonMaps, List<String> doctorAmounts) 
	{
		this.context = adminEditWorkTypeActivity;
		this.inflater = LayoutInflater.from(context);
		this.amount = doctorAmounts;
		this.workPersonMaps = workPersonMaps;
	}

	@Override
	public int getCount() {
		return workPersonMaps.size();
	}

	@Override
	public Object getItem(int position) {
		if(workPersonMaps == null || workPersonMaps.size() == 0) {
			return null;
		}
		return workPersonMaps.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public class ViewHolder {
		TextView tv1;
		EditText ed1;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder = null;
		View view = arg1;

		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.activity_edit_work_type_doctorlist_adapter, null);
			holder.tv1 = (TextView) view.findViewById(R.id.editwork_type_doctorlist_adapter_textView);
			holder.ed1 = (EditText) view.findViewById(R.id.editwork_type_doctorlist_adapter_editText);
			view.setTag(holder);

		}

		else
			holder = (ViewHolder) view.getTag();

		if (amount != null && amount.size() > arg0) {
			holder.ed1.setText(amount.get(arg0));
		}

		if(workPersonMaps.size() > arg0 && workPersonMaps.get(arg0).getPerson() != null) {
			holder.tv1.setText(workPersonMaps.get(arg0).getPerson().getName());
		}
		return view;
	}

}
