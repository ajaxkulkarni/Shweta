package com.rns.shwetalab.mobile;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.rns.shwetalab.mobile.adapter.EditWorkTypeListAdapter;
import com.rns.shwetalab.mobile.db.JobsDao;
import com.rns.shwetalab.mobile.db.WorkTypeDao;
import com.rns.shwetalab.mobile.domain.WorkType;

public class EditWorktype extends Activity 
{
	private WorkType work;
	private JobsDao jobsDao;
	private WorkTypeDao workTypeDao;
	private ArrayList<WorkType> worktype;

	ListView lv;
	private ArrayList<String> objArrayListWorktype = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_worktype);
		lv = (ListView) findViewById(R.id.edit_worktype_listView);
		workTypeDao = new WorkTypeDao(this);
		EditWorkTypeListAdapter Adapter = new EditWorkTypeListAdapter(this, workTypeDao.getAll());
		lv.setAdapter(Adapter);

	}



}
