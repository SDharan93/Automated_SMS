package com.roeapplications.neverforgetsms;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by shane on 09/01/16.
 */
public class Edit extends Fragment {

    private RecyclerView recycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout. edit_layout, container, false);
        recycler = (RecyclerView) layout.findViewById(R.id.drawerList);
        return layout;
    }
}
