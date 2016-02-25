package com.rns.shwetalab.mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AddViewDescription extends Activity 
{
	
	TextView name;
	Button add,view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_view_description);
		name = (TextView)findViewById(R.id.marketingpersontextView);
		add = (Button)findViewById(R.id.adddescripiotn_button);
		view = (Button)findViewById(R.id.viewdescriptionbutton);
		Bundle extras = getIntent().getExtras();
		final String names = extras.getString("personname").toUpperCase();
		name.setText(names);
		
		add.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				Intent i = new Intent(AddViewDescription.this,AddDescription.class);
				i.putExtra("Name",names);
				startActivity(i);
				
			}
		});
		
		
	}
}
