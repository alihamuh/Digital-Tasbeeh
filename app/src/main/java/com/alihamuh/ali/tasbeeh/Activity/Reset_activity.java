package com.alihamuh.ali.tasbeeh.Activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.alihamuh.ali.tasbeeh.R;
import com.alihamuh.ali.tasbeeh.Settings.SettingsFragment;
import com.alihamuh.ali.tasbeeh.Utils;

import static com.alihamuh.ali.tasbeeh.Activity.MainActivity.appClass;

//import com.alihamuh.ali.tasbeeh.Settings.SettingsFragment;

public class Reset_activity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reset_activity);
        Toolbar toolbar =findViewById(R.id.reset_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Settings");
        getSupportFragmentManager().beginTransaction().add(R.id.setting_activity, new SettingsFragment()).commit();



    }




    public void onResume()
    {
        super.onResume();
        //setBackgroundColor();



    }

    public void setBackgroundColor(){

        SharedPreferences customeTheme= PreferenceManager.getDefaultSharedPreferences(this);

        ConstraintLayout Layout = (ConstraintLayout) findViewById(R.id.setting_activity);
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        appClass.setThemeChanged(true);

    }
}
