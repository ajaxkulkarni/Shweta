package com.rns.shwetalab.mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AddPersonWork extends Activity 
{
	Button person,work,edit;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_person_work);

		init();

		person.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View v) 
			{
				String value = "1";
				Intent i = new Intent(AddPersonWork.this,AddPerson.class);
				i.putExtra("Value", value);
				startActivity(i);
			}
		});

		work.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View v) 
			{
				Intent i = new Intent(AddPersonWork.this,AdminAddWorkTypeActivity.class);
				startActivity(i);				
			}
		});

		edit.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View v) 
			{
				Intent i = new Intent(AddPersonWork.this,EditWorktype.class);
				startActivity(i);				
			}
		});
	}

	private void init() 
	{
		person = (Button)findViewById(R.id.addperson_button);
		work = (Button)findViewById(R.id.addwork_button);
		edit = (Button)findViewById(R.id.editworktype_button);
	}



}
