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

public class AddWorkTypeLabListAdapter extends BaseAdapter {

	private List<String> name;
	private List<String> amount;
	Activity context;
	LayoutInflater inflater;

	public AddWorkTypeLabListAdapter(AdminAddWorkTypeActivity adminAddWorkTypeActivity,
			List<String> objArrayLabListName, List<String> objArrayLabListAmount) {
		this.context = adminAddWorkTypeActivity;
		inflater = LayoutInflater.from(context);
		this.name = objArrayLabListName;
		this.amount = objArrayLabListAmount;
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

	public class ViewHolder {
		TextView tv1;
		EditText ed1;
	}

	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		View view = arg1;

		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.activity_add_work_type_lablist_adapter, null);
			holder.tv1 = (TextView) view.findViewById(R.id.addwork_type_lablist_adapter_textView);
			holder.ed1 = (EditText) view.findViewById(R.id.addwork_type_lablist_adapter_editText);
			view.setTag(holder);

		}

		else
			holder = (ViewHolder) view.getTag();

		holder.tv1.setText(name.get(arg0));
		if (amount.size() > arg0) {
			holder.ed1.setText(amount.get(arg0));
		}

		// TODO Auto-generated method stub
		return view;
	}

}
