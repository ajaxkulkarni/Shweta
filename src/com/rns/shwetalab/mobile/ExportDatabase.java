package com.rns.shwetalab.mobile;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ExportDatabase extends Activity 

{
	public static final String DATABASE_NAME = "DENTAL";
	Button registerBtn,exportBtn,importBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_export_database);

	
	exportBtn = (Button) findViewById(R.id.Exportbutton);
	importBtn = (Button) findViewById(R.id.Importbutton);
	
	
	exportBtn.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View arg0) {

			try {
				exportDB();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	});

	importBtn.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View arg0) {

			importdb();
		}


	});
	
	}
	
	@SuppressLint("SdCardPath")
	public static void backupDatabase() throws IOException {
		//Open your local db as the input stream
		String inFileName = "/data/data/com.example.androidsqlite/databases/DENTAL_LAB";
		//String inFileName = "/data/data/com.myapp.main/databases/MYDB";
		File dbFile = new File(inFileName);
		FileInputStream fis = new FileInputStream(dbFile);
		String outFileName = Environment.getExternalStorageDirectory()+"/MYDB";
		//Open the empty db as the output stream
		OutputStream output = new FileOutputStream(outFileName);
		//transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = fis.read(buffer))>0){
			output.write(buffer, 0, length);
		}
		output.flush();
		output.close();
		fis.close();
	}

	@SuppressWarnings("resource")
	private void exportDB() throws IOException{
		File data = Environment.getDataDirectory();
		FileChannel source=null;
		FileChannel destination=null;
		String currentDBPath = "/data/"+ "com.rns.shwetalab.mobile" +"/databases/"+DATABASE_NAME;
		String backupDBPath =	DATABASE_NAME;

		File sdCard = Environment.getExternalStorageDirectory();	
		File directory = new File(sdCard.getAbsolutePath() + "/backup-folder");

		if(!directory.isDirectory()){
			directory.mkdirs();	
		}

		File currentDB = new File(data, currentDBPath);
		File backupDB = new File(directory, backupDBPath);
		try {
			source = new FileInputStream(currentDB).getChannel();
			destination = new FileOutputStream(backupDB).getChannel();
			destination.transferFrom(source, 0, source.size());
			source.close();
			destination.close();
			Toast.makeText(this, "DB Exported at Location"+""+directory, Toast.LENGTH_LONG).show();
		} catch(IOException e) {
			e.printStackTrace();
			Toast.makeText(this, "DB Failed !"+e, Toast.LENGTH_LONG).show();
		}
	}

	private void importdb() {


		File sdCard = Environment.getExternalStorageDirectory();	
		File directory = new File(sdCard.getAbsolutePath() + "/backup-folder");
		String fileDBPath =	DATABASE_NAME;
		
		File folder = Environment.getDataDirectory();

		//String fileName = folder.getPath() + "/backup-folder/"+DATABASE_NAME+".db";
		String fileName = "/data/"+ "com.rns.shwetalab.mobile" +"/databases/"+DATABASE_NAME;

		try {
			File myFile = new File(folder,fileName);
			if(myFile.exists())
			{
				myFile.delete();
				Toast.makeText(this, "DB Deleted!", Toast.LENGTH_LONG).show();


				File backupDB = new File(directory, fileDBPath);

				
				
				copyFile(backupDB, myFile);
				
			}
		} catch(Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "DB Failed !"+e, Toast.LENGTH_LONG).show();
		}

	}

	
	 void copyFile(File src, File dst) throws IOException {
	       FileChannel inChannel = new FileInputStream(src).getChannel();
	       FileChannel outChannel = new FileOutputStream(dst).getChannel();
	       try {
	          inChannel.transferTo(0, inChannel.size(), outChannel);
	       } finally {
	          if (inChannel != null)
	             inChannel.close();
	          if (outChannel != null)
	             outChannel.close();
	       }
	    }	
}
