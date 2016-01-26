package com.rns.shwetalab.mobile;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

public class DetailMarketing extends Activity 
{
	int i=0;
	ImageView send;
	EditText msg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	//	setContentView(R.layout.activity_detail_marketing);

		
		ScrollView scrl=new ScrollView(this);
		final LinearLayout ll=new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		scrl.addView(ll);
		final EditText editText = new EditText(DetailMarketing.this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
		lp.setMargins(20, 20, 20,20);
		editText.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded));
		editText.setHint("Enter Your Message"); 
		editText.setLayoutParams(lp);
		Button add_btn=new Button(this);
		add_btn.setLayoutParams(lp);
		add_btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button));
		add_btn.setText("Send");
		add_btn.setTextColor(Color.parseColor("#FFFFFF"));
		//msg = (EditText)findViewById(R.id.messageeditText);
		//send = (ImageView)findViewById(R.id.sendbutton);
		
		ll.addView(editText);
		ll.addView(add_btn);
		
		// ll.addView(add_btn);
		add_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				i++;
//				TextView tv=new TextView(getApplicationContext());
//				tv.setText("Number"+i);
//				ll.addView(tv);
			
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
				lp.setMargins(10, 20, 20,10);
				EditText et=new EditText(DetailMarketing.this);
				et.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded));
				et.setText(editText.getText().toString());
				editText.setText(""); 
				et.setLayoutParams(lp);
				ll.addView(et); 
			}
		});
		this.setContentView(scrl);
	}

}

