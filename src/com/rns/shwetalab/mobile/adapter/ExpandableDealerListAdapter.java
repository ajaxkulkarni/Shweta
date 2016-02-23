package com.rns.shwetalab.mobile.adapter;

import java.util.HashMap;
import java.util.List;

import com.rns.shwetalab.mobile.ExpandableDealerList;
import com.rns.shwetalab.mobile.R;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandableDealerListAdapter extends BaseExpandableListAdapter
{
	
	private Context context;
    private List<String> listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> listDataChild;

	public ExpandableDealerListAdapter(ExpandableDealerList expandableDealerList, List<String> listDataHeader,
			HashMap<String, List<String>> listDataChild) 
	{

		
		this.context = expandableDealerList;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listDataChild;
	}



	@Override
	public int getGroupCount() 
	{
		return this.listDataHeader.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		 return this.listDataChild.get(this.listDataHeader.get(groupPosition))
	                .size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return this.listDataHeader.get(groupPosition);	}

	@Override
	public Object getChild(int groupPosition, int childPosition) 
	{
		return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .get(childPosition);	
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) 
	{
		String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.dealer_list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) 
	{
		final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.dealer_list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(childText);
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
		return false;
	}

}
