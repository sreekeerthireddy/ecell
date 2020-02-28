package com.example.android.contact;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class uploadevent extends AppCompatActivity {
    private ImageView photo;
    private String key;
    private EditText evname;
    private EditText evdes;

    private Button add;
    private String geturl;
    private StorageReference storeref;
    private Uri imageuri;
    private DatabaseReference dataref;
    private static final int gallerypic=1;
    private String eventn,eventdes;
    private ProgressDialog loadbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadevent);

        photo= findViewById(R.id.ephoto);
        evname= findViewById(R.id.ename);
        evdes= findViewById(R.id.edes);

        add= findViewById(R.id.eadd);
        storeref= FirebaseStorage.getInstance().getReference().child("Image_event");
        dataref= FirebaseDatabase.getInstance().getReference().child("Event");
        loadbar=new ProgressDialog(this);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectimage();
            }

        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adddetails();
            }
        });
    }
    private void selectimage()
    {
        Intent i=new Intent();
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i,gallerypic);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==gallerypic && resultCode==RESULT_OK && data!=null)
        {
            imageuri=data.getData();
            photo.setImageURI(imageuri);
        }
    }
    private void adddetails()
    {
        eventn=evname.getText().toString();
        eventdes=evdes.getText().toString();

        if(imageuri==null)
        {
            Toast.makeText(uploadevent.this, "Please select Image", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(eventn))
        {
            Toast.makeText(uploadevent.this,"Plesae enter name",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(eventdes))
        {
            Toast.makeText(uploadevent.this,"Please enter designation",Toast.LENGTH_SHORT).show();
        }

        else
        {
            uploaingdetails();
        }
    }

    private void uploaingdetails() {
        loadbar.setTitle("Adding new event");
        loadbar.setMessage("Please wait while we are uploading");
        loadbar.setCanceledOnTouchOutside(false);
        loadbar.show();
        key=eventn;
        final StorageReference filepath = storeref.child(imageuri.getLastPathSegment() + key + ".jpg");
        final UploadTask uploadtask = filepath.putFile(imageuri);
        uploadtask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String msg = e.getMessage();
                Toast.makeText(uploadevent.this, "Error:" + msg, Toast.LENGTH_SHORT).show();
                loadbar.dismiss();
            }

        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(uploadevent.this, "uploaded to storage", Toast.LENGTH_SHORT).show();

                Task<Uri> uriTask = uploadtask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        geturl = filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();
                    }

                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if (task.isSuccessful()) {
                            geturl = task.getResult().toString();
                            Toast.makeText(uploadevent.this, "got url successfully", Toast.LENGTH_SHORT).show();
                            storagetobase();
                        }
                        else
                        {
                            Toast.makeText(uploadevent.this,"error in getting urll",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }

        });
    }
    private void storagetobase()
    {
        HashMap<String,Object> detmap=new HashMap<>();
        detmap.put("name",eventn);
        detmap.put("description",eventdes);
        detmap.put("imageurl",geturl);

        dataref.child(key).updateChildren(detmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(uploadevent.this,"uploaded successfully",Toast.LENGTH_SHORT).show();
                    loadbar.dismiss();
                }
                else
                {
                    Toast.makeText(uploadevent.this,"Error in uploading to database",Toast.LENGTH_SHORT).show();
                    loadbar.dismiss();
                }
            }
        });

    }
}
