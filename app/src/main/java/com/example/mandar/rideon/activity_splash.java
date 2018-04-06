package com.example.mandar.rideon;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class activity_splash extends AppCompatActivity {

    private static  int time=3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
    @Override
    public void run() {

                Intent intent = new Intent(activity_splash.this, LoginActivity.class);
                startActivity(intent);
        finish();
    }
},time);


    }
}
