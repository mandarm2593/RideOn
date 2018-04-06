package com.example.mandar.rideon; //change the package name to your project's package name

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

public class MyFirebaseRecylerAdapter extends FirebaseRecyclerAdapter<RideOnDatabase, MyFirebaseRecylerAdapter.MovieViewHolder> {

    private Context mContext;

    static MyFirebaseRecylerAdapter.OnItemClickListener mItemClickListener;


    public MyFirebaseRecylerAdapter(Class<RideOnDatabase> modelClass, int modelLayout,
                                    Class<MovieViewHolder> holder, DatabaseReference ref, Context context) {
        super(modelClass, modelLayout, holder, ref);
        this.mContext = context;
    }

    public void setOnItemClickListener ( final MyFirebaseRecylerAdapter.OnItemClickListener
                                                 mItemClickListener ) {
        this.mItemClickListener =  mItemClickListener;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
        public void onItemLongClick(View view, int position);
        public  void onOverflowMenuClick(View view, int position);
    }
    @Override
    protected void populateViewHolder(MovieViewHolder movieViewHolder, RideOnDatabase db, int i) {

        //TODO: Populate viewHolder by setting the movie attributes to cardview fields
        //movieViewHolder.nameTV.setText(movie.getName());
       // String str=db.getFrom_To();
      // Log.d("database:",""+str);
        movieViewHolder.fromtext.setText(db.getFrom());
        movieViewHolder.totext.setText(db.getTo());
        movieViewHolder.datetext.setText(db.getDate());
        movieViewHolder.timetext.setText(db.getTime());
        movieViewHolder.imageView.setTransitionName(db.getName());

        Typeface custom_font=Typeface.createFromAsset(mContext.getAssets(),"fonts/Arkhip_font.otf");
        movieViewHolder.fromtext.setTypeface(custom_font);
        movieViewHolder.totext.setTypeface(custom_font);
        movieViewHolder.datetext.setTypeface(custom_font);
        movieViewHolder.timetext.setTypeface(custom_font);


        //movieViewHolder.image.setImageResource(R.drawable.done);
    }

    //TODO: Populate ViewHolder and add listeners.
    public static class MovieViewHolder extends RecyclerView.ViewHolder {


        public TextView fromtext;
        public TextView totext;
        public TextView datetext;
        public TextView timetext;
        public ImageView imageView;

        public MovieViewHolder(View v) {

            super(v);

            fromtext = (TextView) v.findViewById(R.id.from_text);
            totext = (TextView) v.findViewById(R.id.to_text);
            datetext = (TextView) v.findViewById(R.id.date_text);
            timetext = (TextView) v.findViewById(R.id.time_text);
            imageView=(ImageView)v.findViewById(R.id.arrowimage);


            v.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick ( View v ) {
                    if( mItemClickListener != null ) {
                        if( getAdapterPosition () !=
                                RecyclerView.NO_POSITION )
                        {
                            mItemClickListener.onItemClick(v,getAdapterPosition());
                        }
                    }
                }
            });

            v.setOnLongClickListener ( new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick ( View v) {
                    if( mItemClickListener != null ) {
                        if( getAdapterPosition () !=
                                RecyclerView.NO_POSITION ) {
                            mItemClickListener.onItemLongClick (v ,
                                    getAdapterPosition () );
                        }
                    }
                    return true ;
                }
            }) ;




        }
    }

}
