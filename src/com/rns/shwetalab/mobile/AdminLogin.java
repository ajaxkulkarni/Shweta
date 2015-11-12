package com.rns.shwetalab.mobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class AdminLogin extends Activity {

	Button login;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		Log.d("sdsd","qwqw");
		setContentView(R.layout.activity_admin_login);


		login = (Button) findViewById(R.id.Loginbutton);
		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{

				AlertDialog.Builder alert = new AlertDialog.Builder
						(AdminLogin.this);
				alert.setTitle("Select Your Option:");

				alert.setPositiveButton("Add Person",
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0,
							int arg1) 
					{
						Log.d("sdsd","ddfdf");
						Intent i = new Intent(AdminLogin.this, AddPerson.class);
						startActivity(i);
					}

				});

				alert.setNegativeButton("Add Work",
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0,
							int arg1) {
						Intent i = new Intent(AdminLogin.this, AdminAddWorkTypeActivity.class);
						startActivity(i);
					}
				});
				alert.show();



			}
		});


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
