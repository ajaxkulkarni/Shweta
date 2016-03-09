package com.rns.shwetalab.mobile;

import java.math.BigDecimal;

import org.w3c.dom.Text;

import com.rns.shwetalab.mobile.db.CommonUtil;
import com.rns.shwetalab.mobile.db.DealerDao;
import com.rns.shwetalab.mobile.db.JobLabMapDao;
import com.rns.shwetalab.mobile.db.JobsDao;
import com.rns.shwetalab.mobile.domain.WorkPersonMap;
import com.rns.shwetalab.mobile.domain.WorkType;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class BalanceSheet extends Activity {
	private TextView doctorPrice, labPrice, doctor, lab,dealerprice,dealers;
	private WorkType worktype;
	private WorkPersonMap workpersonmap;
	private JobsDao jobsDao;
	private DealerDao dealerDao;
	private Button next;
	private TextView totalPrice;
	private JobLabMapDao JobLabMapDao;
	final String price = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_balance_sheet);
		jobsDao = new JobsDao(this);
		dealerDao = new DealerDao(this);
		JobLabMapDao = new JobLabMapDao(this);
		Bundle extras = getIntent().getExtras();
		final String month = extras.getString("Month");
		doctorPrice = (TextView) findViewById(R.id.activity_billingsheet_doctor_textView);
		final BigDecimal gain = jobsDao.getDoctorIncomeForMonth(month, CommonUtil.TYPE_DOCTOR);
		doctorPrice.setText(gain.toString());
		labPrice = (TextView) findViewById(R.id.activity_billingsheetlab_textView);
		BigDecimal labDues = JobLabMapDao.getLabIncomeForMonth(jobsDao.getJobsByMonth(month));
		labPrice.setText(labDues.toString());
		dealerprice = (TextView)findViewById(R.id.activity_billingsheetdealer_textView);
		BigDecimal dealer = dealerDao.getIncomeForMonth(month, CommonUtil.TYPE_DEALER);
		dealerprice.setText(dealer.toString());
		totalPrice = (TextView) findViewById(R.id.activity_billingsheettotal_textView);
		totalPrice.setText(gain.subtract(labDues).add(dealer).toString());
		next = (Button) findViewById(R.id.balancesheet_submit_button);
		dealers = (TextView)findViewById(R.id.activity_billingsheetdealertextView);
		doctor = (TextView) findViewById(R.id.activity_billingsheetdoctortextView);
		
		dealers.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				Intent i = new Intent(BalanceSheet.this, MonthlyDealerList.class);
				i.putExtra("Month", month);
				i.putExtra("type", CommonUtil.TYPE_DEALER);
				startActivity(i);
			}
		}); 
		
		doctor.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(BalanceSheet.this, DoctorMonthlyBalanceList.class);
				i.putExtra("Month", month);
				i.putExtra("Price", doctorPrice.getText().toString());
				i.putExtra("type", CommonUtil.TYPE_DOCTOR);
				startActivity(i);
			}
		});

		lab = (TextView) findViewById(R.id.activity_billingsheetlabtextView);
		lab.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(BalanceSheet.this, LabMonthlyBalanceList.class);
				i.putExtra("Month", month);
				i.putExtra("type", CommonUtil.TYPE_LAB);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.balance_sheet, menu);
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
