package com.example.mandar.rideon;

import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class nonetwork extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nonetwork);
    }

    @Override
    public void onBackPressed() {
       // finish();
        System.exit(0);
    }


}
