package com.example.mandar.rideon;

/**
 * Created by Nita on 4/27/2017.
 */

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Datafromcloud {

    List<Map<String,?>> moviesList;
    DatabaseReference mRef;
    MyFirebaseRecylerAdapter myFirebaseRecylerAdapter;
    Context mContext;

    public void setAdapter(MyFirebaseRecylerAdapter mAdapter) {
        myFirebaseRecylerAdapter = mAdapter;
    }

    public void removeItemFromServer(Map<String,?> movie){
        if(movie!=null){
            String id = (String)movie.get("key");
            mRef.child(id).removeValue();
        }
    }

    public int findforsearch(String find)
    {

        HashMap<String,?> temp=new HashMap<>();
        int val=0;
        String arr[]=new String[moviesList.size()];
        for(int i=0;i<moviesList.size();i++)
        {
            temp=(HashMap<String, ?>) moviesList.get(i);
            arr[i]=(String) temp.get("to");
        }
        for(int i=0;i<moviesList.size();i++)
        {
            if(arr[i].equals(find))
                val=i;
        }
        return val;
    }

    public List<Map<String,?>> Additem(int i,HashMap hs)
    {
        moviesList.add(i,hs);
        return moviesList;
    }
    public void addItemToServer(Map<String,?> movie){
        if(movie!=null){
            String id = (String) movie.get("key");
            mRef.child(id).setValue(movie);
        }
    }

    public void sortmoviesbyname(List<Map<String,?>> moviesList)
    {
        Collections.sort(moviesList, new Comparator<Map<String,?>>() {
            @Override
            public int compare(Map<String,?> h1, Map<String,?> h2) {

                HashMap<String,?> temp1=(HashMap<String,?>)h1;
                HashMap<String,?> temp2=(HashMap<String,?>)h2;
                String s1=(String) temp1.get("name");
                String s2=(String) temp2.get("name");
                return s1.compareTo(s2);
            }
        });
    }
    public DatabaseReference getFireBaseRef(){
        return mRef;
    }
    public void setContext(Context context){mContext = context;}

    public List<Map<String, ?>> getMoviesList() {
        return moviesList;
    }

    public int getSize(){
        return moviesList.size();
    }

    public HashMap getItem(int i){
        if (i >=0 && i < moviesList.size()){
            return (HashMap) moviesList.get(i);
        } else return null;
    }


    public void onItemRemovedFromCloud(HashMap item){
        int position = -1;
        String id=(String)item.get("key");
        for(int i=0;i<moviesList.size();i++){
            HashMap movie = (HashMap)moviesList.get(i);
            String mid = (String)movie.get("key");
            if(mid.equals(id)){
                position= i;
                break;
            }
        }
        if(position != -1){
            moviesList.remove(position);
          //  Toast.makeText(mContext, "Item Removed:" + id, Toast.LENGTH_SHORT).show();
        }
    }

    public void onItemAddedToCloud(HashMap item){
        int insertPosition = 0;
        String id=(String)item.get("key");
        for(int i=0;i<moviesList.size();i++){
            HashMap movie = (HashMap)moviesList.get(i);
            String mid = (String)movie.get("key");
            if(mid.equals(id)){
                return;
            }
            if(mid.compareTo(id)<0){
                insertPosition=i+1;
            }else{
                break;
            }
        }
        moviesList.add(insertPosition,item);
        // Toast.makeText(mContext, "Item added:" + id, Toast.LENGTH_SHORT).show();

    }

    public void onItemUpdatedToCloud(HashMap item){
        String id=(String)item.get("key");
        for(int i=0;i<moviesList.size();i++){
            HashMap movie = (HashMap)moviesList.get(i);
            String mid = (String)movie.get("key");
            if(mid.equals(id)){
                moviesList.remove(i);
                moviesList.add(i,item);
                Log.d("My Test: NotifyChanged",id);

                break;
            }
        }

    }
    public void initializeDataFromCloud() {
        moviesList.clear();
        mRef.addChildEventListener(new com.google.firebase.database.ChildEventListener() {
            @Override
            public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                Log.d("MyTest: OnChildAdded", dataSnapshot.toString());
                HashMap<String,String> movie = (HashMap<String,String>)dataSnapshot.getValue();
                onItemAddedToCloud(movie);
            }

            @Override
            public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                Log.d("MyTest: OnChildChanged", dataSnapshot.toString());
                HashMap<String,String> movie = (HashMap<String,String>)dataSnapshot.getValue();
                onItemUpdatedToCloud(movie);
            }

            @Override
            public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {
                Log.d("MyTest: OnChildRemoved", dataSnapshot.toString());
                HashMap<String,String> movie = (HashMap<String,String>)dataSnapshot.getValue();
                onItemRemovedFromCloud(movie);
            }

            @Override
            public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public Datafromcloud(){

        moviesList = new ArrayList<Map<String,?>>();
        mRef = FirebaseDatabase.getInstance().getReference().child("Events").getRef();
        myFirebaseRecylerAdapter = null;
        mContext = null;

    }


    public int findFirst(String query){

        for(int i=0;i<moviesList.size();i++){
            HashMap hm = (HashMap)getMoviesList().get(i);
            if(((String)hm.get("name")).toLowerCase().contains(query.toLowerCase())){
                return i;
            }
        }
        return 0;

    }
}

