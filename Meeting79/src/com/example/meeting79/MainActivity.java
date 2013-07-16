package com.example.meeting79;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView time,date;
	final String[] DAY_OF_WEEK = {"", "Sundday", "Monday", "Tuesday", "Wensday", "Thursday", "Friday", "Saturday"};
	final String[] MONTH = {"Jenuery", "Februrey", "March", "April", "May", "June", "July", "August", "September", "Actober", "November", "December"};
	
	int day1;
	int hour1;
	int minute1;
	String week, month;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		time = (TextView) findViewById(R.id.time);
		date = (TextView) findViewById(R.id.date);
		
        Calendar cal=new GregorianCalendar();
        day1=cal.get(Calendar.DAY_OF_MONTH);
        hour1=cal.get(Calendar.HOUR);
        switch (hour1) {
		case 0:
			hour1=12;
			break;
		}
        
        minute1=cal.get(Calendar.MINUTE);		
        week = DAY_OF_WEEK[cal.get(Calendar.DAY_OF_WEEK)];
        month = MONTH[cal.get(Calendar.MONTH)];
		
        UpdateNow();        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	void UpdateNow(){
		time.setText(String.format("%02d:%d", hour1, minute1));
		date.setText(week+"\n"+ String.format("%02d", day1)+" "+month);
	}	
	
	public void onMatch(View v){
		Intent i = new Intent(this, ViewControler.class);
		
		startActivity(i);
		System.exit(0); //관련 Activity 모두 종료
	}

}
