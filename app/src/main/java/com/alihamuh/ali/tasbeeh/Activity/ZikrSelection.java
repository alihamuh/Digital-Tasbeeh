package com.alihamuh.ali.tasbeeh.Activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alihamuh.ali.tasbeeh.Array_adapter.CustomArrayAdapter;
import com.alihamuh.ali.tasbeeh.R;
import com.alihamuh.ali.tasbeeh.Utils;

//import com.alihamuh.ali.tasbeeh.Array_adapter.CustomArrayAdapter;

public class ZikrSelection extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefsFile";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    private String[] engArray ;
    private String[] arbArray ;
    private String[] cmpltZikrArray;
    private String[] zikerCounterKey;
    private String[] zikerTypeKey;
    ListView ZikrList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_zikr_selection);


        ////////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////////////
    //for setting and sending data to array adapter
        sendingDataToArrayAdapterforListview();

        /*
        int totalNumberOfZikrs=14;

        engArray = new String[totalNumberOfZikrs];
        arbArray = new String[totalNumberOfZikrs];
        cmpltZikrArray= new String[totalNumberOfZikrs];
        zikerCounterKey= new String[totalNumberOfZikrs];
        zikerTypeKey= new String[totalNumberOfZikrs];
        int page_no;

        for(int index=0; index<totalNumberOfZikrs; index++){
            page_no=index+1;

            engArray[index]= getStringResourceByName("english_Zikr_"+page_no);
            arbArray[index]= getStringResourceByName("Zikr_"+page_no);
            cmpltZikrArray[index]=getStringResourceByName("arabic_Zikr_"+page_no);
            zikerCounterKey[index]=getStringResourceByName("zikr_counter_key_"+page_no);
            zikerTypeKey[index]= getStringResourceByName("zikr_type_key_"+page_no);

        }

        ZikrList =(ListView)findViewById(R.id.ZikrListView);
        CustomArrayAdapter adapter = new CustomArrayAdapter(this, engArray,arbArray,cmpltZikrArray,zikerTypeKey,zikerCounterKey);
        ZikrList.setAdapter(adapter);
        */

    }




    private String getStringResourceByName(String aString) {
        int resId = getResources().getIdentifier(aString, "string", getApplicationContext().getPackageName());
        return getString(resId);
    }


    public void sendingDataToArrayAdapterforListview(){


        int totalNumberOfZikrs=14;

        engArray = new String[totalNumberOfZikrs];
        arbArray = new String[totalNumberOfZikrs];
        cmpltZikrArray= new String[totalNumberOfZikrs];
        zikerCounterKey= new String[totalNumberOfZikrs];
        zikerTypeKey= new String[totalNumberOfZikrs];
        int page_no;

        for(int index=0; index<totalNumberOfZikrs; index++){
            page_no=index+1;

            engArray[index]= getStringResourceByName("english_Zikr_"+page_no);
            arbArray[index]= getStringResourceByName("Zikr_"+page_no);
            cmpltZikrArray[index]=getStringResourceByName("arabic_Zikr_"+page_no);
            zikerCounterKey[index]=getStringResourceByName("zikr_counter_key_"+page_no);
            zikerTypeKey[index]= getStringResourceByName("zikr_type_key_"+page_no);

        }

        ZikrList =(ListView)findViewById(R.id.ZikrListView);
        CustomArrayAdapter adapter = new CustomArrayAdapter(this, engArray,arbArray,cmpltZikrArray,zikerTypeKey,zikerCounterKey);
        ZikrList.setAdapter(adapter);

    }

    public void onResume()
    {
        super.onResume();
        setBackgroundColor();

    }

    public void setBackgroundColor(){

        SharedPreferences customeTheme= PreferenceManager.getDefaultSharedPreferences(this);

        LinearLayout Layout = (LinearLayout) findViewById(R.id.activity_selection);
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
