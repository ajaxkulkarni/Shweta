package com.rns.shwetalab.mobile.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rns.shwetalab.mobile.EditWorktype;
import com.rns.shwetalab.mobile.R;
import com.rns.shwetalab.mobile.domain.WorkType;

public class EditWorkTypeListAdapter extends BaseAdapter 
{
	Activity context;
	private List<WorkType> workTypes;
	LayoutInflater inflater;
	//private WorkType workType;
	//private List<WorkType> work ;

	/*public EditWorkTypeListAdapter(EditWorktype editWorktype, ArrayList<String> objArrayListWorktype) 
	{
		this.context = editWorktype;
		inflater = LayoutInflater.from(context);
		this.name = objArrayDoctorListName;
		this.work = objArrayListWorktype;
	}*/

	
	public EditWorkTypeListAdapter(EditWorktype editWorktype, List<WorkType> all) {
		this.context = editWorktype;
		this.inflater = LayoutInflater.from(context);
		this.workTypes = all;
	}


	@Override
	public int getCount() 
	{
		// TODO Auto-generated method stub
		return workTypes.size();
	}

	@Override
	public Object getItem(int position) {
		if(workTypes == null || workTypes.size() == 0) {
			return null;
		}
		return workTypes.get(position);
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
			if (workTypes != null && workTypes.size() > position) 
			{
				holder.tv1.setText(workTypes.get(position).getName());
				//TODO : Worktype default price to be displayed
			}
		return view;
	}
}
