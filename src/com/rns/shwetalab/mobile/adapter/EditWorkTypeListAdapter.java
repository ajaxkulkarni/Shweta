package com.rns.shwetalab.mobile.adapter;

import java.util.ArrayList;
import java.util.List;

import com.rns.shwetalab.mobile.EditWorktype;
import com.rns.shwetalab.mobile.R;
import com.rns.shwetalab.mobile.adapter.AddWorkTypeDoctorListAdapter.ViewHolder;
import com.rns.shwetalab.mobile.domain.WorkType;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class EditWorkTypeListAdapter extends BaseAdapter 
{
	Activity context;
	private List<String> work;
	LayoutInflater inflater;
	private WorkType workType;
	//private List<WorkType> work ;

	public EditWorkTypeListAdapter(EditWorktype editWorktype, ArrayList<String> objArrayListWorktype) 
	{
		this.context = editWorktype;
		inflater = LayoutInflater.from(context);
		/*this.name = objArrayDoctorListName;*/
		this.work = objArrayListWorktype;
	}

	
	@Override
	public int getCount() 
	{
		// TODO Auto-generated method stub
		return work.size();
	}

	@Override
	public Object getItem(int position) {
		if(work == null || work.size() == 0) {
			return null;
		}
		return work.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public class ViewHolder {
		TextView tv1;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		View view = convertView;

		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.activity_add_work_type_list_adapter, null);
			holder.tv1 = (TextView) view.findViewById(R.id.worktypetextView);
			view.setTag(holder);

		}
		else
			holder = (ViewHolder) view.getTag();
			for (int i = 0; i < work.size(); i++)
				{
		if (work != null && work.size() > position) 
		{
			holder.tv1.setText(work.get(position).toString());
		}
		}
		return view;
	}
}
