package com.rns.shwetalab.mobile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfWriter;
import com.rns.shwetalab.mobile.adapter.JobsExpandableListViewAdapter;
import com.rns.shwetalab.mobile.db.BalanceAmountDao;
import com.rns.shwetalab.mobile.db.JobsDao;
import com.rns.shwetalab.mobile.domain.Balance_Amount;
import com.rns.shwetalab.mobile.domain.Job;
import com.rns.shwetalab.mobile.domain.WorkType;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
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
	String work;
	BigDecimal amount;
	int a, bal_price;
	int total, current_month, current_year;
	String file = "PositionPdf.pdf";



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
		final String price = extras.getString("Price");
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
			public void onClick(View v) 
			{
				try {
					createPdf(month,name);
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

	private void prepareInvoice(String month, String name) {
		jobsDao = new JobsDao(this);
		List<Job> jobs = jobsDao.getJobsByMonthName(month, name);
		listDataHeader = new ArrayList<String>();

		// String subject = "Dental Invoice";
		// String message1 = " " + id + " " + work + " " + amount;
		// Date dNow = new Date();
		// SimpleDateFormat ft = new SimpleDateFormat(" MM-dd-yyyy ");
		// StringBuilder body = new StringBuilder();
		// String message = " Case Id " + " Worktype " + " Price ";
		// body.append("<html>");
		// body.append("<body>");
		// body.append("<table style = "+"width:100%"+">");
		// body.append("<tr>");
		// body.append("<td>" +"Case ID" +"</td>");
		// body.append(" <td>" + " Worktype" + "</td>");
		// body.append(" <td>" + "Price" + "</td>");
		// body.append("</tr>");
		//
		// for (Job job : jobs) {
		// if (job.getDoctor() == null) {
		// continue;
		// }
		// body.append("<tr>");
		// body.append("<td>" + job.getId() + "</td>");
		// body.append("<td>" + prepareWorks(job) + "</td>");
		// body.append("<td>" + job.getPrice() + "</td>");
		// body.append("</tr");
		//
		// }
		//
		// body.append("</table");
		// body.append("</body");
		// body.append("</html");
		//
		// Intent email = new Intent(Intent.ACTION_SEND);
		// email.putExtra(Intent.EXTRA_EMAIL, new String[] { to });
		// email.putExtra(Intent.EXTRA_SUBJECT, subject);
		// email.putExtra(Intent.EXTRA_TEXT,
		// Html.fromHtml(new StringBuilder().append("<p><b>Shweta Dental
		// Laboratory</b></p>")
		// .append("<small><p>522, Narayan Peth,Subhadra Co-op.Hsg.Soc,1st
		// Floor,Pune-30</p></small>")
		// .append("<small><p>MOBILE.:9764004512
		// EMAIL-shwetadentallaboratory@gmail.com</p></small>")
		// .append("<small><p>To - Shweta Dental Laboratory</p></small>")
		// .append("<small><p></p></small>" + ft.format(dNow)).append("<p></p>"
		// + body).toString()));
		// // email.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(new
		// // StringBuilder().append("<p></p>"+body).toString()));
		//
		// email.setType("message/rfc822");
		// startActivity(Intent.createChooser(email, "Select Email Client"));
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

	private void sendmail(File myFile) 
	{
		Intent email = new Intent(Intent.ACTION_SEND);
		email.putExtra(Intent.EXTRA_SUBJECT,"Dental Invoice");
		email.putExtra(Intent.EXTRA_TEXT, "Hello Brother");
		Uri uri = FileProvider.getUriForFile(this,"com.rns.shwetalab.mobile", myFile);
		email.putExtra(Intent.EXTRA_STREAM, uri);
		email.setType("message/rfc822");
		startActivity(email);

	}


	// more of the getters and setters ….. 
	private void createPdf(String month, String name) throws FileNotFoundException, DocumentException 
	{


		jobsDao = new JobsDao(this);
		List<Job> jobs = jobsDao.getJobsByMonthName(month, name);
		listDataHeader = new ArrayList<String>();
		File pdfFolder = new File(Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_DOCUMENTS), "pdfdemo");
		if (!pdfFolder.exists()) {
			pdfFolder.mkdir();
			Log.i("TAG", "Pdf Directory created");
		}
		File myFile = new File(pdfFolder + ".pdf");
		FileOutputStream output = new FileOutputStream(myFile);


		//Rectangle pagesize = new Rectangle(216f, 720f);
		Document document = new Document();
		PdfWriter.getInstance(document, output);
		document.open();
		addMetaData(document);
		//(document);
		addContent(document);
		//		document.add(new Paragraph("Shweta"));
		//		document.add(new Paragraph("Dental Laboratory"));
		//		document.add(new Paragraph("522, Narayan Peth,Subhadra Co-op.Hsg.Soc,1stFloor,Pune-30"));
		//		document.add(new Paragraph("Mob:9764004512, Email - shwetadentallaboratory@gmail.com"));
		//		document.add(new Paragraph("To :  Shweta Dental Laboratory"));	
		//		for (Job job : jobs) 
		//		{
		//			 if (job.getDoctor() == null) 
		//			 {
		//			 continue;
		//			 }
		//			 
		//		}
		document.close();
		sendmail(myFile);
	}

	private static void addMetaData(Document document) {
		document.addTitle("My first PDF");
		document.addSubject("Using iText");
		document.addKeywords("Java, PDF, iText");
		document.addAuthor("Lars Vogel");
		document.addCreator("Lars Vogel");
	}

	private static void addContent(Document document) throws DocumentException {
		Anchor anchor = new Anchor("First Chapter");
		anchor.setName("First Chapter");

		// Second parameter is the number of the chapter
		Chapter catPart = new Chapter(new Paragraph(anchor), 1);

		Paragraph subPara = new Paragraph("Subcategory 1");
		Section subCatPart = catPart.addSection(subPara);
		subCatPart.add(new Paragraph("Hello"));

		subPara = new Paragraph("Subcategory 2");
		subCatPart = catPart.addSection(subPara);
		subCatPart.add(new Paragraph("Paragraph 1"));
		subCatPart.add(new Paragraph("Paragraph 2"));
		subCatPart.add(new Paragraph("Paragraph 3"));

		// add a list
		//  createList(subCatPart);
		Paragraph paragraph = new Paragraph();
		//  addEmptyLine(paragraph, 5);
		subCatPart.add(paragraph);

		// add a table
		//  createTable(subCatPart);

		// now add all this to the document
		document.add(catPart);

		// Next section
		anchor = new Anchor("Second Chapter");
		anchor.setName("Second Chapter");

		// Second parameter is the number of the chapter
		catPart = new Chapter(new Paragraph(anchor), 1);

		subPara = new Paragraph("Subcategory");
		subCatPart = catPart.addSection(subPara);
		subCatPart.add(new Paragraph("This is a very important message"));

		// now add all this to the document
		document.add(catPart);

	}
}
