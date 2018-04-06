package com.example.mandar.rideon;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Emptyactivity extends AppCompatActivity {
    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emptyactivity);


        dbref = FirebaseDatabase.getInstance().getReference().child("Users").getRef();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String s = user.getUid().toString();
        Log.d("xx", s);
        String y = dbref.toString();
        Log.d("yy", y);

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        Log.d("XX", "xx");

        if (ni != null) {
           // boolean isconnected = ni.isConnected();

            if (ni.getType()!=ConnectivityManager.TYPE_MOBILE && ni.getType()!=ConnectivityManager.TYPE_WIFI) {

                Intent myin=new Intent(Emptyactivity.this,nonetwork.class);
                myin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                myin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                myin.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(myin);
                finish();
                // There are no active networks.
           /* Toast.makeText(getApplicationContext(),"huhih",Toast.LENGTH_SHORT).show();
            finish();*/
           // System.exit(0);

            } else {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        dbref.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                            @Override
                            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                                HashMap<String, String> userdata = (HashMap<String, String>) dataSnapshot.getValue();
                                //String n=dataSnapshot.getValue().toString();
                                Log.d("zz", "mytest");
                               if(userdata!=null) {
                                   if (userdata.containsKey(user.getUid().toString())) {

                                       Intent intent = new Intent(Emptyactivity.this, JoinHost.class);
                                       intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                       intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                                       startActivity(intent);
                                       finish();

                                   } else {

                                       Intent intent = new Intent(Emptyactivity.this, BasicInfo.class);
                                       intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                       intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                                       startActivity(intent);
                                       finish();
                                   }
                               }

                              else{


                                   Intent intent = new Intent(Emptyactivity.this, BasicInfo.class);
                                   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                   intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                                   startActivity(intent);
                                   finish();

                               }

                                //HashMap<String,String> userdata=(HashMap<String,String>)dataSnapshot.getValue();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }


                }, 1000);
            }


        }

        else{

            Intent myintent=new Intent(Emptyactivity.this,nonetwork.class);
            startActivity(myintent);
            finish();
        }


    }

    }

