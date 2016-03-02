package com.rns.shwetalab.mobile;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import com.rns.shwetalab.mobile.db.CommonUtil;
import com.rns.shwetalab.mobile.db.DescriptionDao;
import com.rns.shwetalab.mobile.domain.FollowUpMessage;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class AddDescription extends Activity implements OnClickListener {
	private DatePickerDialog toDatePickerDialog;
	private SimpleDateFormat dateFormatter;
	private Button add;
	private EditText message, date;
	private FollowUpMessage msg;
	private DescriptionDao descriptionDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_description);
		dateFormatter = new SimpleDateFormat(CommonUtil.DATE_FORMAT, Locale.US);
		date = (EditText) findViewById(R.id.followupdateeditText1);
		add = (Button) findViewById(R.id.adddescbutton1);
		message = (EditText) findViewById(R.id.desceditText1);
		setDateTimeField();
		date.setText(CommonUtil.convertDate(new Date()));
		descriptionDao = new DescriptionDao(this);
		Bundle extras = getIntent().getExtras();
		final String names = extras.getString("Name");

		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(message.getText())) {
					message.setError("<font color='black'>Enter description</font>");
				} else {
					descriptionDao.insertDetails(preparedesc(names));
					CommonUtil.showMessage(AddDescription.this);
				}
			}
		});
	}

	private FollowUpMessage preparedesc(String names) {
		FollowUpMessage msgs = new FollowUpMessage();
		msgs.setDescription(message.getText().toString());
		msgs.setName(names);
		msgs.setDate(CommonUtil.convertDate(date.getText().toString()));
		return msgs;
	}

	private void setDateTimeField() {

		SimpleDateFormat dfDate = new SimpleDateFormat("dd-M-yyyy");
		Calendar c = Calendar.getInstance();
		String date1 = dfDate.format(c.getTime());
		date.setText(date1);
		date.setOnClickListener(this);

		Calendar newCalendar = Calendar.getInstance();
		toDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				Calendar newDate = Calendar.getInstance();
				newDate.set(year, monthOfYear, dayOfMonth);
				date.setText(dateFormatter.format(newDate.getTime()));
			}
		}, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		toDatePickerDialog.show();
	}
}
