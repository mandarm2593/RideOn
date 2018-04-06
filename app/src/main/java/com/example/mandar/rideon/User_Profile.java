package com.example.mandar.rideon;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.HashMap;

public class User_Profile extends AppCompatActivity {
EditText edittext_name,edittext_phone,edittext_email;
TextView user_name,user_phone,user_email;
    DatabaseReference dbref;
    Toolbar user_toolbar;
    private  static int IMG_RES=1;

    Button button_save;
    FloatingActionButton button_edit;
    ImageView my_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__profile);
        overridePendingTransition(R.anim.right_in,R.anim.left_out);
  //user_toolbar=(Toolbar)findViewById(R.id.userprofile_toolbar);
    //    setSupportActionBar(user_toolbar);

        user_toolbar=(Toolbar)findViewById(R.id.userprofile_toolbar);
        setSupportActionBar(user_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

   edittext_email=(EditText)findViewById(R.id.editText_email);
        edittext_name=(EditText)findViewById(R.id.editText_name);
        edittext_phone=(EditText)findViewById(R.id.editText_phone);
      edittext_email.setEnabled(false);
        edittext_phone.setEnabled(false);
        edittext_name.setEnabled(false);
        button_save=(Button) findViewById(R.id.button_save);
      button_save.setEnabled(true);
       button_save.setVisibility(View.GONE);
        button_edit=(FloatingActionButton) findViewById(R.id.button_edit);
        my_image=(ImageView)findViewById(R.id.my_image12);
        my_image.setEnabled(false);
        user_name=(TextView)findViewById(R.id.user_name);
        user_phone=(TextView)findViewById(R.id.user_phone);
        user_email=(TextView)findViewById(R.id.user_email);
        Typeface custom_font=Typeface.createFromAsset(getAssets(),"fonts/Arkhip_font.otf");
        edittext_name.setTypeface(custom_font);
        //edittext_email.setTypeface(custom_font);
        edittext_phone.setTypeface(custom_font);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        collapsingToolbar.setTitle("My Profile");

        collapsingToolbar.setExpandedTitleColor(Color.WHITE);
        collapsingToolbar.setCollapsedTitleTypeface(custom_font);
        collapsingToolbar.setExpandedTitleTypeface(custom_font);


        user_name.setTypeface(custom_font);
        user_phone.setTypeface(custom_font);
        user_email.setTypeface(custom_font);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseStorage storage=FirebaseStorage.getInstance();
        final StorageReference storageRef = storage.getReference();
        final StorageReference userprofilepicref = storageRef.child(user.getUid().toString()+".jpg");

   button_edit.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           edittext_email.setEnabled(true);
           button_edit.setVisibility(View.GONE);
           edittext_phone.setEnabled(true);
           edittext_name.setEnabled(true);
         //  edittext_name.performClick();
           edittext_name.requestFocus();
           my_image.setEnabled(true);

           button_save.setVisibility(View.VISIBLE);

       }
   });
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name,email,phone;
                name=edittext_name.getText().toString();
                Log.d("name",name );
                email=edittext_email.getText().toString();
                Log.d("email", email);
                phone=edittext_phone.getText().toString();
                Log.d("phone", phone);
                edittext_name.setEnabled(false);
                edittext_phone.setEnabled(false);
                edittext_email.setEnabled(false);
            my_image.setEnabled(false);
                button_save.setVisibility(View.GONE);
                button_edit.setVisibility(View.VISIBLE);

                if(user!=null){

                    HashMap<String,String> h=new HashMap<String, String>();
                    String Name=edittext_name.getText().toString();
                    String Email=edittext_email.getText().toString();
                    String Phone=edittext_phone.getText().toString();
                    h.put("Name",Name);
                    h.put("Email",Email);
                    h.put("Phone",Phone);

                    FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).setValue(h);
                    my_image.setDrawingCacheEnabled(true);
                    my_image.buildDrawingCache();
                    Bitmap bitmap = my_image.getDrawingCache();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();

                    UploadTask uploadTask = userprofilepicref.putBytes(data);


                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                            // Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        }
                    });

                }


            }
        });

my_image.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,IMG_RES);
    }
});
       // final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        dbref= FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).getRef();

        dbref.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                HashMap<String,String> userdata=(HashMap<String,String>)dataSnapshot.getValue();
                //String n=dataSnapshot.getValue().toString();
               String name=userdata.get("Name");
              String email=userdata.get("Email");
                String phone=userdata.get("Phone");
               edittext_name.setText(name.toString());
                edittext_email.setText(email.toString());
                edittext_phone.setText(phone.toString());
                Log.d("xx", user.getUid().toString());

                storageRef.child(user.getUid()+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'users/me/profile.png'
                        Uri downloadUri = uri;
                        Log.d("yy",uri.toString());
                        Picasso.with(User_Profile.this).load(uri).noPlaceholder().centerCrop().fit()
                                .into((ImageView) findViewById(R.id.my_image12));
                        /// The string(file link) that you need
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(),"failed to retrieve Image",Toast.LENGTH_SHORT).show();
                    }
                });

                //HashMap<String,String> userdata=(HashMap<String,String>)dataSnapshot.getValue();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == IMG_RES && resultCode == RESULT_OK
                    && null != data) {
                Uri URI = data.getData();
                Picasso.with(User_Profile.this).load(URI).noPlaceholder().centerCrop().fit()
                        .into((ImageView) findViewById(R.id.my_image12));
            }
        } catch (Exception e) {
            Toast.makeText(this, "Please try again", Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }




}
