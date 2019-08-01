package com.alihamuh.ali.tasbeeh;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.alihamuh.ali.tasbeeh.Activity.MainActivity.appClass;

public class Utils {
	//private static int sTheme;




	public static void changeToTheme(Activity activity, int mode) {
		//appClass.setNightmode(mode);
		activity.finish();
		activity.startActivity(new Intent(activity, activity.getClass()));
		/*TaskStackBuilder.create(activity)
				.addNextIntent(new Intent(activity, activity.getClass()))
				.addNextIntent(activity.getIntent())
				.startActivities();*/
		activity.overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);


	}

	public static void onActivityCreateSetTheme(Activity activity) {
		SharedPreferences customeTheme= PreferenceManager.getDefaultSharedPreferences(activity);

		appClass.setMode(Integer.parseInt(customeTheme.getString("theme_list","0")));



		if(appClass.getMode()==0){
			activity.setTheme(R.style.AppTheme_NoActionBar);
		}else if(appClass.getMode() ==1){
			activity.setTheme(R.style.GreenAppTheme_NoActionBar);

		}else if(appClass.getMode()==2){

			activity.setTheme(R.style.BrownAppTheme_NoActionBar);
		}else if(appClass.getMode()==3){
			activity.setTheme(R.style.GrayAppTheme_NoActionBar);

		}else if(appClass.getMode()==4){

			activity.setTheme(R.style.StarAppTheme_NoActionBar);
		}

	}
}