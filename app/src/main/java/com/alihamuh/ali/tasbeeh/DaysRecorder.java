package com.alihamuh.ali.tasbeeh;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;

import com.alihamuh.ali.tasbeeh.Database.CommonSQLiteUtilities;
import com.alihamuh.ali.tasbeeh.Database.DataBaseHelper;

import org.json.JSONArray;

import java.io.IOException;

public class DaysRecorder extends BroadcastReceiver
{
    public static final String PREFS_NAME = "MyPrefsFile";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    JSONArray localJSONArray1;
    JSONArray localJSONArray2;

    private String getStringResourceByName(String aString,Context context) {
        int resId = context.getResources().getIdentifier(aString, "string", context.getApplicationContext().getPackageName());
        return context.getString(resId);
    }


    public SharedPreferences getSettings(Context context) {

        settings =context.getApplicationContext().getSharedPreferences("MyPrefsFile", 0);
        return settings;
    }


    public void savingInstanceData(Context context){

        //settings = context.getApplicationContext().getSharedPreferences("MyPrefsFile", 0);
        getSettings(context);

        int databaseRowIndex =settings.getInt("Dindex",1);
        try {


            DataBaseHelper dbH = new DataBaseHelper(context);

            //String addingIndex= "INSERT INTO "+"ZikrByDay"+" (id) VALUES "+"("+databaseRowIndex+");";
            //dbH.getWritableDatabase().execSQL(addingIndex);



            String TABLE_NAME ="ZikrByDay";


        for(int index =1; index<=113;index++) {

                if(index<15) {
                    editor = getSettings(context).edit();

                    localJSONArray1 = new JSONArray(this.settings.getString(getStringResourceByName("zikr_y_values_" + index, context), "[]"));
                    localJSONArray2 = new JSONArray(this.settings.getString(getStringResourceByName("zikr_x_values_" + index, context), "[]"));
                    localJSONArray1.put(settings.getInt("zikr_counter_key_" + index, 0));
                    //localJSONArray2.put(settings.getInt(getStringResourceByName("zikr_counter_key_"+index, context), 0));
                    int i = localJSONArray1.length();

                    if (localJSONArray1.length() > 1) {
                        localJSONArray2.put(localJSONArray1.getInt(i - 1) - localJSONArray1.getInt(i - 2));
                    } else {

                        localJSONArray2.put(settings.getInt(getStringResourceByName("zikr_counter_key_" + index, context), 0));
                    }

                    editor.putString(getStringResourceByName("zikr_x_values_" + index, context), localJSONArray2.toString());
                    editor.putString(getStringResourceByName("zikr_y_values_" + index, context), localJSONArray1.toString());

                }






                String ColumnName ="Col_"+index;

                int testValue =settings.getInt("zikr_counter_key_"+index, 0);


                //int totalCounterValue =settings.getInt(getStringResourceByName("zikr_counter_key_"+index, context), 0);

                //String addValue = "UPDATE "+"ZikrByDay"+" SET Col_"+databaseColIndex+" = "+totalCounterValue+" WHERE id ="+databaseRowIndex;
                    //String addValue = "INSERT INTO "+"ZikrByDay"+ "(Col_"+databaseColIndex+") VALUES "+"("+totalCounterValue+");";

                //dbH.getWritableDatabase().execSQL(addValue);

            if(index>1) {
                ContentValues cv = new ContentValues();
                cv.put(ColumnName, testValue);
                dbH.getWritableDatabase().update(TABLE_NAME, cv, "id" + "= ?", new String[]{"" + databaseRowIndex});
            }else{
                String addValue = "INSERT INTO " + "ZikrByDay" + "("+ ColumnName+ ") VALUES " + "(" + testValue + ");";
                dbH.getWritableDatabase().execSQL(addValue);

            }


                editor.apply();

                dbH.close();







        }


            Cursor cursor = dbH.getReadableDatabase().query("ZikrByDay",null,null,null,null,null,null);

            if(cursor.moveToFirst()) {
                do {
                    Log.i("MY", "" + cursor.getInt(cursor.getColumnIndex("id"))+"\t"+ cursor.getInt(cursor.getColumnIndex("Col_15")) );
                } while (cursor.moveToNext());
                dbH.close();
            }


            SharedPreferences.Editor newEditor =getSettings(context).edit();

            newEditor.putInt("Dindex",databaseRowIndex+1);

            newEditor.apply();


        } catch (Exception e) {
            e.printStackTrace();
        }












    }


    @Override
    public void onReceive(Context context, Intent paramIntent)
    {
        Log.d("test","at lest I am here:D");
        savingInstanceData(context);

        //Toast.makeText(context,"hey time passed", Toast.LENGTH_SHORT).show();
    }



}
