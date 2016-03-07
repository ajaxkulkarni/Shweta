package com.rns.shwetalab.mobile.adapter;

import java.util.HashMap;
import java.util.List;

import com.rns.shwetalab.mobile.JobsExpandableListView;
import com.rns.shwetalab.mobile.R;
import com.rns.shwetalab.mobile.db.CommonUtil;
import com.rns.shwetalab.mobile.domain.Job;
import com.rns.shwetalab.mobile.domain.WorkType;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class JobsExpandableListViewAdapter extends BaseExpandableListAdapter 
{

	private Context context;
	//	    private List<String> listDataHeader; // header titles
	//	    // child data in format of header title, child title
	//	    private HashMap<String, List<String>> listDataChild;
	private List<Job> jobs;

	//	public JobsExpandableListViewAdapter(JobsExpandableListView jobsExpandableListView, List<String> listDataHeader,
	//			HashMap<String, List<String>> listDataChild) 
	//	{
	//		this.context = jobsExpandableListView;
	//        this.listDataHeader = listDataHeader;
	//        this.listDataChild = listDataChild;
	//	}

	public JobsExpandableListViewAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listChildData) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public JobsExpandableListViewAdapter(Context context, List<Job> jobs) {
		this.context = context;
		this.jobs = jobs;
	}



	@Override
	public Object getChild(int groupPosition, int childPosition) 
	{
		return this.jobs.get(groupPosition);
	}


	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}




	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		Job job = (Job) getChild(groupPosition, childPosition);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.activity_expandable_list_item, null);
		}

		TextView caseId = (TextView) convertView.findViewById(R.id.lbl_case_id);
		caseId.setText("Case ID :" + job.getId());
		TextView patientName = (TextView) convertView.findViewById(R.id.lbl_patient);
		patientName.setText("Patient Name :" + job.getPatientName());
		if (job.getPrice() != null) {
			TextView price = (TextView) convertView.findViewById(R.id.lbl_price);
			price.setText("Price :" + job.getPrice().toString());
		}
		TextView date = (TextView) convertView.findViewById(R.id.lbl_date);
		date.setText("Date :" +  CommonUtil.convertDate(job.getDate()));
		TextView workTypes = (TextView) convertView.findViewById(R.id.lbl_works);
		workTypes.setText("Works :" +  prepareWorks(job));
		TextView quandrant = (TextView) convertView.findViewById(R.id.lbl_quadrant);
		quandrant.setText("Quandrant :" + job.getQuadrent());
		TextView position = (TextView) convertView.findViewById(R.id.lbl_position);
		position.setText("Position :" + job.getPosition());
		TextView shade = (TextView) convertView.findViewById(R.id.lbl_shade);
		shade.setText("Shade :" + job.getShade());
		return convertView;
	}


	private String prepareWorks(Job job) {
		if(job.getWorkTypes() == null || job.getWorkTypes().size() == 0) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		for(WorkType workType:job.getWorkTypes()) {
			builder.append(workType.getName()).append(",");
		}
		builder.replace(builder.lastIndexOf(","), builder.lastIndexOf(","), "");
		return builder.toString();
	}


	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;
	}
	@Override
	public String getGroup(int groupPosition) {
		return jobs.get(groupPosition).getDoctor() != null ? jobs.get(groupPosition).getDoctor().getName() : "";
	}



	@Override
	public int getGroupCount() {
		return this.jobs.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}



	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		String headerTitle = getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.activity_expandable_list_group, null);
		}

		TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
		lblListHeader.setTypeface(null, Typeface.BOLD);
		lblListHeader.setText(headerTitle);

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
