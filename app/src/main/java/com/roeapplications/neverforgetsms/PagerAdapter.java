package com.roeapplications.neverforgetsms;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by shane on 2016-01-08.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        MyFragment temp = new MyFragment();
        MyFragment frag = temp.newInstance(position);
        return frag;
    }

    @Override
    public int getCount() {
        //the number of tabs in the app
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Tab " + (position+1);
    }
}
