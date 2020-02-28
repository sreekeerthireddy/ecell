package com.example.android.contact;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class eventdetails extends AppCompatActivity {

    private ImageView eimage;
    private TextView etext;
    private String eventnam="";
    private Button reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventdetails);

        eimage= findViewById(R.id.eventimage);
        etext= findViewById(R.id.eventd);
        eventnam=getIntent().getStringExtra("Name");


        reg= findViewById(R.id.eregister);


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://sastra.edu/"));
                startActivity(browserIntent);
            }
        });

        geteventdetails(eventnam);

        getSupportActionBar().setTitle("E Cell");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       // super.onBackPressed();

    }

    private void geteventdetails(final String eventnam)
    {
        DatabaseReference dref= FirebaseDatabase.getInstance().getReference().child("Event");

        dref.child(eventnam).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    downloadevent devent=dataSnapshot.getValue(downloadevent.class);
                    etext.setText(devent.getDescription());
                    Picasso.get().load(devent.getImage_url()).into(eimage);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
