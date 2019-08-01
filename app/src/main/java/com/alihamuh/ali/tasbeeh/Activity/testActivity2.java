package com.alihamuh.ali.tasbeeh.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.alihamuh.ali.tasbeeh.Adapters.ViewPagerAdapter;
import com.alihamuh.ali.tasbeeh.R;
import com.alihamuh.ali.tasbeeh.Utils;
import com.alihamuh.ali.tasbeeh.customClasses.CompleteZikr;

import java.util.ArrayList;

public class testActivity2 extends baseActivity{

    SharedPreferences settings;
    SharedPreferences.Editor editor;
    ArrayList<CompleteZikr> zikr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);




        setArraylistforAzkar();

        ViewPager tasbeehViewPager = findViewById(R.id.tasbeeh_view_pager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),zikr);

        tasbeehViewPager.setAdapter(adapter);

        tasbeehViewPager.setCurrentItem(ITEM);




    }


    private String getStringResourceByName(String aString) {
        int resId = getResources().getIdentifier(aString, "string", getApplicationContext().getPackageName());
        return getString(resId);
    }

    public String selectZikrText(String text,int pg){

        return getStringResourceByName(text+pg);

    }

    public String selectZikrType(String Ztype, int pg){

        return getStringResourceByName(Ztype+pg);

    }


    public void setArraylistforAzkar(){
        zikr = new ArrayList<>();

        settings =this.getSharedPreferences("MyPrefsFile", 0);

        int numberofZikrs=settings.getInt("numberofZikrs",14);

        for(int counter=1;counter<=numberofZikrs; counter++){

            CompleteZikr instanceZikr =new CompleteZikr();

            instanceZikr.setZikrType(selectZikrType("zikr_type_key_",counter));

            int totalZikrDone=settings.getInt("zikr_counter_key_"+counter, 0);

            instanceZikr.setZikrCounter(totalZikrDone);

            instanceZikr.setZikrText(selectZikrText("arabic_Zikr_",counter));

            instanceZikr.setZikrSerialNo(counter);

            zikr.add(instanceZikr);
        }
    }
}
