package com.example.android.contact;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class category extends AppCompatActivity {

   private TextView event,team,alumni,pevent;
   String val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        val=getIntent().getStringExtra("keyy");
       event= findViewById(R.id.eventtext);
        team= findViewById(R.id.teamtext);
        alumni= findViewById(R.id.alumnitext);
        pevent= findViewById(R.id.preveventtext);

        team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(category.this,upload.class);
                startActivity(i);
            }
        });
        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(category.this,uploadevent.class);
                startActivity(i);
            }
        });

        pevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(category.this,eventhome.class);
                i.putExtra("keyy",val);
                startActivity(i);
            }
        });
    }
}
