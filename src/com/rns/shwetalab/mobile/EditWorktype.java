package com.rns.shwetalab.mobile;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rns.shwetalab.mobile.adapter.EditWorkTypeListAdapter;
import com.rns.shwetalab.mobile.db.CommonUtil;
import com.rns.shwetalab.mobile.db.JobsDao;
import com.rns.shwetalab.mobile.db.PersonDao;
import com.rns.shwetalab.mobile.db.WorkTypeDao;
import com.rns.shwetalab.mobile.domain.Person;
import com.rns.shwetalab.mobile.domain.WorkPersonMap;
import com.rns.shwetalab.mobile.domain.WorkType;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class EditWorktype extends Activity {
	private WorkType work;
	private JobsDao jobsDao;
	private WorkTypeDao workTypeDao;
	private ArrayList<WorkType> worktype;
	private WorkPersonMap workPersonMap;
	private PersonDao personDao;
	private ArrayList<WorkPersonMap> workPersonMaps;

	ListView lv;
	private ArrayList<String> objArrayListWorktype = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_worktype);
		lv = (ListView) findViewById(R.id.edit_worktype_listView);
		workTypeDao = new WorkTypeDao(this);

		final EditWorkTypeListAdapter adapter = new EditWorkTypeListAdapter(this, workTypeDao.getAll());
		lv.setAdapter(adapter);

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//				view = lv.getAdapter().getView(position, null, null);
				//				view = lv.getChildAt(position);
				//				TextView textView = (TextView) view.findViewById(R.id.worktypetextView);
				//				TextView textView1 = (TextView) view.findViewById(R.id.worktypepricetextView);

				Intent i = new Intent(EditWorktype.this, AdminEditWorkTypeActivity.class);
				//i.putExtra("WorkType", textView.getText().toString());
				i.putExtra("Data",new Gson().toJson(adapter.getItem(position)));
				//i.putExtra("DefaultPrice", textView1.getText().toString());
				startActivity(i);
			}
		});

	}

}
