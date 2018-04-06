package com.example.mandar.rideon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class recycler extends AppCompatActivity {
    Toolbar recToolbar;
    DatabaseReference dbref;
    String dbkey="";
    String key_="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
       // final String key = getIntent().getExtras().getString("mydata");
        recToolbar=(Toolbar)findViewById(R.id.recToolbar);
        setSupportActionBar(recToolbar);
        overridePendingTransition(R.anim.right_in,R.anim.left_out);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        getSupportFragmentManager().beginTransaction().replace(R.id.relative_recycler,recyclerview_fragment.newInstance(R.id.rec))
                .commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {


        if(getSupportFragmentManager().getBackStackEntryCount()>0){
            getSupportFragmentManager().popBackStack();

        }
        else {
            Intent intent1 = new Intent(this, JoinHost.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent1);
        }
        }


}
