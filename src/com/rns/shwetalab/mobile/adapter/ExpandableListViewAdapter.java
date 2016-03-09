package com.rns.shwetalab.mobile.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.rns.shwetalab.mobile.R;
import com.rns.shwetalab.mobile.db.CommonUtil;
import com.rns.shwetalab.mobile.db.JobWorkTypeMapDao;
import com.rns.shwetalab.mobile.db.WorkPersonMapDao;
import com.rns.shwetalab.mobile.domain.Job;
import com.rns.shwetalab.mobile.domain.WorkPersonMap;
import com.rns.shwetalab.mobile.domain.WorkType;

/**
 * Created by Rajesh on 8/28/2015.
 */
public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

	private Context _context;
	// private List<String> jobTitles; // header titles
	// child data in format of header title, child title
	// private HashMap<String, List<String>> _listDataChild;

	private List<Job> jobs;

	public ExpandableListViewAdapter(Context context, List<String> listDataHeader,
			HashMap<String, List<String>> listChildData) {
		this._context = context;
		// this.jobTitles = listDataHeader;
		// this._listDataChild = listChildData;
	}

	public ExpandableListViewAdapter(Context context, List<Job> jobs) {
		this._context = context;
		this.jobs = jobs;
	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		// return
		// this._listDataChild.get(this.jobTitles.get(groupPosition)).get(childPosititon);
		return this.jobs.get(groupPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {

		// final String childText = (String) getChild(groupPosition,
		// childPosition);

		Job job = (Job) getChild(groupPosition, childPosition);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
		date.setText("Date :" + CommonUtil.convertDate(job.getDate()));
		TextView workTypes = (TextView) convertView.findViewById(R.id.lbl_works);
		workTypes.setText("Works :" + prepareWorks(job));
		TextView quandrant = (TextView) convertView.findViewById(R.id.lbl_quadrant);
		quandrant.setText("Quandrant :" + job.getQuadrent());
		TextView position = (TextView) convertView.findViewById(R.id.lbl_position);
		position.setText("Position :" + job.getPosition());
		TextView shade = (TextView) convertView.findViewById(R.id.lbl_shade);
		shade.setText("Shade :" + job.getShade());
		return convertView;
	}

	private String prepareWorks(Job job) {
		StringBuilder builder = new StringBuilder();
		WorkPersonMapDao workPersonMapDao = new WorkPersonMapDao(_context);
		JobWorkTypeMapDao jobWorkTypeMapDao = new JobWorkTypeMapDao(_context);
		List<WorkType> works = jobWorkTypeMapDao.getWorktypesForJob(job);
		if (works == null || works.size() == 0) {
			return "";
		}

		if (job.getDoctor().getWorkType().equals(CommonUtil.TYPE_LAB)) {
			List<WorkPersonMap> work = new ArrayList<WorkPersonMap>();
			for (WorkType workType : works) {
				List<WorkPersonMap> jobs = workPersonMapDao.getWorkTypeById(workType, CommonUtil.TYPE_LAB,
						job.getDoctor().getId());
				{
					work.addAll(jobs);
					builder.append(work.get(0).getWorkType().getName());
					break;
				}
			}
		}

		else {
			for (WorkType workType : job.getWorkTypes()) {
				builder.append(workType.getName()).append(",");

			}
		}
		return builder.toString();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// return
		// this._listDataChild.get(this.jobTitles.get(groupPosition)).size();
		return 1;
	}

	@Override
	public String getGroup(int groupPosition) {
		// return this.jobTitles.get(groupPosition);
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
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.activity_expandable_list_group, null);
		}

		TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
		lblListHeader.setTypeface(null, Typeface.BOLD);
		lblListHeader.setText(headerTitle);

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
