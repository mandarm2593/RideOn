package com.example.mandar.rideon;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class appdetails_pager extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    Toolbar vptoolbar;

    FragmentStatePagerAdapter pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appdetails_pager);


        viewPager=(ViewPager) findViewById(R.id.viewPager);
        tabLayout=(TabLayout)findViewById(R.id.tabLayout);

        pager = new appdetails_pager.MyPagerAdapter(this.getSupportFragmentManager());
        viewPager.setAdapter(pager);
        viewPager.setPageTransformer(true, new vpAnimation());

        tabLayout.setupWithViewPager(viewPager);
        vptoolbar=(Toolbar)findViewById(R.id.vptoolbar);
        setSupportActionBar(vptoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }



    public static class MyPagerAdapter extends FragmentStatePagerAdapter {
        private static int num_movies = 2;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }


        @Override
        public int getCount() {
            return num_movies;
        }


        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return rideonworks_fragment.newInstance(1);


                case 1:
                    return aboutus_fragment.newInstance(1);

                default:

                    return null;


            }


        }


        @Override
        public CharSequence getPageTitle (int position){

            String name;
            //Locale 1=Locale.getDefault();
            switch(position){

                case 0: name ="How Ride on works";
                    break;


                case 1: name="About us";

                    break;


                default: name="";
            }


            return name;
        }


    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



}
