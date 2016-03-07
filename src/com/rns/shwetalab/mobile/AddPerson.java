package com.rns.shwetalab.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.rns.shwetalab.mobile.db.CommonUtil;
import com.rns.shwetalab.mobile.db.PersonDao;
import com.rns.shwetalab.mobile.domain.Person;

public class AddPerson extends Activity {

	private PersonDao personDao;
	private Button addPersonButton;
	private Person person;
	private EditText nameEditText, emailEditText, phoneEditText;
	private RadioButton doctor, lab, dealer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_person);
		person = new Person();
		Bundle extras = getIntent().getExtras();
		 String value = extras.getString("Value");
		String names = extras.getString("Name");
		String email = extras.getString("Email");
		String phone = extras.getString("Phone");
		nameEditText = (EditText) findViewById(R.id.add_person_activity_name_editText);
		emailEditText = (EditText) findViewById(R.id.add_person_activity_email_editText);
		phoneEditText = (EditText) findViewById(R.id.add_person_activity_phone_editText);
		if (value.equals("0")) 
		{
			nameEditText.setText(names.toString());
			emailEditText.setText(email.toString());
			phoneEditText.setText(phone);
		} else if(value.equals("1"))
		{
			nameEditText.setText("");
			emailEditText.setText("");
			phoneEditText.setText("");
		}
		personDao = new PersonDao(getApplicationContext());
		doctor = (RadioButton) findViewById(R.id.docotorradio);
		lab = (RadioButton) findViewById(R.id.labradio);
		dealer = (RadioButton) findViewById(R.id.dealerradio);
		addPersonButton = (Button) findViewById(R.id.add_person_activity_add_button);
		addPersonButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) { // validate();
				preparePerson();
				personDao.insertDetails(person);
				CommonUtil.showMessage(AddPerson.this);
			}
		});

		doctor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				person.setWorkType(CommonUtil.TYPE_DOCTOR);
			}
		});
		lab.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				person.setWorkType(CommonUtil.TYPE_LAB);
			}
		});
		dealer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				person.setWorkType(CommonUtil.TYPE_DEALER);
			}
		});

	}

	private void preparePerson() {

		person.setName(nameEditText.getText().toString());
		person.setEmail(emailEditText.getText().toString());
		person.setPhone(phoneEditText.getText().toString());
	}

	private void validate() {
		if (TextUtils.isEmpty(nameEditText.getText())) {
			nameEditText.setError(Html.fromHtml("<font color='black'>Enter Name</font>"));
		}
		if (TextUtils.isEmpty(emailEditText.getText())) {
			emailEditText.setError(Html.fromHtml("<font color='black'>Enter Name</font>"));
		}
		if (TextUtils.isEmpty(nameEditText.getText())) {
			phoneEditText.setError(Html.fromHtml("<font color='black'>Enter Phone</font>"));
		}
		if (!(doctor.isChecked() || lab.isChecked() || dealer.isChecked())) {
			Toast.makeText(AddPerson.this, "Please Select One Option!", Toast.LENGTH_SHORT).show();
		}

	}

}
