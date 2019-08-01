package com.alihamuh.ali.tasbeeh.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alihamuh.ali.tasbeeh.Text_Formatter.CustomTypefaceSpan;
import com.alihamuh.ali.tasbeeh.Text_Formatter.FontText;
import com.alihamuh.ali.tasbeeh.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

//import com.alihamuh.ali.tasbeeh.Text_Formatter.CustomTypefaceSpan;
//import com.alihamuh.ali.tasbeeh.Text_Formatter.FontText;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.InterstitialAd;
//import com.google.android.gms.ads.MobileAds;

public class CounterActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    public static final String PREFS_NAME = "MyPrefsFile";
    MediaPlayer bead;
    Button counter;
    private AdView mAdView;
    FontText tasbeeh;
    TextView type;
    InterstitialAd mcInterstitialAd;

    SharedPreferences settings;
    SharedPreferences.Editor editor;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hiding Notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_counter);

        //setting toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.counter_toolbar);
        setSupportActionBar(toolbar);

        //hiding toolbar
        getSupportActionBar().hide();

        //adding drawer layout toggle and other functions
        addDrawerLayoutFunctions(toolbar);

       //setting navigationview listener and adding fonts to navigation view
         setNavigationViewFunctions();

       //declaring variables
        declarationsOfVariables();

        settings = getSettings();

        //adding scrolling to the textview of arabic
        type.setMovementMethod(new ScrollingMovementMethod());

        //getting the type of arabic zikr which the user will see
        gettingTypeofZikr();

        //for hiding and showing main menu toolbar
        onTouchListenersforHidingAndShowingToolbar(toolbar);


        //MobileAds.initialize(this, getStringResourceByName("ad_app_id"));

        //mAdView = findViewById(R.id.adView);
        //AdRequest adRequest = new AdRequest.Builder().build();
        //mAdView.loadAd(adRequest);

        //mcInterstitialAd = new InterstitialAd(this);
        //mcInterstitialAd.setAdUnitId(getStringResourceByName("counter_inter_ad"));
        //To Load Gogole Admob Interstitial Ad
        //mcInterstitialAd.loadAd(new AdRequest.Builder().build());


    }



    public void menuFontApplier(NavigationView nv){

        Menu m = nv.getMenu();
        for (int i=0;i<m.size()-1;i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }

    }


    public void addDrawerLayoutFunctions(Toolbar toolbar){

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.counter_drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }


    public void setNavigationViewFunctions(){

        NavigationView navigationView = (NavigationView) findViewById(R.id.counter_nav_view);

        navigationView.setNavigationItemSelectedListener(this);


        //applying fonts to the navigationview items
        menuFontApplier(navigationView);

    }



    public void declarationsOfVariables(){


        tasbeeh = (FontText) findViewById(R.id.tasbee);
        counter = (Button)findViewById(R.id.count);
        type = (TextView)findViewById(R.id.type);
        bead = MediaPlayer.create(this, R.raw.bead);
    }



    public void gettingTypeofZikr(){


        for(int counter=1;counter<=14; counter++){

         //getting the type of zikr
            if (settings.getString("type", "").equals(getStringResourceByName("zikr_type_key_"+counter)))
            {
                this.tasbeeh
                        .setText(""+settings
                                .getInt(getStringResourceByName("zikr_counter_key_"+counter), 0));
                this.type
                        .setText(settings
                                .getString("text", ""));   //this default string if nothing found has to be changed
            }

        }
        //if no zikr type available then...
        if (settings.getString("type", "").equals(""))
        {
            this.tasbeeh.setText("" + settings.getInt(getStringResourceByName("zikr_type_key_10"), 0));
            this.type.setText("لَآ اِلٰهَ اِلَّا اللّٰهُ مُحَمَّدٌ رَّسُوْلُ اللّٰهِؕ");
        }



    }

    @SuppressLint("ClickableViewAccessibility")
    public void onTouchListenersforHidingAndShowingToolbar(final Toolbar toolbar){

        RelativeLayout layout = (RelativeLayout)findViewById(R.id.activity_counter);


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



    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/muhammadi.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.counter_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
/*
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int i = item.getItemId();


        if (i == R.id.nav_counterAlhumd)
        {

            selectZikr("arabic_Zikr_","zikr_type_key_",1);

        } else if (i == R.id.nav_counterTakbeer)
        {
            selectZikr("arabic_Zikr_","zikr_type_key_",2);
        }
        else if (i == R.id.nav_counterAllah)
        {
            selectZikr("arabic_Zikr_","zikr_type_key_",3);
        }
        else if (i == R.id.nav_counterAstaghfar)
        {
            selectZikr("arabic_Zikr_","zikr_type_key_",4);
        }
        else if (i == R.id.nav_counterAyat)
        {
            selectZikr("arabic_Zikr_","zikr_type_key_",5);
        }
        else if (i == R.id.nav_counterDurood)
        {
            selectZikr("arabic_Zikr_","zikr_type_key_",6);
        }
        else if (i == R.id.nav_counterHasbi)
        {
                selectZikr("arabic_Zikr_","zikr_type_key_",7);
        }
        else if (i == R.id.nav_counterTamjeed)
        {
            selectZikr("arabic_Zikr_","zikr_type_key_",8);
        }
        else if (i == R.id.nav_counterTauheed)
        {
            selectZikr("arabic_Zikr_","zikr_type_key_",9);
        }
        else if (i == R.id.nav_counterKalma)
        {
            selectZikr("arabic_Zikr_","zikr_type_key_",10);
        }
        else if (i == R.id.nav_counterLahawla)
        {
            selectZikr("arabic_Zikr_","zikr_type_key_",11);
        }
        else if (i == R.id.nav_counterLailaha)
        {
            selectZikr("arabic_Zikr_","zikr_type_key_",12);
        }
        else if (i == R.id.nav_counterWabihamdi)
        {
            selectZikr("arabic_Zikr_","zikr_type_key_",13);
        }
        else if (i == R.id.nav_counterSubhan)
        {
            selectZikr("arabic_Zikr_","zikr_type_key_",14);
        }
        else if (i == R.id.nav_counterRate)
        {

            rateApp();
        }
        else if(i==R.id.nav_counterAbout){

            aboutAlertdialog();
        }else if(i==R.id.nav_counterDonate){

            mcInterstitialAd.loadAd(new AdRequest.Builder().build());
            mcInterstitialAd.show();

        }else if(i==R.id.nav_counterShare){
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Tasbeeh is a great app for remembring Allah. It has great interface with complete charts of your daily and weekly progress. https://play.google.com/store/apps/details?id=com.alihamuh.ali.tasbeeh&hl=en";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Digital Tasbeeh");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.counter_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;


    }




    private String getStringResourceByName(String aString) {
        int resId = getResources().getIdentifier(aString, "string", getApplicationContext().getPackageName());
        return getString(resId);
    }



    public void back(View paramView)
    {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void counter(View v)
    {




        bead.start();
        int i = Integer.parseInt(tasbeeh.getText().toString()) + 1;
        this.tasbeeh.setText("" + i);
        settings = getSettings();

        for(int counter=1; counter<=14; counter++){

            if (settings.getString("type", "").equals(getStringResourceByName("zikr_type_key_"+counter)))
            {
                editor = settings.edit();
                editor.putInt(getStringResourceByName("zikr_counter_key_"+counter), i);
                editor.apply();
                return;
            }

        }


        if (settings.getString("type", "").equals("")) {
            editor = settings.edit();
            editor.putInt(getStringResourceByName("zikr_counter_key_10"), i);
            editor.apply();
        }

    }


    public void onDestroy()
    {
        if (this.mAdView != null)
            this.mAdView.destroy();
        super.onDestroy();
    }

    public void onPause()
    {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    public void onResume()
    {
        super.onResume();
        setBackgroundColor();
        if (mAdView != null) {
            mAdView.resume();
        }
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


    public void selectZikr(String text, String Ztype, int pg){

        settings = getSettings();
        editor= settings.edit();
        //int i = settings.getInt(counter[position], 0);

        //editor.putInt(counter[position], i);
        editor.putString("text", getStringResourceByName(text+pg));
        editor.putString("type", getStringResourceByName(Ztype+pg));
        editor.apply();



            tasbeeh.setText("" +settings.getInt(getStringResourceByName("zikr_counter_key_"+pg), 0));
            type.setText(settings.getString("text", ""));   //this default string if nothing found has to be changed




   }


    public SharedPreferences getSettings() {

        settings =this.getSharedPreferences("MyPrefsFile", 0);
        return settings;
    }


    public void setBackgroundColor(){

        SharedPreferences customeTheme= PreferenceManager.getDefaultSharedPreferences(this);

        RelativeLayout Layout = (RelativeLayout) findViewById(R.id.activity_counter);
        if(customeTheme.getString("theme_list","none").equals("blue")) {

            Layout.getBackground().setColorFilter(Color.CYAN, PorterDuff.Mode.ADD);

        }else if(customeTheme.getString("theme_list","none").equals("green")) {

            Layout.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.ADD);

        }else if(customeTheme.getString("theme_list","none").equals("yellow")){

            Layout.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.ADD);
        }else if(customeTheme.getString("theme_list","none").equals("default")){

            Layout.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);

        }


    }


}
