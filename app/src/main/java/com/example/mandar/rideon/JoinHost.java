package com.example.mandar.rideon;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.NavigationView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class JoinHost extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private final int SPEECH_RECOGNITION_CODE = 1;
    ImageView profileimage;
    TextView name,email;
    String hname,hemail;
    DatabaseReference mref;
    Uri URI1;
    View header;
    DrawerLayout drawerLayout;
    ImageView voicetotext;
    ImageButton host_button,join_button;
    //Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_host);
        Toolbar toolbar=(Toolbar)findViewById(R.id.recToolbar);
        setSupportActionBar(toolbar);
        host_button=(ImageButton)findViewById(R.id.host_button);
        join_button=(ImageButton)findViewById(R.id.join_button);
        voicetotext=(ImageView)findViewById(R.id.voicetotext);
        overridePendingTransition(R.anim.left_in,R.anim.right_out);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseStorage storage=FirebaseStorage.getInstance();
        final StorageReference storageRef = storage.getReference();
        final StorageReference userprofilepicref = storageRef.child(user.getUid().toString()+".jpg");
        YoYo.with(Techniques.ZoomInLeft).duration(700).playOn(host_button);
        YoYo.with(Techniques.ZoomInLeft).duration(700).playOn(join_button);
        host_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Maps=new Intent(JoinHost.this, MapsActivity.class);
                startActivity(Maps);
            }
        });
        join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(JoinHost.this,recycler.class);
                startActivity(intent);
            }
        });

        voicetotext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VoicetoText();
            }
        });

        NavigationView navigationView;
        navigationView=(NavigationView)findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.OpenDrawer,R.string.CloseDrawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

        header=navigationView.getHeaderView(0);

        name = (TextView)header.findViewById(R.id.ppname);
        email = (TextView)header.findViewById(R.id.phemail);
        profileimage=(ImageView)header.findViewById(R.id.my_image12);


       // final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


       /* b.setVisibility(View.GONE);
        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                storageRef.child(user.getUid().toString()+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Toast.makeText(getApplicationContext(),"show",Toast.LENGTH_SHORT).show();
                        Picasso.with(JoinHost.this).load(uri).noPlaceholder().centerCrop().fit()
                                .into(profileimage);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });
                mref.child(user.getUid()).addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                        HashMap<String,String> userdata=(HashMap<String,String>)dataSnapshot.getValue();
                      // if(userdata.get("Name")!=null)

                     //   hname = userdata.get("Name");
          //              hemail = userdata.get("Email");
                       // name.setText(hname);
                      //  email.setText(hemail);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
b.performClick();*/


        mref= FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).getRef();
        mref.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                HashMap<String,String> userdata=(HashMap<String,String>)dataSnapshot.getValue();
                String hname=userdata.get("Name");
                String hemail=userdata.get("Email");
                name.setText(hname);
                email.setText(hemail);
                storageRef.child(user.getUid()+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'users/me/profile.png'
                        Uri downloadUri = uri;
                        Log.d("yy",uri.toString());
                        Picasso.with(JoinHost.this).load(uri).noPlaceholder().centerCrop().fit()
                                .into(profileimage); /// The string(file link) that you need
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(),"failed to retrieve Image",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem Item){

        switch(Item.getItemId()){

            case R.id.settings :
                Intent setting_intent=new Intent(Settings.ACTION_SETTINGS);
                startActivity(setting_intent);
                return true;
            default :
                return super.onOptionsItemSelected(Item);
        }
    }

    private void VoicetoText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speak something...");
        try {
            startActivityForResult(intent, SPEECH_RECOGNITION_CODE);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(this,
                    "Sorry! Speech recognition is not supported in this device.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SPEECH_RECOGNITION_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String text = result.get(0);
                    Log.d("hi",text);

                    Toast.makeText(this,
                            text.toUpperCase(),
                            Toast.LENGTH_SHORT).show();

                    if (text.equalsIgnoreCase("Host")) {

                        host_button.performClick();
                    }
                    if(text.equalsIgnoreCase("Join")){
                        join_button.performClick();
                    }
                    if(text.equalsIgnoreCase("close")){
                        finish();
                        System.exit(0);
                    }
                }
                break;
            }
        }
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem Item) {

        int id = Item.getItemId();
        switch (id) {
            case R.id.aboutme:
                Intent intent=new Intent(JoinHost.this,User_Profile.class);
                startActivity(intent);
                break;
            case R.id.task2:
               intent=new Intent(JoinHost.this,appdetails_pager.class);
                startActivity(intent);
            break;
            case R.id.share:
                intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,"Hi!, I am using Ride On! You can try it on Play store.");
                startActivity(Intent.createChooser(intent,"Share Using"));
            break;
            case R.id.youtubeicon1:

                Intent intent3 = new Intent(this,YoutubeAct.class);
                intent3.putExtra(YoutubeAct.VIDEO_ID, "V5en5drz5N0");
                startActivity(intent3);
                return true;

            case R.id.logout:
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                Intent intent1 = new Intent(this,LoginActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                startActivity(intent1);
                break;
            default:

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();
        }
    }

}
