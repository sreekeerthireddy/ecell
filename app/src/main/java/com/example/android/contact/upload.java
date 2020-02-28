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

public class upload extends AppCompatActivity {
    private ImageView photo;
    private String key;
    private EditText fname;
    private EditText fphone;
    private EditText fregister;
    private EditText flink;
    private Button add;
    private String geturl;
    private StorageReference storeref;
    private Uri imageuri;
    private DatabaseReference dataref;
    private static final int gallerypic=1;
    private String frndname,frndphone,freg,link;
    private ProgressDialog loadbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        photo= findViewById(R.id.photo);
        fname= findViewById(R.id.name);
        fphone= findViewById(R.id.number);
        fregister= findViewById(R.id.register);
        flink= findViewById(R.id.linked);
        add= findViewById(R.id.add);
        storeref= FirebaseStorage.getInstance().getReference().child("Image_contact");
        dataref= FirebaseDatabase.getInstance().getReference().child("Contact");
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
        frndname=fname.getText().toString();
        frndphone=fphone.getText().toString();
        freg=fregister.getText().toString();
        link=flink.getText().toString();
        if(imageuri==null)
        {
            Toast.makeText(upload.this, "Please select Image", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(frndname))
        {
            Toast.makeText(upload.this,"Plesae enter name",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(frndphone))
        {
            Toast.makeText(upload.this,"Please enter designation",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(freg))
        {
            Toast.makeText(upload.this,"Please enter register number",Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(link))
        {
            Toast.makeText(upload.this,"Please enter linked in profile link",Toast.LENGTH_SHORT).show();
        }
        else
        {
            uploaingdetails();
        }
    }

    private void uploaingdetails() {
        loadbar.setTitle("Add new member");
        loadbar.setMessage("Please wait while we are uploading");
        loadbar.setCanceledOnTouchOutside(false);
        loadbar.show();
        key=freg;
        final StorageReference filepath = storeref.child(imageuri.getLastPathSegment() + key + ".jpg");
        final UploadTask uploadtask = filepath.putFile(imageuri);
        uploadtask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String msg = e.getMessage();
                Toast.makeText(upload.this, "Error:" + msg, Toast.LENGTH_SHORT).show();
                loadbar.dismiss();
            }

        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(upload.this, "uploaded to storage", Toast.LENGTH_SHORT).show();

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
                            Toast.makeText(upload.this, "got url successfully", Toast.LENGTH_SHORT).show();
                            storagetobase();
                        }
                        else
                        {
                            Toast.makeText(upload.this,"error in getting urll",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }

        });
    }
    private void storagetobase()
    {
        HashMap<String,Object> detmap=new HashMap<>();
        detmap.put("name",frndname);
        detmap.put("designation",frndphone);
        detmap.put("imageurl",geturl);
        detmap.put("register",freg);
        detmap.put("linkedinlink",link);
        dataref.child(key).updateChildren(detmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(upload.this,"uploaded successfully",Toast.LENGTH_SHORT).show();
                    loadbar.dismiss();
                }
                else
                {
                    Toast.makeText(upload.this,"Error in uploading to database",Toast.LENGTH_SHORT).show();
                    loadbar.dismiss();
                }
            }
        });

    }
}
