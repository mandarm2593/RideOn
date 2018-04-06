package com.example.mandar.rideon;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Mandar on 4/15/2017.
 */

public class aboutus_fragment extends Fragment {
    private static final String Sec_number = "section_number";

    public static aboutus_fragment newInstance(int sectionNumber) {

         aboutus_fragment fragment = new aboutus_fragment();
        Bundle args = new Bundle();
        args.putInt(Sec_number, sectionNumber);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

setRetainInstance(true);
        View rootView = null;
        int option = getArguments().getInt(Sec_number);
        rootView = inflater.inflate(R.layout.aboutus_fragment, container, false);


        return rootView;

    }
}