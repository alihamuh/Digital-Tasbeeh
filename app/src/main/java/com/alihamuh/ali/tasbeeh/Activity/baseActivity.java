package com.alihamuh.ali.tasbeeh.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.ViewGroup;

import com.alihamuh.ali.tasbeeh.R;
import com.google.android.gms.ads.AdRequest;

import static com.alihamuh.ali.tasbeeh.Activity.MainActivity.appClass;

public class baseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static int ITEM=0;

    protected void onCreateDrawer() {

        //setContentView(R.layout.activity_test2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        onCreateDrawer() ;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.base, menu);
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

        if(i==R.id.nav_asmaUlHusna){

            Log.d("test",""+this.getClass().getSimpleName());


            if(this.getClass().getSimpleName().equals("testActivity")) {
                ViewPager tasbeehViewPager = findViewById(R.id.tasbeeh_view_pager);
                tasbeehViewPager.setCurrentItem(0);
            }else{
                startActivity(new Intent(getApplicationContext(), testActivity.class));
                //ITEM =0;
            }

        } else if (i == R.id.nav_counterAlhumd)
        {

            selectZikr("arabic_Zikr_","zikr_type_key_",0);

        } else if (i == R.id.nav_counterTakbeer)
        {
            selectZikr("arabic_Zikr_","zikr_type_key_",1);
        }
        else if (i == R.id.nav_counterAllah)
        {
            selectZikr("arabic_Zikr_","zikr_type_key_",2);
        }
        else if (i == R.id.nav_counterAstaghfar)
        {
            selectZikr("arabic_Zikr_","zikr_type_key_",3);
        }
        else if (i == R.id.nav_counterAyat)
        {
            selectZikr("arabic_Zikr_","zikr_type_key_",4);
        }
        else if (i == R.id.nav_counterDurood)
        {
            selectZikr("arabic_Zikr_","zikr_type_key_",5);
        }
        else if (i == R.id.nav_counterHasbi)
        {
            selectZikr("arabic_Zikr_","zikr_type_key_",6);
        }
        else if (i == R.id.nav_counterTamjeed)
        {
            selectZikr("arabic_Zikr_","zikr_type_key_",7);
        }
        else if (i == R.id.nav_counterTauheed)
        {
            selectZikr("arabic_Zikr_","zikr_type_key_",8);
        }
        else if (i == R.id.nav_counterKalma)
        {
            selectZikr("arabic_Zikr_","zikr_type_key_",9);
        }
        else if (i == R.id.nav_counterLahawla)
        {
            selectZikr("arabic_Zikr_","zikr_type_key_",10);
        }
        else if (i == R.id.nav_counterLailaha)
        {
            selectZikr("arabic_Zikr_","zikr_type_key_",11);
        }
        else if (i == R.id.nav_counterWabihamdi)
        {
            selectZikr("arabic_Zikr_","zikr_type_key_",12);
        }
        else if (i == R.id.nav_counterSubhan)
        {
            selectZikr("arabic_Zikr_","zikr_type_key_",13);
        }
        else if (i == R.id.nav_counterRate)
        {

            rateApp();
        }
        else if(i==R.id.nav_counterAbout){

            aboutAlertdialog();
        }else if(i==R.id.nav_counterDonate){

            //mcInterstitialAd.loadAd(new AdRequest.Builder().build());
            //mcInterstitialAd.show();

        }else if(i==R.id.nav_counterShare){
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Tasbeeh is a great app for remembring Allah. It has great interface with complete charts of your daily and weekly progress. https://play.google.com/store/apps/details?id=com.alihamuh.ali.tasbeeh&hl=en";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Digital Tasbeeh");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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


        if(this.getClass().getSimpleName().equals("testActivity2")) {
            ViewPager tasbeehViewPager = findViewById(R.id.tasbeeh_view_pager);
            tasbeehViewPager.setCurrentItem(pg);
        }else{
            startActivity(new Intent(getApplicationContext(), testActivity2.class));
            ITEM =pg;
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if(appClass.getThemeChanged()) {
            this.recreate();
            appClass.setThemeChanged(false);
        }
    }
}
