package com.gvm.vlinedriver;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import com.gvm.vlinedriver.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class Competency extends Activity {
	private Competency_adapter madapter;
	private ArrayList<String> id;
	private ArrayList<String> competency;	
	public boolean[] chbc;
	public boolean[] chbnyc;
	
	public String editable="true";
	private String trainee="";
	private String assessor="";
	private String trainer="";
	public Activity activity;
	private String assessmentid;
	
	private GridView gridview;
	final Calendar myCalendar = Calendar.getInstance();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
			
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_competency);
		final TestAdapter mDbHelper = new TestAdapter(this); 
		mDbHelper.open();
		assessmentid=getIntent().getStringExtra("assessmentid");
		
		//Fill data in top of the page
				TextView tvtraineename=(TextView) findViewById(R.id.textView2);
				TextView tvtraineeemployeeno=(TextView) findViewById(R.id.textView4);
				TextView tvassessorname=(TextView) findViewById(R.id.textView7);
				TextView tvassessoremployeeno=(TextView) findViewById(R.id.textView9);
				TextView tvtitle=(TextView) findViewById(R.id.textView14);
				TextView tvobjective=(TextView) findViewById(R.id.textView16);
				
				ImageView ivcalendar=(ImageView) findViewById(R.id.imageView1);				
				final EditText etdate=(EditText) findViewById(R.id.editText2);
				final Button bsave=(Button) findViewById(R.id.button1);
				
				//get trainee username
				Cursor ctraineeusername=mDbHelper.traineexist();
				if(ctraineeusername.getCount()>0)
				{
					ctraineeusername.moveToFirst();
					trainee=ctraineeusername.getString(ctraineeusername.getColumnIndex("username"));
					tvtraineename.setText(ctraineeusername.getString(ctraineeusername.getColumnIndex("fullname")));
					tvtraineeemployeeno.setText(ctraineeusername.getString(ctraineeusername.getColumnIndex("employeeno")));
				}
				//Get assessor username
				Cursor cassessorusername=mDbHelper.assessoruserid();
				if(cassessorusername.getCount()>0)
				{
					cassessorusername.moveToFirst();
					assessor=cassessorusername.getString(cassessorusername.getColumnIndex("username"));
				}
				//Get role of user
				Cursor crole=mDbHelper.userexist();
				if(crole.getCount()>0)
				{
					crole.moveToFirst();
					if(!crole.getString(crole.getColumnIndex("role")).equalsIgnoreCase("assessor"))
					{
						bsave.setVisibility(View.INVISIBLE);
						etdate.setFocusable(false);
						ivcalendar.setVisibility(View.INVISIBLE);
						editable="false";
					}
				}	
		//Get assessment Record

				Cursor cassessor=mDbHelper.getassessment(assessmentid);
				if(cassessor.getCount()>0)
				{
					cassessor.moveToFirst();	
					tvassessorname.setText(cassessor.getString(cassessor.getColumnIndex("fullname")));
					tvassessoremployeeno.setText(cassessor.getString(cassessor.getColumnIndex("assessoremployeeno")));
					tvobjective.setText(cassessor.getString(cassessor.getColumnIndex("objective")));
					etdate.setText(cassessor.getString(cassessor.getColumnIndex("date")));
					tvtitle.setText(cassessor.getString(cassessor.getColumnIndex("stagename"))+"   "+cassessor.getString(cassessor.getColumnIndex("name")));
				}
		//Fill gridview
				paperList();
				madapter=new Competency_adapter(this, id, competency, chbc,chbnyc,editable,trainee,assessor,trainer);
				gridview=(GridView) findViewById(R.id.gridView1);
				gridview.setAdapter(madapter);
				
				final DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
					
					EditText etdate=(EditText) findViewById(R.id.editText2);
					public void onDateSet(DatePicker view, int year, int monthOfYear,
							int dayOfMonth) {
						myCalendar.set(Calendar.YEAR, year);
						myCalendar.set(Calendar.MONTH, monthOfYear);
						myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
						updateLabel(etdate);
					}
				};
				ivcalendar.setOnClickListener(new OnClickListener() {
					
					public void onClick(View v) {
						new DatePickerDialog(Competency.this, date, myCalendar
			                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
			                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
					}
				});
				
				bsave.setOnClickListener(new OnClickListener() {
					
					public void onClick(View v) {
						
						mDbHelper.assessment10date(Competency.this,assessmentid, etdate.getText().toString());
						
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.act_competency, menu);
		return true;
	}

	private void updateLabel(EditText et) {

	    String myFormat = "dd/MM/yyyy"; //In which you need put here
	    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
	    
	    et.setText(sdf.format(myCalendar.getTime()));
	    }
	
	public void paperList()
	{
		TestAdapter mdbHelper=new TestAdapter(this);
		mdbHelper.open();
		id=new ArrayList<String>();
		competency=new ArrayList<String>();
		Cursor c=mdbHelper.getcompetency();
		int arraysize=c.getCount();	
		chbc=new boolean[arraysize];
		chbnyc=new boolean[arraysize];
		//we need row number because we need to know which cell of array is true or false and each cell is for one row
		int rownumber=0;
		if(c.getCount()>0)
		{
			c.moveToFirst();
			do
			{
				id.add(c.getString(c.getColumnIndex("Id")));
				if(!c.getString(c.getColumnIndex("corridor")).equalsIgnoreCase(""))
				{
					competency.add(c.getString(c.getColumnIndex("taskactivity"))+" ("+ c.getString(c.getColumnIndex("corridor")) +")");
				}
				else
				{
					competency.add(c.getString(c.getColumnIndex("taskactivity")));
				};
				if(c.getString(c.getColumnIndex("c"))!=null && c.getString(c.getColumnIndex("c")).equalsIgnoreCase("true")
							&& c.getString(c.getColumnIndex("assessorsig"))!=null && c.getString(c.getColumnIndex("assessorsig")).equalsIgnoreCase("true")
							&& c.getString(c.getColumnIndex("traineesig"))!=null && c.getString(c.getColumnIndex("traineesig")).equalsIgnoreCase("true"))
				    {chbc[rownumber]=true;}
				
				if(c.getString(c.getColumnIndex("nyc"))!=null && c.getString(c.getColumnIndex("nyc")).equalsIgnoreCase("true"))
					{chbnyc[rownumber]=true;}
				
				rownumber=rownumber+1;
			}
			while(c.moveToNext());
		}
	}

}
