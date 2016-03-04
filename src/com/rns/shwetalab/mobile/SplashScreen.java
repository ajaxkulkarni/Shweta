package com.rns.shwetalab.mobile;

import com.rns.shwetalab.mobile.db.CommonUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SplashScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_sceen);

		Thread th = new Thread() {
			public void run() {
				try {
					sleep(3000);

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent i = new Intent(SplashScreen.this, HomePage.class);
				startActivity(i);
				finish();
			}
		};
		th.start();	
		
		
		
	}

	
	
	
	
}
