package com.rns.shwetalab.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rns.shwetalab.mobile.db.CommonUtil;
import com.rns.shwetalab.mobile.db.PersonDao;
import com.rns.shwetalab.mobile.domain.Person;

public class AddPerson extends Activity 
{

	private PersonDao personDao;
	private Button addPersonButton;
	private Person person;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_person);
		personDao = new PersonDao(getApplicationContext());
		addPersonButton = (Button) findViewById(R.id.add_person_activity_add_button);
		addPersonButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				preparePerson();
				personDao.insertDetails(person);
				Toast.makeText(getApplicationContext(), "Record Inserted successfully!!", Toast.LENGTH_LONG).show();
			}
		});
	}

	private void preparePerson() {
		person = new Person();
		EditText nameEditText = (EditText) findViewById(R.id.add_person_activity_name_editText);
		EditText emailEditText = (EditText)findViewById(R.id.add_person_activity_email_editText);
		EditText phoneEditText = (EditText)findViewById(R.id.add_person_activity_phone_editText);
		person.setName(nameEditText.getText().toString());
		person.setEmail(emailEditText.getText().toString());
		person.setPhone(phoneEditText.getText().toString());
		person.setWorkType(CommonUtil.TYPE_DOCTOR);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_person, menu);
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
