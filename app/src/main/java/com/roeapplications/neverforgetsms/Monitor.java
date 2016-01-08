package com.roeapplications.neverforgetsms;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by shane on 2016-01-08.
 */
public class MonitorTemp extends AppCompatActivity {

    private Toolbar options;
    private TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monitor);
        options = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(options);
        tabs = (TabLayout) findViewById(R.id.tab_layout);
    }
}
