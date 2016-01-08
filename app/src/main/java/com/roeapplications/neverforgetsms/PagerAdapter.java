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
        MyFragment frag = new MyFragment();
        return frag;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
