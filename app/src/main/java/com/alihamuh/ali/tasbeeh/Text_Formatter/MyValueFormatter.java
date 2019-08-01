package com.alihamuh.ali.tasbeeh.Text_Formatter;

import com.github.mikephil.charting.utils.ValueFormatter;

import java.text.DecimalFormat;

public class MyValueFormatter implements ValueFormatter {

    private DecimalFormat mFormat;

    public MyValueFormatter() {
        mFormat = new DecimalFormat("###,###,##0"); // use one decimal
    }

    @Override
    public String getFormattedValue(float value) {
        // write your logic here
        return mFormat.format(value) + " times"; // e.g. append a dollar-sign
    }
}
