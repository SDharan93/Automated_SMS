package com.roeapplications.neverforgetsms;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by shane on 2016-01-08.
 */
public class Monitor extends AppCompatActivity {

    private Toolbar options;
    private TabLayout tabs;
    private ViewPager pager;
    private PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monitor);
        adapter = new PagerAdapter(getSupportFragmentManager());
        options = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(options);
        tabs = (TabLayout) findViewById(R.id.tab_layout);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        tabs.setTabsFromPagerAdapter(adapter);
    }
}
