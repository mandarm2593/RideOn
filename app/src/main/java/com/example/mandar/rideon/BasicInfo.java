package com.example.mandar.rideon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class BasicInfo extends AppCompatActivity {
TextView name_basicinfo,phone_basic_info;
    EditText ETname,ETphone;
    ImageView profilepic;

//    StorageReference storageRef = storage.getReference();


    Button save_basicinfo;
    private  static int IMG_RES=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);

   name_basicinfo=(TextView)findViewById(R.id.textViewname_basicinfo);
        phone_basic_info=(TextView)findViewById(R.id.textViewphne_basicinfo);
        ETname=(EditText)findViewById(R.id.ETname_basicinfo);
        ETphone=(EditText)findViewById(R.id.ETphone_basicinfo);
        profilepic=(ImageView)findViewById(R.id.userpic_basicinfo);
        save_basicinfo=(Button)findViewById(R.id.button_save_basicinfo);
       final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        final StorageReference userprofilepicref = storageRef.child(user.getUid().toString()+".jpg");
        save_basicinfo.setOnClickListener(new View.OnClickListener() {




            @Override
            public void onClick(View v) {
                if(user!=null) {

                    HashMap<String, String> h = new HashMap<String, String>();
                    String Name = ETname.getText().toString();

                    String Phone = ETphone.getText().toString();
                    h.put("Name", Name);
                    h.put("Email", user.getEmail().toString());
                    h.put("Phone", Phone);

                    FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).setValue(h);
                    Intent intent = new Intent(BasicInfo.this, JoinHost.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);


                    profilepic.setDrawingCacheEnabled(true);
                    profilepic.buildDrawingCache();
                    Bitmap bitmap = profilepic.getDrawingCache();
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


profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,IMG_RES);
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


                Picasso.with(BasicInfo.this).load(URI).noPlaceholder().centerCrop().fit()
                        .into((ImageView) findViewById(R.id.userpic_basicinfo));


            }
        } catch (Exception e) {
            Toast.makeText(this, "Please try again", Toast.LENGTH_LONG)
                    .show();
        }

    }



}
