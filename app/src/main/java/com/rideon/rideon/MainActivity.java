package com.rideon.rideon;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ImageView profileimage;
    TextView name,email;
    String hname,hemail;
    View header;
    DrawerLayout drawerLayout;
    ImageButton host_button,join_button;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        host_button=(ImageButton)findViewById(R.id.host_button);
        join_button=(ImageButton)findViewById(R.id.join_button);

        NavigationView navigationView;
        navigationView=(NavigationView)findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.OpenDrawer,R.string.CloseDrawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

    }
    @Override
    public boolean onNavigationItemSelected(MenuItem Item) {


        int id = Item.getItemId();
        switch (id) {

            case R.id.aboutme:
                //Intent intent=new Intent(JoinHost.this,User_Profile.class);
                //startActivity(intent);


                break;


            case R.id.task2:
                //intent=new Intent(JoinHost.this,appdetails_pager.class);
                //startActivity(intent);
                break;

            case R.id.share:
                /*intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,"Hi!, I am using Ride On! You can try it on Play store.");
                startActivity(Intent.createChooser(intent,"Share Using"));*/
                break;
            case R.id.logout:



                break;
            default:

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}
