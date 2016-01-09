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

    public static final String ARG_PAGE = "arg_page";
    public MyFragment() {

    }

    //Fragments cannot have parameters with constructors, use this as substitution
    public static MyFragment newInstance(int pageNumber) {
        MyFragment frag = new MyFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_PAGE, pageNumber);
        frag.setArguments(arguments);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        int pageNumber = arguments.getInt(ARG_PAGE);
        //int pageNumber = 0;
        TextView myText = new TextView(getActivity());
        myText.setText("Hello I am the text inside the first tab. Page: " + pageNumber);
        myText.setGravity(Gravity.CENTER);
        return myText;
    }


}
