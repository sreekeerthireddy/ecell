package com.example.android.contact;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.android.contact.R;

public class home extends AppCompatActivity {

    TextView admin;
    ImageView iview1;
    ImageView iview3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



  getSupportActionBar().setTitle("E Cell");


        admin = findViewById(R.id.admin);
        iview1= findViewById(R.id.i1);
        iview3= findViewById(R.id.i3);


        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(home.this, adminlogin.class);
                startActivity(i);

            }
        });

        iview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(home.this,eventhome.class);
                i.putExtra("keyy","user");
                startActivity(i);
            }
        });

        iview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(home.this,clusterhome.class);

                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.home:
            newhome();
            return true;
            case R.id.team:
                newteam();
                return true;
            case R.id.events:
                newevent();
                return true;


            case R.id.alumni:
                newalumni();
                return true;

                default:
                    return super.onOptionsItemSelected(item);



        }
    }

    public void newhome()
    {
              Intent i=new Intent(home.this,about.class);
              startActivity(i);
    }
    public void newteam()
    {
        Intent i=new Intent(home.this,teamhome.class);
        startActivity(i);
        }

        public void newevent()
        {
           Intent i=new Intent(home.this,eventhome.class);
                   i.putExtra("keyy","user");
           startActivity(i);
        }
        public void newalumni()
        {
                    Intent i=new Intent(home.this,alumini.class);
                    startActivity(i);
        }
}
