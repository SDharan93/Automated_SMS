package com.roeapplications.neverforgetsms;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by shane on 19/01/16.
 */
public class Contact extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the user interface layout for this Activity
        // The layout file is defined in the project res/layout/main_activity.xml file
        setContentView(R.layout.contact_layout);
    }
}
