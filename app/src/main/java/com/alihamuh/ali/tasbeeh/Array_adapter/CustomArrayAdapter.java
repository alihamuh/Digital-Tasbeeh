package com.alihamuh.ali.tasbeeh.Array_adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alihamuh.ali.tasbeeh.Activity.MainActivity;
import com.alihamuh.ali.tasbeeh.Text_Formatter.FontText;
import com.alihamuh.ali.tasbeeh.R;

//import com.alihamuh.ali.tasbeeh.R;
//import com.alihamuh.ali.tasbeeh.Text_Formatter.FontText;


public class CustomArrayAdapter extends BaseAdapter {
    private final Context context;
    private final String[] eng;
    private final String[] arb;
    private final String[] zikr;
    private final String[] type;
    private final String[] counter;
    LayoutInflater inflater;


    public CustomArrayAdapter(Context context, String[] eng, String[] arb, String[] zikr, String[] type, String[] counter) {
        this.context = context;
        this.eng = eng;
        this.arb =arb;
        this.zikr=zikr;
        this.type=type;
        this.counter= counter;
        inflater =(LayoutInflater.from(context));

    }


    @Override
    public int getCount() {
        return eng.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

   static class ViewHolder{

        private TextView eng;
        private FontText arb;
        private Button resetBtn;
   }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();


        //View juzContents = inflater.inflate(R.layout.activity_juz_contents,parent,false);

        //LinearLayout scrllayout = (LinearLayout)juzContents.findViewById(R.id.scrollViewlayout);

        final LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)convertView = vi.inflate(R.layout.custom_button,parent,false);

        mViewHolder.eng= (TextView)convertView.findViewById(R.id.engBtn);
        mViewHolder.arb = (FontText)convertView.findViewById(R.id.arbBtn);
        mViewHolder.resetBtn=(Button)convertView.findViewById(R.id.resetBtn);

        mViewHolder.eng.setText(eng[position]);
        mViewHolder.arb.setText(arb[position]);


        convertView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                SharedPreferences settings;
                SharedPreferences.Editor editor;
                settings = context.getSharedPreferences("MyPrefsFile", 0);
                //int i = settings.getInt(counter[position], 0);
                editor = settings.edit();
                //editor.putInt(counter[position], i);
                editor.putString("text", zikr[position]);
                editor.putString("type", type[position]);
                editor.apply();
                Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
                context.startActivity(intent);
            }
        });


        mViewHolder.resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //LayoutInflater layoutInflater =LayoutInflater.from(context);

                final View dView = vi.inflate(R.layout.select_dialog_box, null);

                if(dView.getParent()!=null) {
                    ((ViewGroup) dView.getParent()).removeView(dView); // <- fix
                }

                //final View dView=vi.inflate(R.layout.select_dialog_box,null);
                final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setCancelable(false);

                final EditText dcustomValue = (EditText) dView.findViewById(R.id.zikr_custom_value);

                Button dCancel =(Button)dView.findViewById(R.id.dialog_cancel);
                Button dSet = (Button)dView.findViewById(R.id.dialog_set);
                Button dZero = (Button)dView.findViewById(R.id.dialog_reset_zero);

                dCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alertDialog.dismiss();
                    }
                });

                dSet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        SharedPreferences settings;
                        SharedPreferences.Editor editor;
                        settings = context.getSharedPreferences("MyPrefsFile", 0);

                        editor = settings.edit();
                        editor.putInt(counter[position], Integer.parseInt(dcustomValue.getText().toString()));
                        editor.apply();
                        alertDialog.dismiss();

                    }
                });

                dZero.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        SharedPreferences settings;
                        SharedPreferences.Editor editor;
                        settings = context.getSharedPreferences("MyPrefsFile", 0);

                        editor = settings.edit();
                        editor.putInt(counter[position], 0);
                        editor.apply();
                        alertDialog.dismiss();

                    }
                });



                alertDialog.setView(dView);
                alertDialog.show();
                /////////////////////////////////////////////////////////////////////////////////////////





            }
        });

        return convertView;
    }




}

