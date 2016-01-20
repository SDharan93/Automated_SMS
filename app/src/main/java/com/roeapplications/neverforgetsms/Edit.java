package com.roeapplications.neverforgetsms;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shane on 09/01/16.
 */
public class Edit extends Fragment {

    private RecyclerView recycler;
    private RecAdapter adapter;
    private FloatingActionButton button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout. edit_layout, container, false);
        recycler = (RecyclerView) layout.findViewById(R.id.drawerList);
        adapter = new RecAdapter(getActivity(), getData());
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        button = (FloatingActionButton) layout.findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "This is a test", Toast.LENGTH_LONG).show();
                Intent i = new Intent(v.getContext(), Contact.class);
                startActivity(i);
            }
        });
        return layout;
    }

    public static List<Information> getData() {
        List<Information> data = new ArrayList<>();
        String[] titles = {"Brother", "Sister", "Mother", "Father", "Girlfriend"};

        for (int i=0; i<titles.length; i++) {
            Information current = new Information();
            current.title = titles[i];
            data.add(current);
        }

        return data;
    }
}
