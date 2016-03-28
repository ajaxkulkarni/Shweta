package com.rns.shwetalab.mobile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.rns.shwetalab.mobile.adapter.JobsExpandableListViewAdapter;
import com.rns.shwetalab.mobile.db.BalanceAmountDao;
import com.rns.shwetalab.mobile.db.CommonUtil;
import com.rns.shwetalab.mobile.db.JobsDao;
import com.rns.shwetalab.mobile.domain.Balance_Amount;
import com.rns.shwetalab.mobile.domain.Job;
import com.rns.shwetalab.mobile.domain.WorkType;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

public class JobsExpandableListView extends Activity {
	private JobsExpandableListViewAdapter joblistAdapter;
	private ExpandableListView expListView;
	private List<String> listDataHeader;
	private HashMap<String, List<String>> listDataChild;
	private JobsDao jobsDao;
	private String dateSelected, new_balance;
	List<Integer> balance_data;
	private BalanceAmountDao amountDao;
	public TextView date, bal, pay_bal_text;
	ImageView pay, mail;
	int id = 0;
	String price;
	String work;
	BigDecimal amount;
	int a, bal_price;
	int total, current_month, current_year;
	String file = "PositionPdf.pdf";
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD, BaseColor.RED);
	private static Font redFont2 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.RED);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jobs_expandable_list_view);
		expListView = (ExpandableListView) findViewById(R.id.jobsexpandableListView);
		bal = (TextView) findViewById(R.id.paid_textView);
		pay = (ImageView) findViewById(R.id.addpayment_imageView);
		mail = (ImageView) findViewById(R.id.mail_imageView1);
		pay_bal_text = (TextView) findViewById(R.id.doctor_pay_balance_textView1);
		final Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
		amountDao = new BalanceAmountDao(getApplicationContext());
		jobsDao = new JobsDao(getApplicationContext());
		Bundle extras = getIntent().getExtras();
		final String month = extras.getString("Month");
		final String name = extras.getString("Name");
		String type = extras.getString("Type");
		price = extras.getString("Price");
		prepareListData(month, name);
		getbalance(price);

		current_month = localCalendar.get(Calendar.MONTH) + 1;
		current_year = localCalendar.get(Calendar.YEAR);
		// if(type==CommonUtil.TYPE_DOCTOR)
		// bal.setText("HEllo");

		pay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(JobsExpandableListView.this, DoctorAmountDetails.class);
				i.putExtra("Price", price);
				i.putExtra("ID", id);
				i.putExtra("New_Balance", new_balance);
				startActivity(i);
				finish();
			}
		});

		mail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					createPdf(month, name);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});

		joblistAdapter = new JobsExpandableListViewAdapter(this, jobsDao.getJobsByMonthName(month, name));
		expListView.setAdapter(joblistAdapter);

		// else
		// {
		// joblistAdapter = new JobsExpandableListViewAdapter(this,
		// jobsDao.getLabJobsByMonth(month,name));
		// expListView.setAdapter(joblistAdapter);
		// }
		expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				return false;
			}
		});

		expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {
			}
		});

		expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
			@Override
			public void onGroupCollapse(int groupPosition) {
			}
		});

		// Listview on child click listener
		expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition,
					long id) {

				return false;

			}
		});

		expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {
			}
		});

		expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
			@Override
			public void onGroupCollapse(int groupPosition) {
			}
		});

		// Listview on child click listener
		expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition,
					long id) {

				return false;
			}
		});

	}

	private void prepareListData(String month, String name) {
		jobsDao = new JobsDao(this);
		List<Job> jobs = jobsDao.getJobsByMonthName(month, name);
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();
		for (Job job : jobs) {
			if (job.getDoctor() == null) {
				continue;
			}
			id = job.getDoctor().getId();
			work = prepareWorks(job);
			amount = job.getPrice();

		}
	}

	private String prepareWorks(Job job) {
		if (job.getWorkTypes() == null || job.getWorkTypes().size() == 0) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		for (WorkType workType : job.getWorkTypes()) {
			builder.append(workType.getName()).append(",");
		}
		builder.replace(builder.lastIndexOf(","), builder.lastIndexOf(","), "");
		return builder.toString();
	}

	private void getbalance(String price) {

		List<Balance_Amount> amountbalance = new ArrayList<Balance_Amount>();
		// balance_data = new ArrayList<Integer>();
		amountbalance = amountDao.getDealerName(id);

		if (amountbalance.isEmpty()) {
			bal.setText("" + price);
			new_balance = bal.getText().toString();
		} else {
			total = Integer.parseInt(price) - amountbalance.get(amountbalance.size() - 1).getAmount_paid();
			bal.setText("" + total);
			new_balance = bal.getText().toString();
			if (total == 0) {
				pay.setVisibility(View.GONE);
				pay_bal_text.setVisibility(View.GONE);
			}
		}

	}

	private void sendmail(File myFile) {
		String to = "ajinkyashiva@gmail.com";
		Intent email = new Intent(Intent.ACTION_SEND);
		email.putExtra(Intent.EXTRA_SUBJECT, "Shweta Dental Laboratory Invoice");
		email.putExtra(Intent.EXTRA_TEXT, "PFA,");
		Uri uri = Uri.fromFile(myFile);
		email.putExtra(Intent.EXTRA_EMAIL, new String[] { to });
		email.putExtra(Intent.EXTRA_STREAM, uri);
		email.setType("message/rfc822");
		startActivity(email);

	}

	// more of the getters and setters …..
	@SuppressLint("NewApi")
	private void createPdf(String month, String name) throws FileNotFoundException, DocumentException {
		File pdfFolder = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(),
				"Shweta Dental Lab Invoice");
		if (!pdfFolder.exists()) {
			pdfFolder.mkdir();
			Log.i("TAG", "Pdf Directory created");
		}
		File myFile = new File(pdfFolder + ".pdf");
		FileOutputStream output = new FileOutputStream(myFile);
		Document document = new Document();
		PdfWriter.getInstance(document, output);
		document.open();
		addContent(document, name, month);
		document.close();
		sendmail(myFile);
	}

	private void addContent(Document document, String name, String month) throws DocumentException {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date dateobj = new Date();
		Paragraph preface = new Paragraph();
		preface.add(new Paragraph("Shweta", redFont));
		preface.add(new Paragraph("Dental Laboratory", catFont));
		preface.add(new Paragraph("522, Narayan Peth,Subhadra Co-op.Hsg.Soc,1stFloor,Pune-30", smallBold));
		preface.add(new Paragraph("Mob:9764004512, Email-shwetadentallaboratory@gmail.com", smallBold));
		addEmptyLine(preface, 2);
		preface.add(new Paragraph("Date :" + df.format(dateobj), smallBold));
		preface.setAlignment(Element.ALIGN_RIGHT);
		addEmptyLine(preface, 5);
		createtable(name, month, preface);
		createtotalamounttable(preface);
		addEmptyLine(preface, 4);
		preface.add(new Paragraph("Thanks", redFont2));
		preface.add(new Paragraph("For Shweta", redFont2));
		preface.add(new Paragraph("Dental Laboratory", redFont2));
		document.add(preface);

	}

	private void createtotalamounttable(Paragraph preface) {
		PdfPTable table1 = new PdfPTable(3);
		PdfPCell c1 = new PdfPCell(new Phrase(""));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		table1.addCell(c1);

		c1 = new PdfPCell(new Phrase(""));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table1.addCell(c1);
		c1 = new PdfPCell(new Phrase(""));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		table1.addCell(c1);

		table1.setHeaderRows(1);

		table1.addCell("Total");
		table1.addCell("");
		table1.addCell(price + "/-");

		preface.add(table1);
	}

	private void createtable(String name, String month, Paragraph preface) {
		// Section sec = null;
		jobsDao = new JobsDao(this);
		List<Job> jobs = jobsDao.getJobsByMonthName(month, name);
		listDataHeader = new ArrayList<String>();

		PdfPTable table = new PdfPTable(3);
		PdfPCell c1 = new PdfPCell(new Phrase("Case Id"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Worktype"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Price"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		
		c1 = new PdfPCell(new Phrase("Date"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		table.setHeaderRows(1);

		for (Job job : jobs) {
			if (job.getDoctor() == null) {
				continue;
			}

			table.addCell(job.getId().toString());
			table.addCell(prepareWorks(job));
			table.addCell(job.getPrice().toString() + "/-");
			table.addCell(CommonUtil.convertDate(job.getDate()));

		}
		preface.add(table);
	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
}