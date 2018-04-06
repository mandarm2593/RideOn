package com.example.mandar.rideon;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;

/**
 * Created by Mandar on 4/13/2017.
 */

public class recyclerview_fragment extends Fragment {

    RecyclerView recyclerView;
    // final moviedata Movie = new moviedata();
    MyFirebaseRecylerAdapter myFirebaseRecylerAdapter;
    static String datakey;
    DatabaseReference dbref;
    DatabaseReference ref;
    Datafromcloud metadata;

    private static final String Sec_number = "section_number";

    public static recyclerview_fragment newInstance(int sectionNumber) {

        recyclerview_fragment fragment = new recyclerview_fragment();
        Bundle args = new Bundle();
        args.putInt(Sec_number, sectionNumber);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = null;
        int option = getArguments().getInt(Sec_number);
        rootView = inflater.inflate(R.layout.recyclerview_fragment, container, false);

        DatabaseReference childRef =
                FirebaseDatabase.getInstance().getReference().child("Events").getRef();
        // Log.d("ALL DB=",""+ childRef.getDatabase());

        myFirebaseRecylerAdapter = new MyFirebaseRecylerAdapter(RideOnDatabase.class, R.layout.cardview, MyFirebaseRecylerAdapter.MovieViewHolder.class,
                childRef, getContext());

        metadata = new Datafromcloud();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);

        LinearLayoutManager layoutmanager;
        layoutmanager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutmanager);
        recyclerView.setAdapter(myFirebaseRecylerAdapter);

        if (metadata.getSize() == 0) {
            metadata.setAdapter(myFirebaseRecylerAdapter);
            metadata.setContext(getActivity());
            metadata.initializeDataFromCloud();
        }

        myFirebaseRecylerAdapter.setOnItemClickListener(new MyFirebaseRecylerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final View view, int position) {
                final HashMap<String, ?> movie = (HashMap<String, ?>) metadata.getItem(position);
                String id = (String) movie.get("key");
                DatabaseReference ref = metadata.getFireBaseRef();
              // final TextView fromtext=(TextView) view.findViewById(R.id.from_text);

               final ImageView arrowimage = (ImageView)
                        view.findViewById(R.id.arrowimage);

                final Detailfragment fragment=Detailfragment.newInstance(movie);
                fragment.setSharedElementEnterTransition(new DetailsTransition());
                fragment.setEnterTransition(new android.transition.Fade());
                fragment.setExitTransition(new android.transition.Fade());
                fragment.setSharedElementReturnTransition(new DetailsTransition());

                ref.child(id).addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                        HashMap<String, String> db = (HashMap<String, String>) dataSnapshot.getValue();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .addSharedElement(arrowimage,arrowimage.getTransitionName()).
                                replace(R.id.relative_recycler, fragment).addToBackStack(null)
                                .commit();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            @Override
            public void onItemLongClick(View view, int position) {

            }

            @Override
            public void onOverflowMenuClick(View view, int position) {

            }
        });

        defaultanimation();
        adapteranimation();
        itemanimation();

        return rootView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (menu.findItem(R.id.action_serach) == null)
            inflater.inflate(R.menu.searchmenu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_serach).getActionView();
        if (searchView != null)
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {

                    int position = metadata.findforsearch(query);
                    if (position >= 0)

                        recyclerView.scrollToPosition(position);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return true;
                }
            });
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_serach:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void defaultanimation() {

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(3000);
        animator.setRemoveDuration(100);
        recyclerView.setItemAnimator(animator);
    }

    private void adapteranimation() {

        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(myFirebaseRecylerAdapter);
        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(alphaInAnimationAdapter);
        recyclerView.setAdapter(scaleInAnimationAdapter);
    }

    private void itemanimation() {
        ScaleInAnimator animator = new ScaleInAnimator();
        animator.setInterpolator(new OvershootInterpolator());
        animator.setAddDuration(300);
        animator.setRemoveDuration(300);
        recyclerView.setItemAnimator(animator);

    }
}