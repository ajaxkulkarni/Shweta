package com.rns.shwetalab.mobile;

import com.rns.shwetalab.mobile.db.CommonUtil;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLogin extends Activity {

	Button login;
	EditText username, password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("sdsd", "qwqw");
		setContentView(R.layout.activity_admin_login);
		alreadylogin();
		login = (Button) findViewById(R.id.Loginbutton);
		username = (EditText) findViewById(R.id.usernameeditText);
		password = (EditText) findViewById(R.id.passwordeditText);
		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				validation();
				{
					if (username.getText().toString().equals(CommonUtil.USERNAME)
							&& password.getText().toString().equals(CommonUtil.PASSWORD)) {
						CommonUtil.login(AdminLogin.this);
						Toast.makeText(AdminLogin.this, "Login Successfull", Toast.LENGTH_SHORT).show();
						Intent i = new Intent(AdminLogin.this, HomePage.class);
						startActivity(i);
						finish();
					} else {
						CommonUtil.showLoginErrorMessage(AdminLogin.this);
					}
				}
			}
		});
	}

	private void alreadylogin() {
		SharedPreferences settings = getSharedPreferences(CommonUtil.LABNAME, 0);
		if (settings.getString("logged in", "").toString().equals("logged in")) {
			Intent intent = new Intent(AdminLogin.this, AddPersonWork.class);
			startActivity(intent);
			finish();
		}
	}

	private void validation() {
		if (TextUtils.isEmpty(username.getText()))

		{
			username.setError(Html.fromHtml("<font color='black'>Enter Username</font>"));
		} else if (TextUtils.isEmpty(password.getText()))

		{
			password.setError(Html.fromHtml("<font color='black'>Enter Password</font>"));
		}

	}

}
