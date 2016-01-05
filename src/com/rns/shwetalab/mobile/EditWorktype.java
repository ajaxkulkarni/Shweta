package com.rns.shwetalab.mobile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.rns.shwetalab.mobile.adapter.DoctorListAdapter;
import com.rns.shwetalab.mobile.adapter.EditWorkTypeListAdapter;
import com.rns.shwetalab.mobile.db.JobsDao;
import com.rns.shwetalab.mobile.db.WorkTypeDao;
import com.rns.shwetalab.mobile.domain.Job;
import com.rns.shwetalab.mobile.domain.Person;
import com.rns.shwetalab.mobile.domain.WorkPersonMap;
import com.rns.shwetalab.mobile.domain.WorkType;

import android.app.Activity;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

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
		EditWorkTypeListAdapter Adapter = new EditWorkTypeListAdapter(this,objArrayListWorktype);
		lv.setAdapter(Adapter);

	}



}
