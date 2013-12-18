package edu.nhcc.csci2100.baleeted;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.*;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

public class MainActivity extends SherlockActivity {
	NumberPicker mindel, maxdel;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mindel = (NumberPicker)findViewById(R.id.minDelay);
        maxdel = (NumberPicker)findViewById(R.id.maxDelay);
        mindel.setMinValue(1);
        mindel.setMaxValue(20);
        maxdel.setMinValue(1);
        maxdel.setMaxValue(120);    	
    }
    
    public void saveClicked(View v) {
    	if( ((Switch)findViewById(R.id.enableSwitch)).isChecked() ) saveBaleeted();
		else quitBaleeted();
    }
        
    public void saveBaleeted() {
      	setupBroadcast(true);
    	Toast toast = Toast.makeText(this, R.string.running, Toast.LENGTH_LONG);
    	toast.setGravity(Gravity.CENTER, 0, 0);
    	toast.show();
    }
    
    public void quitBaleeted() {
    	setupBroadcast(false);
    	Toast toast = Toast.makeText(this, R.string.not_running, Toast.LENGTH_LONG);
    	toast.setGravity(Gravity.CENTER, 0, 0);
    	toast.show();
    }
    
    protected void setupBroadcast(boolean go) {
    	Intent i = new Intent(this, Reassure.class);
    	i.putExtra("message", "");
    	i.putExtra("mintime", mindel.getValue());
    	// set maxtime to 0 if go is false (which will disable further broadcasts)
    	i.putExtra("maxtime", go ? maxdel.getValue() : 0);
    	
    	PendingIntent outgoing = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
    	AlarmManager alMan = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
    	alMan.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), outgoing);
    }

}
