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
        switch(position) {
            case 0:
                return new Edit();
            case 1:
                return new Halt();
            default:
                break;
        }

        //should never be hit
        return null;
    }

    @Override
    public int getCount() {
        //the number of tabs in the app
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence tabName = "";
        switch (position) {
            case 0:
                    tabName = "Edit";
                    break;
            case 1:
                    tabName = "Halt";
                    break;
            default:
                    tabName = "";
                    break;
        }
        return tabName;
    }
}
