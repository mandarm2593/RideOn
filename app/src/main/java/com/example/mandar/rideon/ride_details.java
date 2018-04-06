package com.example.mandar.rideon;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class ride_details extends AppCompatActivity {
EditText editText_time,editText_date,editText_cost,editText_capacity;
    TextView textView_capacity,textView_date,textView_cost,textView_time,textView_ridedetails;
    Button button_ridedetails;
    DatabaseReference dref;
    String name,phone,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_details);
        final String from1 = getIntent().getExtras().getString("from");
        final String strto = getIntent().getExtras().getString("to");
        overridePendingTransition(R.anim.left_in,R.anim.right_out);
        Typeface custom_font=Typeface.createFromAsset(getAssets(),"fonts/Arkhip_font.otf");

        editText_time=(EditText)findViewById(R.id.editText_time);
        editText_date=(EditText)findViewById(R.id.editText_date);
        editText_cost=(EditText)findViewById(R.id.editText_cost);
        editText_capacity=(EditText)findViewById(R.id.editText_capacity);

        textView_ridedetails=(TextView)findViewById(R.id.ride_details_detail);
        textView_time=(TextView)findViewById(R.id.time_header);
        textView_date=(TextView)findViewById(R.id.date_header);
        textView_capacity=(TextView)findViewById(R.id.capacity_header);
        textView_cost=(TextView)findViewById(R.id.cost_header);
        button_ridedetails=(Button)findViewById(R.id.button_ridedetails);
        editText_capacity.setTypeface(custom_font);
        editText_cost.setTypeface(custom_font);
        editText_date.setTypeface(custom_font);
        editText_time.setTypeface(custom_font);
        textView_capacity.setTypeface(custom_font);
        textView_cost.setTypeface(custom_font);
        textView_date.setTypeface(custom_font);
        textView_time.setTypeface(custom_font);
        textView_ridedetails.setTypeface(custom_font);

        button_ridedetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                try{
                    if(user!=null) {
                        dref= FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).getRef();

                        dref.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                            @Override
                            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                                HashMap<String,String> userdata=(HashMap<String,String>)dataSnapshot.getValue();
                                //String n=dataSnapshot.getValue().toString();
                                HashMap<String, String> h = new HashMap<String, String>();
                                String from = from1;
                                String to=strto;
                                String date=editText_date.getText().toString();
                                String capacity=editText_capacity.getText().toString();
                                String time=editText_time.getText().toString();
                                String cost=editText_cost.getText().toString();
                                String data=from+"_"+to;

                                h.put("from", from);
                                h.put("to",to);
                                h.put("date", date);
                                h.put("capacity", capacity);
                                h.put("time", time);
                                h.put("cost", cost);
                                h.put("name",userdata.get("Name").toString());
                                h.put("email",userdata.get("Email").toString());
                                h.put("phone",userdata.get("Phone").toString());
                                h.put("key",data+user.getUid().toString());

                                FirebaseDatabase.getInstance().getReference().child("Events").child(data+user.getUid().toString()).setValue(h);
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }catch (Exception e){
                    Log.d("exception",""+e);}

               // String str =data+user.getUid().toString();
              //  FirebaseDatabase.getInstance().getReference().child("Keys").child(str).setValue(str);

                Intent intent=new Intent(ride_details.this,recycler.class);
                startActivity(intent);
            }
        });

        editText_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentDate=Calendar.getInstance();
                int year=mcurrentDate.get(Calendar.YEAR);
                int day=mcurrentDate.get(Calendar.DAY_OF_MONTH);
                int month=mcurrentDate.get(Calendar.MONTH);
                Log.d("xx","xx");
                DatePickerDialog datePickerDialog;
                datePickerDialog=new DatePickerDialog(ride_details.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editText_date.setText(month+"/"+dayOfMonth+"/"+year);
                    }
                },year,month,day);

                datePickerDialog.show();
            }
        });

        editText_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ride_details.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if(selectedMinute<10)
                            editText_time.setText( selectedHour + ":0" + selectedMinute);
                        else
                            editText_time.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


    }

   /* @Override
    public void onBackPressed() {

        Intent intent1 = new Intent(this,JoinHost.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent1);
    }

*/

}
