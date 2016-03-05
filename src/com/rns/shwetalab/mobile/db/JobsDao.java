package com.rns.shwetalab.mobile.db;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rns.shwetalab.mobile.ViewMonth;
import com.rns.shwetalab.mobile.domain.Job;
import com.rns.shwetalab.mobile.domain.Person;
import com.rns.shwetalab.mobile.domain.WorkPersonMap;
import com.rns.shwetalab.mobile.domain.WorkType;

public class JobsDao {

	private DatabaseHelper dbHelper;
	private SQLiteDatabase jobsDb;
	private PersonDao personDao;
	private WorkTypeDao workTypeDao;
	private WorkPersonMapDao workPersonMapDao;
	private JobWorkTypeMapDao jobWorkTypeMapDao;
	private JobLabMapDao jobLabMapDao;
	private Context context;

	private static String[] cols = { DatabaseHelper.KEY_ID, DatabaseHelper.JOB_PATIENT_NAME, DatabaseHelper.JOB_DATE,
			DatabaseHelper.JOB_SHADE, DatabaseHelper.JOB_DOCTOR, DatabaseHelper.JOB_PRICE, DatabaseHelper.JOB_QUADRENT,
			DatabaseHelper.JOB_POSITION };

	public JobsDao(Context c) {
		context = c;
		personDao = new PersonDao(context);
		workTypeDao = new WorkTypeDao(context);
		workPersonMapDao = new WorkPersonMapDao(context);
		jobWorkTypeMapDao = new JobWorkTypeMapDao(context);
		jobLabMapDao = new JobLabMapDao(context);
	}

	public void openToRead() {
		dbHelper = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.VERSION);
		jobsDb = dbHelper.getReadableDatabase();
	}

	public void openToWrite() {
		dbHelper = new DatabaseHelper(context, DatabaseHelper.DATABASE_NAME, null, DatabaseHelper.VERSION);
		jobsDb = dbHelper.getWritableDatabase();
	}

	public void Close() {
		jobsDb.close();
	}

	public long insertDetails(Job job) {
		Person doctor = personDao.getPerson(job.getDoctor());
		if (doctor == null) {
			return -10;
		}
		job.setDoctor(doctor);
		job.setQuadrent(job.getQuadrent());
		job.setPosition(job.getPosition());
		List<WorkType> workTypes = new ArrayList<WorkType>();
		calculateTotalPrice(job, workTypes);
		openToWrite();
		long val = jobsDb.insert(DatabaseHelper.JOB_TABLE, null, prepareContentValues(job));
		job.setWorkTypes(workTypes);
		job.setId(new Long(val).intValue());
		jobWorkTypeMapDao.insertDetails(job);
		insertLabJobs(job);
		Close();
		return val;

	}

	private void insertLabJobs(Job job) {
		if (job == null || job.getWorkTypes() == null) {
			return;
		}
		for (WorkType workType : job.getWorkTypes()) {
			insertLabJob(workPersonMapDao.getMapsForWorkType(workType, CommonUtil.TYPE_LAB), job);
		}
	}

	private void calculateTotalPrice(Job job, List<WorkType> workTypes) {
		BigDecimal total = BigDecimal.ZERO;
		for (WorkType workType : job.getWorkTypes()) {
			WorkPersonMap map = new WorkPersonMap();
			WorkType workTypeDb = workTypeDao.getWorkType(workType);
			map.setWorkType(workTypeDb);
			map.setPerson(job.getDoctor());
			map = workPersonMapDao.getWorkPersonMap(map);
			if (map != null && map.getPrice() != null) {
				total = total.add(map.getPrice());
			} else if (workTypeDb.getDefaultPrice() != null) {
				total = total.add(workTypeDb.getDefaultPrice());
			}
			workTypes.add(workTypeDb);
		}
		job.setPrice(total);
	}

	private ContentValues prepareContentValues(Job job) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseHelper.JOB_PATIENT_NAME, job.getPatientName());
		contentValues.put(DatabaseHelper.JOB_SHADE, job.getShade());
		contentValues.put(DatabaseHelper.JOB_DATE, CommonUtil.convertDate(job.getDate()));
		contentValues.put(DatabaseHelper.JOB_DOCTOR, job.getDoctor().getId());
		// contentValues.put(DatabaseHelper.JOB_WORK,
		// job.getWorkTypes().getId());
		if (job.getPrice() != null) {
			contentValues.put(DatabaseHelper.JOB_PRICE, job.getPrice().toString());
		}
		contentValues.put(DatabaseHelper.JOB_QUADRENT, job.getQuadrent());
		contentValues.put(DatabaseHelper.JOB_POSITION, job.getPosition());
		return contentValues;
	}

	private void insertLabJob(List<WorkPersonMap> mapsForWorkType, Job job) {
		for (WorkPersonMap map : mapsForWorkType) {
			if (map.getPerson() == null) {
				continue;
			}
			if (CommonUtil.TYPE_LAB.equals(map.getPerson().getWorkType())) {
				Job labJob = new Job();
				labJob.setId(job.getId());
				labJob.setDate(job.getDate());
				labJob.setPatientName(job.getPatientName());
				labJob.setShade(job.getShade());
				// labJob.setWorkType(job.getWorkTypes());
				labJob.setDoctor(map.getPerson());
				labJob.setPrice(map.getPrice());
				jobLabMapDao.insertDetails(labJob);
			}
		}
	}

	public String[] getWorkTypeNames() {
		List<WorkType> workTypes = workTypeDao.getAll();
		if (workTypes.size() == 0) {
			return new String[0];
		}
		List<String> namesList = new ArrayList<String>();
		for (WorkType work : workTypes) {
			namesList.add(work.getName());
		}
		String[] names = new String[namesList.size()];
		names = namesList.toArray(new String[0]);
		return names;
	}

	public List<Job> getJobsByDate(String date, String personType) {
		List<Job> jobs = iterateJobsCursor(queryByDate(date));
		List<Job> jobsByTypes = new ArrayList<Job>();
		if (CommonUtil.TYPE_LAB.equalsIgnoreCase(personType)) 
		{
			return getLabJobs(jobs);
		}
		for (Job job : jobs) 
		{
			
			if (job.getDoctor() != null && personType.equals(job.getDoctor().getWorkType())) {
				jobsByTypes.add(job);
			}
		}
		return jobsByTypes;
	}

	private List<Job> getLabJobs(List<Job> jobs) {
		List<Job> labJobs = new ArrayList<Job>();
		if (jobs == null || jobs.size() == 0) {
			return labJobs;
		}
		for (Job job : jobs) {
			labJobs.addAll(jobLabMapDao.getLabJobsForJob(job));
		}
		return labJobs;
	}

	public Cursor queryByDate(String date) {
		openToWrite();
		return jobsDb.query(DatabaseHelper.JOB_TABLE, cols, DatabaseHelper.JOB_DATE + " = '" + date + "'", null, null,
				null, null);
	}

	public List<Job> getJobs() {
		return iterateJobsCursor(null);
	}

	public List<Job> getJobsByMonth(String month) {
		List<Job> jobs = iterateJobsCursor(queryForMonth(month));
		List<Job> jobsByType = new ArrayList<Job>();
		for (Job job : jobs) {
			if (job.getDoctor() != null) {
				jobsByType.add(job);
			}
		}
		// return iterateJobsCursor(queryForMonth(month));
		return jobs;
	}
	
	public List<Job> getJobsByMonthName(String month,Integer id) {
		List<Job> jobs = iterateJobsCursor(queryForMonthName(month,id));
		List<Job> jobsByType = new ArrayList<Job>();
		for (Job job : jobs) {
			if (job.getDoctor() != null) {
				jobsByType.add(job);
			}
		}
		// return iterateJobsCursor(queryForMonth(month));
		return jobs;
	}

	
	
	
	public BigDecimal getDoctorIncomeForMonth(String month, String personType) {
		// int value = Integer.parseInt(month);
		BigDecimal total = BigDecimal.ZERO;
		List<Job> jobs = getJobsByMonth(month);
		for (Job job : jobs) {
			if (job.getPrice() == null || job.getDoctor() == null
					|| !personType.equals(job.getDoctor().getWorkType())) {
				continue;
			}
			total = total.add(job.getPrice());
		}
		return total;
	}

	private Cursor queryForMonthName(String month, Integer id) {
		openToWrite();
		return jobsDb.query(DatabaseHelper.JOB_TABLE, cols, DatabaseHelper.JOB_DATE + " LIKE '%-"
				+ ViewMonth.months().get(month) + "-2016'" + " AND " + DatabaseHelper.JOB_DOCTOR +" = " + id ,
				null, null, null, null);
	}

	private Cursor queryForMonth(String month) {
		openToWrite();
		return jobsDb.query(DatabaseHelper.JOB_TABLE, cols,
				DatabaseHelper.JOB_DATE + " LIKE '%-" + ViewMonth.months().get(month) + "-2016'", null, null, null,
				null);
	}

	private List<Job> iterateJobsCursor(Cursor cursor) {
		List<Job> jobs = new ArrayList<Job>();
		if (cursor.moveToFirst()) {
			do {
				Job job = new Job();
				job.setId(Integer.parseInt(cursor.getString(0)));
				job.setPatientName(cursor.getString(1));
				job.setDate(CommonUtil.convertDate(cursor.getString(2)));
				job.setShade(cursor.getString(3));
				Person person = personDao.getPerson(cursor.getInt(4));
				job.setDoctor(person);
				job.setWorkTypes(jobWorkTypeMapDao.getWorktypesForJob(job));
				job.setPrice(new BigDecimal(cursor.getInt(5)));
				job.setQuadrent(cursor.getInt(6));
				job.setPosition(cursor.getInt(7));
				jobs.add(job);
			} while (cursor.moveToNext());
		}
		return jobs;
	}

	
	public List<Job> getLabJobsByMonth(String month) {
		List<Job> jobs = getJobsByMonth(month);
		List<Job> totalLabJobs = new ArrayList<Job>();
		if(jobs == null || jobs.size() == 0) {
			return totalLabJobs;
		}
		for(Job job: jobs) {
			List<Job> labJobs = jobLabMapDao.getLabJobsForJob(job);
			if(labJobs == null || labJobs.size() == 0) {
				continue;
			}
			for(Job labJob:labJobs) {
				totalLabJobs.add(labJob);
			}
		}
		return totalLabJobs;
	}
	
}
