package com.alihamuh.ali.tasbeeh.Activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

//import com.alihamuh.ali.tasbeeh.Text_Formatter.CustomTypefaceSpan;
//import com.alihamuh.ali.tasbeeh.Text_Formatter.MyValueFormatter;
import com.alihamuh.ali.tasbeeh.Text_Formatter.CustomTypefaceSpan;
import com.alihamuh.ali.tasbeeh.Text_Formatter.MyValueFormatter;
import com.alihamuh.ali.tasbeeh.R;
import com.alihamuh.ali.tasbeeh.Utils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
//import com.jjoe64.graphview.series.DataPoint;
//import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;

//import android.support.design.button.MaterialButton;

public class stats_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String PREFS_NAME = "MyPrefsFile";
    BarChart weeklyBarGraph;
    BarChart sumBarGraph;
    BarChart dailyBarGraph;
    SharedPreferences settings;
    JSONArray jsonArray1;


    ActionBarDrawerToggle toggle;


    Boolean weekly=true;
    Button Mbtn;
    String individualZikrKeyValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Utils.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_stats_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.stats_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Statistics");

        //adding drawer layout toggle and other functions
        addDrawerLayoutFunctions(toolbar);

        //setting onclick Litener on the blue button
        settingOnClickListeneronWeeklyButton();

        //setting navigation bars
        setNavigationViewFunctions();



        weeklyBarGraph = (BarChart)findViewById(R.id.weekly_graph);
        sumBarGraph = (BarChart)findViewById(R.id.sum_graph);
        dailyBarGraph =(BarChart)findViewById(R.id.daily_graph);

        //For the total sum of Zikrs
        TotalBarGraph();






    }


    public void addDrawerLayoutFunctions(Toolbar toolbar){

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.stats_drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }




    public void IconChanger(Boolean fromNav){

        if(fromNav){ weekly=true; }

        if(weekly){
            Mbtn.setText(getStringResourceByName("begin"));

            WeekAndDailyGraph(individualZikrKeyValue);


        }else{

            Mbtn.setText(getStringResourceByName("weekly"));

            WeekAndDailyGraph(individualZikrKeyValue);

        }

        weekly =!weekly;

    }



    public void settingOnClickListeneronWeeklyButton(){

        Mbtn = (Button)findViewById(R.id.stats_btn);

        Mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                IconChanger(false);



            }
        });
    }


    public void setNavigationViewFunctions(){

        NavigationView navigationView = (NavigationView) findViewById(R.id.stats_nav_view);

        navigationView.setNavigationItemSelectedListener(this);


        //applying fonts to the navigationview items
        menuFontApplier(navigationView);

    }


    public void menuFontApplier(NavigationView nv){

        Menu m = nv.getMenu();
        for (int i=1;i<m.size()-1;i++) {
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

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/muhammadi.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.stats_drawer_layout);
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


            if (i == R.id.nav_alhumd)
            {
            WeekAndDailyGraph(getStringResourceByName("zikr_x_values_1"));
                Mbtn.setVisibility(View.VISIBLE);
                IconChanger(true);
            } else if (i == R.id.nav_takbeer)
            {
                WeekAndDailyGraph(getStringResourceByName("zikr_x_values_2"));
                Mbtn.setVisibility(View.VISIBLE);
                IconChanger(true);
            }
            else if (i == R.id.nav_allah)
            {
                WeekAndDailyGraph(getStringResourceByName("zikr_x_values_3"));
                Mbtn.setVisibility(View.VISIBLE);
                IconChanger(true);
            }
            else if (i == R.id.nav_astaghfar)
            {
                WeekAndDailyGraph(getStringResourceByName("zikr_x_values_4"));
                Mbtn.setVisibility(View.VISIBLE);
                IconChanger(true);
            }
            else if (i == R.id.nav_ayat)
            {
                WeekAndDailyGraph(getStringResourceByName("zikr_x_values_5"));
                Mbtn.setVisibility(View.VISIBLE);
                IconChanger(true);
            }
            else if (i == R.id.nav_durood)
            {
                WeekAndDailyGraph(getStringResourceByName("zikr_x_values_6"));
                Mbtn.setVisibility(View.VISIBLE);
                IconChanger(true);
            }
            else if (i == R.id.nav_hasbi)
            {
                WeekAndDailyGraph(getStringResourceByName("zikr_x_values_7"));
                Mbtn.setVisibility(View.VISIBLE);
                IconChanger(true);
            }
            else if (i == R.id.nav_tamjeed)
            {
                WeekAndDailyGraph(getStringResourceByName("zikr_x_values_8"));
                Mbtn.setVisibility(View.VISIBLE);
                IconChanger(true);
            }
            else if (i == R.id.nav_tauheed)
            {
                WeekAndDailyGraph(getStringResourceByName("zikr_x_values_9"));
                Mbtn.setVisibility(View.VISIBLE);
                IconChanger(true);
            }
            else if (i == R.id.nav_kalma)
            {
                WeekAndDailyGraph(getStringResourceByName("zikr_x_values_10"));
                Mbtn.setVisibility(View.VISIBLE);
                IconChanger(true);
            }
            else if (i == R.id.nav_lahawla)
            {
                WeekAndDailyGraph(getStringResourceByName("zikr_x_values_11"));
                Mbtn.setVisibility(View.VISIBLE);
                IconChanger(true);
            }
            else if (i == R.id.nav_lailaha)
            {
                WeekAndDailyGraph(getStringResourceByName("zikr_x_values_12"));
                Mbtn.setVisibility(View.VISIBLE);
                IconChanger(true);

            }
            else if (i == R.id.nav_wabihamdi)
            {
                WeekAndDailyGraph(getStringResourceByName("zikr_x_values_13"));
                Mbtn.setVisibility(View.VISIBLE);
                IconChanger(true);
            }
            else if (i == R.id.nav_subhan)
            {
                WeekAndDailyGraph(getStringResourceByName("zikr_x_values_14"));
                Mbtn.setVisibility(View.VISIBLE);
                IconChanger(true);

            }
            else if (i == R.id.nav_barGraph)
            {
                TotalBarGraph();
                Mbtn.setVisibility(View.GONE);
            }else if(i==R.id.nav_Share){
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Here is the share content body";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }else if(i==R.id.nav_rate){
                rateApp();
            }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.stats_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;


    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////////////////////////////

    public void AllTotalGraph(View paramView)
    {
        Toast.makeText(getApplicationContext(), "Find Daily Stats for each Zikr from Top Menu.", Toast.LENGTH_SHORT).show();
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void WeekAndDailyGraph(String Valuekey)
    {

        individualZikrKeyValue =Valuekey;


        this.sumBarGraph.setVisibility(View.GONE);
        //this.weeklyBarGraph.setVisibility(View.VISIBLE);
        ArrayList<String> testArraylist= new ArrayList<>();
        ArrayList<BarEntry> barEntry2 = new ArrayList<>();



        try
        {
            jsonArray1 = new JSONArray(settings.getString(Valuekey,"[]"));

            if(weekly) {
                weeklyBarGraph.setVisibility(View.VISIBLE);
                dailyBarGraph.setVisibility(View.GONE);
                if (jsonArray1.length() > 7) {
                    for (int index = jsonArray1.length() - 7; index < jsonArray1.length(); index++) {
                        int page = index - (jsonArray1.length() - 7);
                        barEntry2.add(new BarEntry(jsonArray1.getInt(index), page));

                    }

                } else {
                    int page2 = 0;

                    for (int index = 0; index < jsonArray1.length(); index++) {
                        page2 = index + 1;

                        barEntry2.add(new BarEntry(jsonArray1.getInt(index), index));

                    }

                    for (int index2 = page2; index2 < 7; index2++) {
                        barEntry2.add(new BarEntry(0, index2));
                    }
                }
                testArraylist = weekFinal();
                BarGraphMaker(weeklyBarGraph,barEntry2,testArraylist,"Daily", Typeface.create("serif-monospace", Typeface.NORMAL),"Weekly",7f);
            }else{

                weeklyBarGraph.setVisibility(View.GONE);
                dailyBarGraph.setVisibility(View.VISIBLE);

                for(int index=0; index<jsonArray1.length();index++){
                    int page= index+1;

                    barEntry2.add(new BarEntry(jsonArray1.getInt(index),index));
                    testArraylist.add(""+page);
                }

                BarGraphMaker(dailyBarGraph,barEntry2,testArraylist,"Daily", Typeface.create("serif-monospace", Typeface.NORMAL),"Daily",7f);

            }


        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

    }



    public void BarGraphMaker(BarChart whichGraph, ArrayList<BarEntry> barEntries, ArrayList<String> srNmEntries, String Description, Typeface tp, String label, float range){

        BarDataSet barDataSet2 = new BarDataSet(barEntries,label);

        barDataSet2.setValueTextSize(20f);
        barDataSet2.setColors(new int[]{Color.GREEN, Color.MAGENTA});


        BarData data2 = new BarData(srNmEntries,barDataSet2);
        data2.setValueFormatter(new MyValueFormatter());
        XAxis xAxis= whichGraph.getXAxis();
        YAxis yAxisl= whichGraph.getAxisLeft();
        YAxis yAxisr = whichGraph.getAxisRight();
        yAxisl.setTextColor(Color.WHITE);
        yAxisr.setTextColor(Color.WHITE);
        xAxis.setTextSize(20);
        xAxis.setTextColor(Color.WHITE);

        //Typeface tp = Typeface.create("serif-monospace",Typeface.NORMAL);
        xAxis.setTypeface(tp);
        whichGraph.setBackgroundColor(getResources().getColor(R.color.redColor));
        whichGraph.setDescriptionTextSize(20f);
        whichGraph.setData(data2);
        whichGraph.setDescription(Description);
        whichGraph.animateXY(2000, 2000);
        whichGraph.getDefaultValueFormatter();
        whichGraph.invalidate();
        whichGraph.setVisibleXRange(range);


    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void TotalBarGraph()
    {
        sumBarGraph.setVisibility(View.VISIBLE);
        weeklyBarGraph.setVisibility(View.GONE);
        settings = getApplicationContext().getSharedPreferences("MyPrefsFile", 0);
        int totalNumberOfZikrs=113;
        int[] zikrsArray= new int[totalNumberOfZikrs];
        ArrayList<String> horizontalLabels= new ArrayList<>();
        ArrayList<BarEntry> barEntry= new ArrayList<>();

        for(int counter=0; counter<totalNumberOfZikrs; counter++){

            int index=counter+1;
            zikrsArray[counter]=settings.getInt("zikr_counter_key_"+index,0);
            barEntry.add(new BarEntry(zikrsArray[counter],counter));
            horizontalLabels.add(getStringResourceByName("Zikr_"+index));

        }

        BarGraphMaker(sumBarGraph,barEntry,horizontalLabels,"Total ZIKR", Typeface.createFromAsset(getAssets(),  "fonts/muhammadi.ttf"),"Total",5f);


    }


    private String getStringResourceByName(String aString) {
        int resId = getResources().getIdentifier(aString, "string", getApplicationContext().getPackageName());
        return getString(resId);
    }


    public ArrayList<String> weekFinal()
    {
        Calendar localCalendar = Calendar.getInstance();
        ArrayList<String> newString = new ArrayList<>();
        newString.add(week(localCalendar.get(Calendar.DAY_OF_WEEK) - 7));
        newString.add(week(localCalendar.get(Calendar.DAY_OF_WEEK) - 6));
        newString.add(week(localCalendar.get(Calendar.DAY_OF_WEEK) - 5));
        newString.add(week(localCalendar.get(Calendar.DAY_OF_WEEK) - 4));
        newString.add(week(localCalendar.get(Calendar.DAY_OF_WEEK) - 3));
        newString.add(week(localCalendar.get(Calendar.DAY_OF_WEEK) - 2));
        newString.add(week(localCalendar.get(Calendar.DAY_OF_WEEK) - 1));

        return newString;

    }


    public String week(int paramInt)
    {
        if ((paramInt == 2) || (paramInt == -5) || (paramInt == 9))
            return "Mon";
        if ((paramInt == 3) || (paramInt == -4) || (paramInt == 10))
            return "Tue";
        if ((paramInt == 4) || (paramInt == -3) || (paramInt == 11))
            return "Wed";
        if ((paramInt == 5) || (paramInt == -2) || (paramInt == 12))
            return "Thu";
        if ((paramInt == 6) || (paramInt == -1) || (paramInt == 13))
            return "Fri";
        if ((paramInt == 7) || (paramInt == 0) || (paramInt == 14))
            return "Sat";
        if ((paramInt == 1) || (paramInt == -6) || (paramInt == 8))
            return "Sun";
        return "Exception Found";
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

}
