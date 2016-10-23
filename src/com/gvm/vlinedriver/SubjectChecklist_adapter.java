package com.gvm.vlinedriver;

import java.util.ArrayList;

import com.gvm.vlinedriver.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

public class SubjectChecklist_adapter extends BaseAdapter{
	
	private TestAdapter mDbHelper;
	private ArrayList<String> id;
	private ArrayList<String> checknum;
	private ArrayList<String> name;
	private ArrayList<String> critical;
	private Activity activity;
	boolean[] checkBoxState;
	public boolean[] chbsdemonstrated;
	public boolean[] chbsnyc;
	public boolean[] chbscomp;
	public String[] keepetexam;
	public boolean[] chbrpl;
	public boolean[] chbperform;
	String editableachive;
	private String trainee="";
	private String assessor="";
	
	public SubjectChecklist_adapter(Activity activity,ArrayList<String> id,ArrayList<String> checknum,ArrayList<String> name
			,ArrayList<String> critical,boolean[] chbscomp
			,boolean[] chbsdemonstrated,boolean[] chbsnyc,String[] keepetexam,String editableachive,String trainee,String assessor,
			String trainer,boolean[] chbrpl,boolean[] chbperform)
	{
		super();
		mDbHelper = new TestAdapter(activity);
		mDbHelper.open();
		this.id=id;
		this.checknum=checknum;
		this.name=name;
		this.critical=critical;
		this.activity=activity;
		this.chbscomp=chbscomp;
		this.chbsdemonstrated=chbsdemonstrated;
		this.chbsnyc=chbsnyc;
		this.keepetexam=keepetexam;
		this.editableachive=editableachive;
		this.trainee=trainee;
		this.assessor=assessor;
		this.chbrpl=chbrpl;
		this.chbperform=chbperform;
	}

	public int getCount() {
		return id.size();
	}

	public Object getItem(int position) {
		return id.get(position);
		//return position;
	}

	public long getItemId(int position) {
		return 0;
		//return position;
	}
	

	public static class ViewHolder
	{
		public TextView txtId;
		public TextView txtchecknum;
		public TextView txttaskdesc;
		public TextView txtcritical;
		public TextView txte;
		public CheckBox chbe;
		public CheckBox chbcomp;
		public CheckBox chbdemonstrated;
		public CheckBox chbnyc;	
		public CheckBox chbrpl;
		public CheckBox chbperform;
	}

	public View getView(final int position, View convertView, ViewGroup parent) 
	{

		final ViewHolder view;
		LayoutInflater inflator=activity.getLayoutInflater();
		if(convertView==null)
		{
			view= new ViewHolder();
			convertView=inflator.inflate(R.layout.subjectchecklist_row,null);
			
			view.txtId=(TextView) convertView.findViewById(R.id.textView4);
			view.txtchecknum=(TextView) convertView.findViewById(R.id.textView1);
			view.txttaskdesc=(TextView) convertView.findViewById(R.id.textView2);
			view.txtcritical=(TextView) convertView.findViewById(R.id.textView3);
			view.txte=(TextView) convertView.findViewById(R.id.textView5);
			
			view.chbe=(CheckBox) convertView.findViewById(R.id.checkBox4);
			view.chbcomp=(CheckBox) convertView.findViewById(R.id.checkBox1);
			view.chbdemonstrated=(CheckBox) convertView.findViewById(R.id.checkBox2);
			view.chbnyc=(CheckBox) convertView.findViewById(R.id.checkBox3);
			view.chbrpl=(CheckBox) convertView.findViewById(R.id.checkBox5);
			view.chbperform=(CheckBox) convertView.findViewById(R.id.cbperform);

			convertView.setTag(view);
		}
		else
		{
			view=(ViewHolder) convertView.getTag();
		}
		
		view.txtId.setText(id.get(position));
		
		view.txtchecknum.setText(checknum.get(position));
		view.txtchecknum.setPaintFlags(view.txtchecknum.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
		
		view.txttaskdesc.setText(name.get(position));
		view.txtcritical.setText(critical.get(position));
		//NA or checkbox
		//NA means Not Applicable
		if(keepetexam[position].equalsIgnoreCase("N/A"))
		{
			view.txte.setText(keepetexam[position]);
			view.chbe.setVisibility(View.GONE);
			view.txte.setVisibility(View.VISIBLE);
		}
		else
		{
			view.txte.setVisibility(View.GONE);
			view.chbe.setVisibility(View.VISIBLE);
			view.chbe.setOnCheckedChangeListener(null);
	        if(keepetexam[position].equalsIgnoreCase("True"))
	        	view.chbe.setChecked(true);
	        else  
	        	view.chbe.setChecked(false);
		}
		
	//Intervention
		view.chbperform.setOnCheckedChangeListener(null);
        if(chbperform[position])
        	view.chbperform.setChecked(false);
        else  
        	view.chbperform.setChecked(true);
        
	//If RPL is checked, All item in checklist row is locked. If checklist is completed, RPL is locked
	//Completed
		view.chbcomp.setOnCheckedChangeListener(null);
        if(chbscomp[position])
        {
        	view.chbcomp.setChecked(true);
        	view.chbrpl.setEnabled(false);
			view.chbrpl.setFocusable(false);
        }
        else  
        {
        	view.chbcomp.setChecked(false);
        	view.chbrpl.setEnabled(true);
			view.chbrpl.setFocusable(true);
        }
      //RPL
      	view.chbrpl.setOnCheckedChangeListener(null);
             if(chbrpl[position])
             {
              	view.chbrpl.setChecked(true);
              	view.chbdemonstrated.setEnabled(false);
            	view.chbdemonstrated.setFocusable(false);
    			view.chbnyc.setEnabled(false);
    			view.chbnyc.setFocusable(false);
    			view.chbe.setEnabled(false);
    			view.chbe.setFocusable(false);
    			
             }
             else 
              {
              	view.chbrpl.setChecked(false);
              	
              	view.chbdemonstrated.setEnabled(true);
            	view.chbdemonstrated.setFocusable(true);
    			view.chbnyc.setEnabled(true);
    			view.chbnyc.setFocusable(true);
    			view.chbe.setEnabled(true);
    			view.chbe.setFocusable(true);
              }
              
              view.chbrpl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                	  
                   if(isChecked)
                   {
                	   //Show message before to check RPL
                		final TestAdapter mDbHelper = new TestAdapter(activity);  
                	    AlertDialog.Builder alertDlg = new AlertDialog.Builder(activity);

                	    alertDlg.setMessage(R.string.rplmessage);

                	    alertDlg.setCancelable(false); 

                	    alertDlg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {    

                	    			public void onClick(DialogInterface dialog, int id) {
                	    	  		chbrpl[position]=true;
		                       	   //If rpl is true checklist is locked
		                       	    view.chbdemonstrated.setEnabled(false);
		       	                   	view.chbdemonstrated.setFocusable(false);
		       	           			view.chbnyc.setEnabled(false);
		       	           			view.chbnyc.setFocusable(false);
		       	           			view.chbe.setEnabled(false);
		       	           			view.chbe.setFocusable(false);
		                         	 mDbHelper.checkchangeassessor(activity,"checklist", "rpl", "true", "checknum",checknum.get(position),trainee,assessor);
                	           }     
                	    }
                	     );
                	     alertDlg.setNegativeButton("No", new DialogInterface.OnClickListener() {
                	    	 public void onClick(DialogInterface dialog, int which) {
                	    		 view.chbrpl.setChecked(false);
                	    }
                	    });
                	     alertDlg.create().show(); 
                   }
                   else
                   {
                	   chbrpl[position]=false;
                	   //If rpl is false checklist is not locked
                	   view.chbdemonstrated.setEnabled(true);
	                   	view.chbdemonstrated.setFocusable(true);
	           			view.chbnyc.setEnabled(true);
	           			view.chbnyc.setFocusable(true);
	           			view.chbe.setEnabled(true);
	           			view.chbe.setFocusable(true);
	           			mDbHelper.checkchangeassessor(activity,"checklist", "rpl", "false", "checknum",checknum.get(position),trainee,assessor);
                   }
                  }
                  });
        
       //D 
		view.chbdemonstrated.setOnCheckedChangeListener(null);
        if(chbsdemonstrated[position])
        	view.chbdemonstrated.setChecked(true);
        else  
        	view.chbdemonstrated.setChecked(false);
        
        view.chbdemonstrated.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {    
             if(isChecked){
            	 chbsdemonstrated[position]=true;
            	 mDbHelper.checkchangeassessor(activity,"subjectchecklist", "demonstration", "true", "Id",id.get(position),trainee,assessor);
             }
             else{
            	 chbsdemonstrated[position]=false;
            	 mDbHelper.checkchangeassessor(activity,"subjectchecklist", "demonstration", "false", "Id",id.get(position),trainee,assessor);
             }
            }
            });
        //nyc
		view.chbnyc.setOnCheckedChangeListener(null);
        if(chbsnyc[position])
        	view.chbnyc.setChecked(true);
        else  
        	view.chbnyc.setChecked(false);
        
        view.chbnyc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {    
             if(isChecked)
             {
            	 chbsnyc[position]=true;
            	 mDbHelper.checkchangeassessor(activity,"subjectchecklist", "nyc", "true", "Id",id.get(position),trainee,assessor);
             }
             else
             {
            	 chbsnyc[position]=false;
            	 mDbHelper.checkchangeassessor(activity,"subjectchecklist", "nyc", "false", "Id",id.get(position),trainee,assessor);
             }
            }
            });
        //If RPL is checked checklist is locked and user can't click on checklist num
		view.txtchecknum.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Context context=v.getContext();
				if(chbrpl[position])
				{
					Toast.makeText(context,R.string.checklistlockmessage, Toast.LENGTH_LONG).show();
				}
				else
				{
					String checknum=view.txtchecknum.getText().toString();
					Intent intent=new Intent(context,ChecklistTaskTab.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.putExtra("checknum", checknum);
					
					context.startActivity(intent);
				}
			}
		});
		
		//E
        view.chbe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {    
             if(isChecked)
             {
            	 keepetexam[position]="true";
            	 mDbHelper.checkchangeassessor(activity,"subjectchecklist","explained","true", "Id",id.get(position),trainee,assessor);
             }
             else
             {
            	 keepetexam[position]="false";
            	 mDbHelper.checkchangeassessor(activity,"subjectchecklist","explained","false", "Id",id.get(position),trainee,assessor);
            	// mDbHelper.checklistexplained("false", checknum.get(position),trainee,assessor);
             }
            }
            });

		//Check permission
		if(!editableachive.equalsIgnoreCase("true"))
		{
			view.chbdemonstrated.setEnabled(false);
			view.chbnyc.setEnabled(false);
			view.chbe.setEnabled(false);
			view.chbrpl.setEnabled(false);
		}
		return convertView;
	}

}
