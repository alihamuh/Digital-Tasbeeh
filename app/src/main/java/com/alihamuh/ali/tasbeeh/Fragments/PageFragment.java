package com.alihamuh.ali.tasbeeh.Fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.alihamuh.ali.tasbeeh.R;
import com.alihamuh.ali.tasbeeh.Text_Formatter.FontText;
import com.alihamuh.ali.tasbeeh.customClasses.CompleteZikr;

public class PageFragment extends Fragment {

    private CompleteZikr zikrComplete;
    MediaPlayer bead;
    //CommonAppClass Myapp=new CommonAppClass();

    private static final float[] NEGATIVE = {
            -1.0f,     0,     0,    0, 255, // red
            0, -1.0f,     0,    0, 255, // green
            0,     0, -1.0f,    0, 255, // blue
            0,     0,     0, 1.0f,   0  // alpha
    };

    //public static Boolean nightMode=false;
    //public static Boolean highlight=false;





    public static PageFragment getInstance(CompleteZikr completeZikr) {
        PageFragment f = new PageFragment();
        Bundle args = new Bundle();
        args.putParcelable("completeZikr_source",completeZikr);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        zikrComplete=getArguments().getParcelable("completeZikr_source");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        View view =inflater.inflate(R.layout.counter_fragment_page, container, false);
        //view.setRotationY(180);



                return view;

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final FontText tasbeeh =(FontText) view.findViewById(R.id.fragment_tasbee);
        Button counter = (Button)view.findViewById(R.id.fragment_count);
        FontText type = (FontText) view.findViewById(R.id.fragment_type);
        bead = MediaPlayer.create(view.getContext(), R.raw.bead);

        if(zikrComplete.getZikrSerialNo()==1||zikrComplete.getZikrSerialNo()==2
                ||zikrComplete.getZikrSerialNo()==3){
            type.setTextSize(85);
        }


        if(zikrComplete.getZikrSerialNo()>=14){

            if(zikrComplete.getZikrSerialNo()==99){
                type.setTextSize(50);
            }else{
                type.setTextSize(85);
            }
        }


        type.setMovementMethod(new ScrollingMovementMethod());


        final SharedPreferences settings = getActivity().getSharedPreferences("MyPrefsFile", 0);

        int totalZikrDone= settings.getInt("zikr_counter_key_"+zikrComplete.getZikrSerialNo(), 0);

        //number zikr done
        tasbeeh.setText(""+totalZikrDone);

        //text of zikr
        type.setText(zikrComplete.getZikrText());


        counter.setOnClickListener(new View.OnClickListener() {
            SharedPreferences.Editor editor;
            @Override
            public void onClick(View view) {



                bead.start();
                int i = Integer.parseInt(tasbeeh.getText().toString()) + 1;
                tasbeeh.setText("" + i);




                editor = settings.edit();
                editor.putInt("zikr_counter_key_"+zikrComplete.getZikrSerialNo(), i);
                editor.apply();

            }
        });



    }


    private String getStringResourceByName(String aString) {
        int resId = getResources().getIdentifier(aString, "string", getActivity().getApplicationContext().getPackageName());
        return getString(resId);

    }

        @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
    }





    @Override
    public void onDestroy() {
        super.onDestroy();
        bead.release();
    }




}