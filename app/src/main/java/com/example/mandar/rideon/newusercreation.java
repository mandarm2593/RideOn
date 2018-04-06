package com.example.mandar.rideon;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Nita on 4/17/2017.
 */

public class newusercreation extends Fragment {
    private static final String Sec_number = "section_number";

    public static newusercreation newInstance(int sectionNumber) {

        newusercreation fragment = new newusercreation();
        Bundle args = new Bundle();
        args.putInt(Sec_number, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = null;
        int option = getArguments().getInt(Sec_number);
        rootView = inflater.inflate(R.layout.newuserlayout, container, false);
        return rootView;

    }
}