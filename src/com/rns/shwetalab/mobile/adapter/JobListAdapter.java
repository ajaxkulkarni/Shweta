package com.rns.shwetalab.mobile.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rns.shwetalab.mobile.JobList;
import com.rns.shwetalab.mobile.R;

/**
 * Created by Rajesh on 8/27/2015.
 */
public class JobListAdapter extends BaseAdapter
{

    private ArrayList<String> name;
    Activity context;
    LayoutInflater inflater;


    public JobListAdapter(JobList jobsList,
                                  ArrayList<String> objArrayListName)
    {
        // TODO Auto-generated constructor stub
        this.context = jobsList;
        inflater = LayoutInflater.from(context);
        this.name = objArrayListName;


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
        TextView tv1;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2)
    {
        ViewHolder holder = null;
        View view = arg1;

        if(view == null)
        {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.activity_jobs_list_adapter, null);
            holder.tv1 = (TextView)view.findViewById(R.id.DoctornametextView);
            view.setTag(holder);

        }

        else
            holder = (ViewHolder)view.getTag();

        holder.tv1.setText(name.get(arg0));


        // TODO Auto-generated method stub
        return view;

    }

}
