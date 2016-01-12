package com.rns.shwetalab.mobile.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.rns.shwetalab.mobile.AdminAddWorkTypeActivity;
import com.rns.shwetalab.mobile.R;
import com.rns.shwetalab.mobile.domain.WorkPersonMap;

public class AddWorkTypeDoctorListAdapter extends BaseAdapter {

	//private List<String> name;
	private List<String> amount;
	private List<WorkPersonMap> workPersonMaps;
	Activity context;
	LayoutInflater inflater;



	public AddWorkTypeDoctorListAdapter(AdminAddWorkTypeActivity adminAddWorkTypeActivity, List<WorkPersonMap> workPersonMapList, List<String> doctorAmount) {
		this.context = adminAddWorkTypeActivity;
		this.inflater = LayoutInflater.from(context);
		/*this.name = objArrayDoctorListName;*/
		this.amount = doctorAmount;
		this.workPersonMaps = workPersonMapList;
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
			view = inflater.inflate(R.layout.activity_add_work_type_doctorlist_adapter, null);
			holder.tv1 = (TextView) view.findViewById(R.id.addwork_type_doctorlist_adapter_textView);
			holder.ed1 = (EditText) view.findViewById(R.id.addwork_type_doctorlist_adapter_editText);
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
