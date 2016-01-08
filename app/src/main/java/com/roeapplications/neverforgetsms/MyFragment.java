package com.roeapplications.neverforgetsms;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by shane on 2016-01-08.
 */
public class MyFragment extends Fragment{

    public MyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView myText = new TextView(getActivity());
        myText.setText("Hello I am the text inside the first tab!");
        myText.setGravity(Gravity.CENTER);
        return myText;
    }


}
