package com.alihamuh.ali.tasbeeh.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.alihamuh.ali.tasbeeh.Fragments.PageFragment;
import com.alihamuh.ali.tasbeeh.customClasses.CompleteZikr;

import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<CompleteZikr> completeZikr;

    public ViewPagerAdapter(FragmentManager fm, List<CompleteZikr> completeZikr) {
        super(fm);
        this.completeZikr =completeZikr;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.getInstance(completeZikr.get(position));
    }

    @Override
    public int getCount() {
        return completeZikr.size();
    }

}
