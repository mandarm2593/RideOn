package com.example.mandar.rideon;

import android.content.Intent;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    Button search,refresh;
    EditText loc,loc1;
    TextView distance_text,time_text;
   FloatingActionButton floatingActionButton;
    DatabaseReference dbref;
    private GoogleMap mMap;
    private List<GroundOverlay> overlays;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        loc=(EditText)findViewById(R.id.source_text);
        loc1=(EditText)findViewById(R.id.destination_text);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        overridePendingTransition(R.anim.right_in,R.anim.left_out);
        distance_text=(TextView)findViewById(R.id.distance);
        time_text=(TextView)findViewById(R.id.time);
        floatingActionButton=(FloatingActionButton)findViewById(R.id.floatingActionButton);
        Typeface custom_font=Typeface.createFromAsset(getAssets(),"fonts/Arkhip_font.otf");
        loc.setTypeface(custom_font);
        loc1.setTypeface(custom_font);
        distance_text.setTypeface(custom_font);
        time_text.setTypeface(custom_font);
        distance_text.setVisibility(View.GONE);
        time_text.setVisibility(View.GONE);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        refresh=(Button)findViewById(R.id.refresh);
        search=(Button)findViewById(R.id.search);
      refresh.setTypeface(custom_font);
        search.setTypeface(custom_font);

       floatingActionButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(MapsActivity.this,ride_details.class);
               String str=loc.getText().toString();
               String str2=loc1.getText().toString();

                if(str.equals("") || str2.equals(""))
                {
                Toast.makeText(getApplicationContext(),"Enter details",Toast.LENGTH_SHORT).show();

                }
               else
                {
                    intent.putExtra("from",loc.getText().toString());
                    intent.putExtra("to",loc1.getText().toString());
                    startActivity(intent);
                }

           }
       });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();

                distance_text.setText("");
                time_text.setText("");
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location=loc.getText().toString();
                String location1=loc1.getText().toString();
                List<Address> addressList=null;
                List<Address> addressList1=null;
                if(location!=null ||!location.equals(""))
                {
                    Geocoder geocoder=new Geocoder(getApplicationContext());
                    try{
                        addressList=geocoder.getFromLocationName(location,1);
                        addressList1=geocoder.getFromLocationName(location1,1);
                    }
                    catch(IOException e){
                        e.printStackTrace();
                    }
                    Address address=null;
                    Address address1=null;
                    if(addressList.size()==0 || addressList1.size()==0) {
                        Toast.makeText(getApplicationContext(),"Enter Valid Source/destination",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        address = addressList.get(0);
                        address1 = addressList1.get(0);
                        LatLng latLng=null;
                        LatLng latLng1=null;
                        latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        latLng1 = new LatLng(address1.getLatitude(), address1.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(latLng).title(address.getAddressLine(0).toString()));
                        mMap.addMarker(new MarkerOptions().position(latLng1).title(address1.getAddressLine(0).toString()));
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng1));
                        MyDownloadJsonAsynctask searchtask = new MyDownloadJsonAsynctask();
                        searchtask.execute("http://maps.googleapis.com/maps/api/directions/json?origin=" + address.getLatitude() + "," + address.getLongitude() + "&destination=" + address1.getLatitude() + "," + address1.getLongitude() + "&sensor=false&units=metric&mode=driving");

                    }
                }
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-100, 151);
        //LatLng india = new LatLng(20, 88);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        // mMap.addMarker(new MarkerOptions().position(india).title("Marker in india"));

    }
    public class MyDownloadJsonAsynctask extends AsyncTask<String, Void,String>
    {
        @Override
        protected String doInBackground(String... urls) {
            String json=null;
            // moviedatajson threadMovieData = new moviedatajson();
            for(String url:urls){
                json= MyUtility.downloadJSONusingHTTPGetRequest(url);
            }
            Log.d("hi",json);
            return json;
        }

        @Override
        protected void onPostExecute(String json){

            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray route=jsonObject.getJSONArray("routes");
                JSONObject routes=route.getJSONObject(0);
                JSONArray legs=routes.getJSONArray("legs");
                JSONObject dist_time=legs.getJSONObject(0);
                JSONObject distance=dist_time.getJSONObject("distance");

                distance_text.setText(distance.getString("text"));

                JSONObject time=dist_time.getJSONObject("duration");
                time_text.setText(time.getString("text"));
                distance_text.setVisibility(View.VISIBLE);
                time_text.setVisibility(View.VISIBLE);

            }

            catch (JSONException e){

                e.printStackTrace();
                Log.d("ex",e.toString());

            }


        }
    }






}