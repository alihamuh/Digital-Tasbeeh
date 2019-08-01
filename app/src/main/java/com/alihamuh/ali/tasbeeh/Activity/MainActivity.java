package com.alihamuh.ali.tasbeeh.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.alihamuh.ali.tasbeeh.CommonAppClass;
import com.alihamuh.ali.tasbeeh.Database.CommonSQLiteUtilities;
import com.alihamuh.ali.tasbeeh.Database.DataBaseHelper;
import com.alihamuh.ali.tasbeeh.DaysRecorder;
import com.alihamuh.ali.tasbeeh.NotificationRecorder;
import com.alihamuh.ali.tasbeeh.R;
import com.alihamuh.ali.tasbeeh.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    AdView mainAdView;
    InterstitialAd mInterstitialAd;

    public static CommonAppClass appClass = new CommonAppClass();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setTheme(R.style.BrownAppTheme_NoActionBar);
        Utils.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        //setting toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        //hiding toolbar
        getSupportActionBar().hide();
        getSupportActionBar().setTitle("");

        //adding drawer layout toggle and other functions
        addDrawerLayoutFunctions(toolbar);

        //setting Navigation view functions
        setNavigationViewFunctions();

        //for hiding and showing toolbar
        onTouchListenersforHidingAndShowingToolbar(toolbar);

        //Daily change in the Zikr values is registered
        DailyBarGraphChanger();

        //daily notification as set by the user
        NotificationAlaram();

        //setting and showing banner ad plus initialing FullScreen Ad
        //initializingFullScreenandBannerAds();

         /*
        try {
            DataBaseHelper dbH = new DataBaseHelper(getBaseContext());

            //CommonSQLiteUtilities.logDatabaseInfo(dbH.getWritableDatabase());

            for(int databaseRowIndex=1; databaseRowIndex<5; databaseRowIndex++) {
                //String addingIndex = "INSERT INTO " + "ZikrByDay" + " (id) VALUES " + "(" + databaseRowIndex + ");";
                //dbH.getWritableDatabase().execSQL(addingIndex);

                int testValue =databaseRowIndex+50;

                String TABLE_NAME ="ZikrByDay";

                for(int ColumnIndex=1;ColumnIndex<14; ColumnIndex++) {
                    String ColumnName ="Col_"+ColumnIndex;

                    //String addValue = "INSERT INTO " + "ZikrByDay" + "(Col_" + ColumnIndex + ") VALUES " + "(" + testValue + ");";
                    //String addValue = "UPDATE "+TABLE_NAME +" SET " + ColumnName+ " = '"+testValue+"' WHERE "+Column+ " = "+rowId;
                    //dbH.getWritableDatabase().execSQL(addValue);
                    if(ColumnIndex>1) {
                        ContentValues cv = new ContentValues();
                        cv.put(ColumnName, testValue);
                        dbH.getWritableDatabase().update(TABLE_NAME, cv, "id" + "= ?", new String[]{"" + databaseRowIndex});
                    }else{
                        String addValue = "INSERT INTO " + "ZikrByDay" + "(Col_" + ColumnIndex + ") VALUES " + "(" + testValue + ");";
                        dbH.getWritableDatabase().execSQL(addValue);

                    }

                }
            }

            Cursor cursor = dbH.getReadableDatabase().query("ZikrByDay",null,null,null,null,null,null);

            if(cursor.moveToFirst()) {
                do {
                    Log.i("MY", "" + cursor.getInt(cursor.getColumnIndex("id"))+"\t"+ cursor.getInt(cursor.getColumnIndex("Col_12")) );
                } while (cursor.moveToNext());
                dbH.close();
            }


            //Log.i("MY",CommonSQLiteUtilities.getAllRowsFromTable("ZikrByDay",))

        }catch (IOException e){
            e.printStackTrace();
        }
        */




    }





    public void addDrawerLayoutFunctions(Toolbar toolbar){

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    public void setNavigationViewFunctions(){

        NavigationView navigationView = (NavigationView) findViewById(R.id.main_nav_view);

        navigationView.setNavigationItemSelectedListener(this);


        //applying fonts to the navigationview items
        //menuFontApplier(navigationView);

    }

    @SuppressLint("ClickableViewAccessibility")
    public void onTouchListenersforHidingAndShowingToolbar(final Toolbar toolbar){

        LinearLayout layout = (LinearLayout) findViewById(R.id.activity_main_layout);

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (toolbar.isShown()) {

                        //toolbar.animate().translationY(-toolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
                        getSupportActionBar().hide();

                    } else {
                        getSupportActionBar().show();
                        //toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
                    }
                    return true;
                } else return false;
            }
        });

    }

    public void initializingFullScreenandBannerAds(){

        MobileAds.initialize(this, getStringResourceByName("ad_app_id"));
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getStringResourceByName("main_inter_ad"));
        //To Load Gogole Admob Interstitial Ad
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mainAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mainAdView.loadAd(adRequest);

    }


    private String getStringResourceByName(String aString) {
        int resId = getResources().getIdentifier(aString, "string", getApplicationContext().getPackageName());
        return getString(resId);
    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.global, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(getApplicationContext(), Reset_activity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int i = item.getItemId();


        if (i == R.id.nav_mainSelectZikr)
        {
            startActivity(new Intent(getApplicationContext(), ZikrSelection.class));
        } else if (i == R.id.nav_mainSettings)
        {
            startActivity(new Intent(getApplicationContext(), Reset_activity.class));
        }
        else if (i == R.id.nav_mainStartZikr)
        {
            startActivity(new Intent(getApplicationContext(), testActivity.class));
        }
        else if (i == R.id.nav_mainStats)
        {
            startActivity(new Intent(getApplicationContext(), stats_activity.class));
        }
        else if (i == R.id.nav_mainAbout)
        {
            aboutAlertdialog();
        }
        else if (i == R.id.nav_mainRate)
        {
            rateApp();
        }
        else if (i == R.id.nav_mainDonate)
        {

            mInterstitialAd.loadAd(new AdRequest.Builder().build());
            mInterstitialAd.show();
        }else if(i==R.id.nav_mainShare){
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Tasbeeh is a great app for remembring Allah. It has great interface with complete charts of your daily and weekly progress. https://play.google.com/store/apps/details?id=com.alihamuh.ali.tasbeeh";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Digital Tasbeeh");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_drawer_layout);

        drawer.closeDrawer(GravityCompat.START);
        return true;

    }





    public void resetAll(View paramView)
    {
        startActivity(new Intent(getApplicationContext(), Reset_activity.class));
    }
    ////////////////////////////////////////////////////////////////////
    public void stats(View paramView)
    {
        startActivity(new Intent(getApplicationContext(), stats_activity.class));
    }
    //////////////////////////////////////////////////////////////////////
    public void tasbeeh(View paramView)
    {
        startActivity(new Intent(getApplicationContext(), testActivity2.class));
    }
    /////////////////////////////////////////////////////////////////////////
    public void azkar(View paramView)
    {
        startActivity(new Intent(getApplicationContext(), ZikrSelection.class));
    }

    public void rateApp(){
        Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
        }

    }


    public void aboutAlertdialog(){

        LayoutInflater li= LayoutInflater.from(this);
        final View aboutView= li.inflate(R.layout.about_dialog_box,null);

        AlertDialog aboutAlertDialog = new AlertDialog.Builder(this).create();
        aboutAlertDialog.setCancelable(true);
        aboutAlertDialog.setView(aboutView);
        aboutAlertDialog.show();


    }

    Calendar cal_alarm=Calendar.getInstance();
    ///////////////////////////////////////////////////////////////////////////////
    void NotificationAlaram()
    {

        SharedPreferences getTime= PreferenceManager.getDefaultSharedPreferences(this);

        int timeInMnutes=getTime.getInt("zikr_alaram",0);

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Date dat = new Date();
        //Calendar cal_alarm = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();
        cal_now.setTime(dat);
        cal_alarm.setTime(dat);
        cal_alarm.set(Calendar.HOUR_OF_DAY,timeInMnutes/60);
        cal_alarm.set(Calendar.MINUTE,timeInMnutes%60);
        cal_alarm.set(Calendar.SECOND,00);
        if(cal_alarm.before(cal_now)){
            cal_alarm.add(Calendar.DATE,1);
            //Toast.makeText(this,"one hour passed",Toast.LENGTH_SHORT).show();
        }


        Intent myIntent = new Intent(this,NotificationRecorder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);

        //manager.setRepeating(AlarmManager.RTC_WAKEUP,timeInMnutes*60000,AlarmManager.INTERVAL_DAY, pendingIntent);
        manager.setRepeating(AlarmManager.RTC_WAKEUP,cal_alarm.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);
    }


    public void DailyBarGraphChanger() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Date dat = new Date();
        Calendar cal_alarm = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();
        cal_now.setTime(dat);
        cal_alarm.setTime(dat);
        cal_alarm.set(Calendar.HOUR_OF_DAY,24);
        cal_alarm.set(Calendar.MINUTE,00);
        cal_alarm.set(Calendar.SECOND,00);
        if(cal_alarm.before(cal_now)){
            cal_alarm.add(Calendar.DATE,1);
            //Toast.makeText(this,"one hour passed",Toast.LENGTH_SHORT).show();
        }

        Intent myIntent = new Intent(this, DaysRecorder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);

        manager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),60000, pendingIntent);
        //manager.setRepeating(AlarmManager.RTC_WAKEUP,cal_alarm.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);
    }



    public void onDestroy()
    {
        if (this.mainAdView != null)
        this.mainAdView.destroy();
        super.onDestroy();
    }

    public void onPause()
    {
        if (mainAdView != null) {
        mainAdView.pause();
        }
        super.onPause();
    }

    public void onResume()
    {
        super.onResume();

        //Utils.onActivityCreateSetTheme(this);
        setBackgroundColor();
        NotificationAlaram();
        if (mainAdView != null) {
        mainAdView.resume();
        }
/*
        SharedPreferences abc = PreferenceManager.getDefaultSharedPreferences(this);
        int timeInMnutes=abc.getInt("zikr_alaram",0)*60000;

        int test=90%50;
        SharedPreferences.Editor editor =abc.edit();

        editor.putString("text",""+test);
        editor.apply();
*/
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(appClass.getThemeChanged()) {
            this.recreate();
            appClass.setThemeChanged(false);
        }
    }

    public void setBackgroundColor(){
          /*
        SharedPreferences customeTheme= PreferenceManager.getDefaultSharedPreferences(this);

        LinearLayout Layout = (LinearLayout) findViewById(R.id.activity_main_layout);
        if(customeTheme.getString("theme_list","none").equals("blue")) {

            Layout.getBackground().setColorFilter(Color.CYAN, PorterDuff.Mode.ADD);

        }else if(customeTheme.getString("theme_list","none").equals("green")) {

            Layout.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.ADD);

        }else if(customeTheme.getString("theme_list","none").equals("yellow")){

            Layout.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.ADD);
        }else if(customeTheme.getString("theme_list","none").equals("default")){

            Layout.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);

        }


     */
    }

}
