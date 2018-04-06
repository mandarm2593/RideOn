package com.example.mandar.rideon;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by Nita on 4/27/2017.
 */

public class Detailfragment extends android.support.v4.app.Fragment {
    FloatingActionButton call_button;

    static HashMap datamap;
    private static  final String ARG_Section_number ="section_number";

    public static Detailfragment newInstance(HashMap datahashmap)
    {
        datamap=datahashmap;
        Detailfragment fragment=new Detailfragment();
        Bundle args=new Bundle();
        args.putSerializable(ARG_Section_number,datahashmap);
        fragment.setArguments(args);
        return fragment;
    }
    public Detailfragment()
    {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.detail_layout, container,
                false);

        TextView hosteedby=(TextView)view.findViewById(R.id.textView);
        TextView time=(TextView)view.findViewById(R.id.timedetail);
        TextView cost=(TextView)view.findViewById(R.id.costdetail);
        TextView capacity=(TextView)view.findViewById(R.id.capacitydetail);
        TextView date=(TextView)view.findViewById(R.id.datedetail);



        TextView hosted_by_header=(TextView)view.findViewById(R.id.hostedby);
        TextView time_header=(TextView) view.findViewById(R.id.time_header);


        TextView date_header=(TextView)view.findViewById(R.id.date_header);
        TextView capacity_header=(TextView)view.findViewById(R.id.capacity_header);
        TextView cost_header=(TextView)view.findViewById(R.id.cost_header);
        TextView phone_header=(TextView)view.findViewById(R.id.number_header);
        TextView from_to=(TextView)view.findViewById(R.id.from_to_header);
        TextView ridedetails=(TextView)view.findViewById(R.id.ride_details_detail);
      // ridedetails.setTransitionName("fromtext");
      //  TextView emailtext=(TextView)view.findViewById(R.id.emaildetail1);
        TextView phone_number=(TextView)view.findViewById(R.id.number_header);
        //TextView nametext=(TextView)view.findViewById(R.id.textView6);
        Typeface custom_font=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Arkhip_font.otf");
        String timestr="";
        String coststr="";
        String capacitystr="";
        String datestr="";
        String host="";
        String email="";
        String from="";
        String to="";

        // String phone="";
        call_button=(FloatingActionButton)view.findViewById(R.id.call_button);
        timestr= (String) datamap.get("time");
        coststr=(String)datamap.get("cost");
        capacitystr=(String)datamap.get("capacity");
        datestr=(String)datamap.get("date");
        host=(String)datamap.get("name");
        email=(String)datamap.get("email");
        final  String phone=(String)datamap.get("phone");
        from=(String)datamap.get("from");
        to=(String)datamap.get("to");
        time.setText(timestr);
        cost.setText(coststr);
        capacity.setText(capacitystr);
        date.setText(datestr);
        hosteedby.setText(host);
        //emailtext.setText(email);
        phone_number.setText(phone);

       from_to.setText(from+"  To  "+to);

        time.setTypeface(custom_font);
        cost.setTypeface(custom_font);
        capacity.setTypeface(custom_font);
        date.setTypeface(custom_font);
        hosteedby.setTypeface(custom_font);
        phone_number.setTypeface(custom_font);

        hosted_by_header.setTypeface(custom_font);
        //nametext.setText(host);
        time_header.setTypeface(custom_font);
        capacity_header.setTypeface(custom_font);
        date_header.setTypeface(custom_font);
        cost_header.setTypeface(custom_font);
        phone_header.setTypeface(custom_font);
        from_to.setTypeface(custom_font);
        call_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                 intent.setData(Uri.parse("tel:" + phone));
                 startActivity(intent);
            }
        });

        return view;
    }







}

