package edu.nhcc.csci2100.baleeted;

import java.util.Random;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class Reassure extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle extra = intent.getExtras();
		
		int maxtime = extra.getInt("maxtime");
		// maxtime can only be set to 0 if we're cancelling any further broadcasts
		if(maxtime == 0) return;
		
		String message = extra.getString("message");
		int mintime = extra.getInt("mintime");
		
		
		
		// this should really be checked at the settings screen, to keep the user
		// from thinking they can really set the mintime greater than the maxtime
		if(mintime >= maxtime) maxtime = mintime + 1;
		
		if( message.length() > 0 ) {
			Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
		
		Intent intout = new Intent(context, Reassure.class);
		intout.putExtra("mintime", mintime);
		intout.putExtra("maxtime", maxtime);
		intout.putExtra("message", getNewMessage(context));
		
		PendingIntent outgoing = PendingIntent.getBroadcast(context, 0, intout, PendingIntent.FLAG_UPDATE_CURRENT);
		
		AlarmManager alMan = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		
		// give me a time between min and max time (which both are in minutes)
		long fireTime = 60000 * (new Random().nextInt(maxtime - mintime) + mintime);
		fireTime += SystemClock.elapsedRealtime();
				
		alMan.set(AlarmManager.ELAPSED_REALTIME, fireTime, outgoing);
	}
	
	public String getNewMessage(Context context) {
		// TODO: make this customizable by the user, and return more than a single string
		return String.format(context.getResources().getString(R.string.default_message));
	}

}
