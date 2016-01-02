package com.rns.shwetalab.mobile.adapter;

import java.util.ArrayList;
import java.util.List;

import com.rns.shwetalab.mobile.Doctor_List_Amount;
import com.rns.shwetalab.mobile.R;
import com.rns.shwetalab.mobile.adapter.AddWorkTypeDoctorListAdapter.ViewHolder;
import com.rns.shwetalab.mobile.domain.Job;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class DoctorListAdapter extends BaseAdapter
{

	private ArrayList<String> name;
	Activity context;
	
		
	private List<Job> jobs;
	LayoutInflater inflater;

	public DoctorListAdapter(Doctor_List_Amount doctor_List_Amount, ArrayList<String> objArrayListDoctorName)
	{
		this.context = doctor_List_Amount;
		inflater = LayoutInflater.from(context);
		this.name = objArrayListDoctorName;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return name.size();
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
		TextView name;
		TextView amount;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2)
	{

		ViewHolder holder = null;
		View view = arg1;

		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.activity_doctorlist_adapter, null);
			holder.name = (TextView) view.findViewById(R.id.doctorListName_textView);
			holder.amount = (EditText) view.findViewById(R.id.doctorListAmount_textView);
			view.setTag(holder);

		}
		else
			holder = (ViewHolder)view.getTag();
			holder.name.setText(name.get(arg0));


		// TODO Auto-generated method stub
		return view;

	}

}
