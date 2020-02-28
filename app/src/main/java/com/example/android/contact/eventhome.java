package com.example.android.contact;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.contact.ViewHolder.displayviewholder;
import com.example.android.contact.ViewHolder.evnetdisplayviewholder;
import com.example.android.contact.ViewHolder.evnetdisplayviewholder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class eventhome extends AppCompatActivity {

    private DatabaseReference dref;
    private RecyclerView rview;
    RecyclerView.LayoutManager layoutManager;
   // Button d;
    String dels;
    String ename;
    String val;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventhome);

        val=getIntent().getStringExtra("keyy");
        dref= FirebaseDatabase.getInstance().getReference().child("Event");
        rview=findViewById(R.id.eventrecycleview);
        rview.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        rview.setLayoutManager(layoutManager);
         rview.setNestedScrollingEnabled(false);
       // d=(Button)findViewById(R.id.del);

       /* d.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent i=new Intent(eventhome.this,home.class);
                 startActivity(i);

             }
         });*/

        getSupportActionBar().setTitle("E Cell");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }
    @Override
    protected void onStart() {

        super.onStart();
        FirebaseRecyclerOptions<downloadevent> options= new FirebaseRecyclerOptions.Builder<downloadevent>()
                .setQuery(dref,downloadevent.class).build();

        FirebaseRecyclerAdapter<downloadevent,evnetdisplayviewholder> adapter =new FirebaseRecyclerAdapter<downloadevent, evnetdisplayviewholder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull evnetdisplayviewholder holder, int position, @NonNull final downloadevent model) {

               // adapter.notifyDataSetChanged();


                holder.delete.setVisibility(View.INVISIBLE);
                holder.name.setText(model.getName());


              //  holder.des.setText(model.getDescription());
                Picasso.get().load(model.getImage_url()).into(holder.image);

                if(val.equals("admin"))
                {
                    holder.delete.setVisibility(View.VISIBLE);

                }
                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //  Intent i=new Intent(eventhome.this,about.class);
                        //  startActivity(i);
                        ename=model.getName();
                        deleteitem();

                    }
                });



         /*       d.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dels=model.getImage_url();
                        StorageReference sref= FirebaseStorage.getInstance().getReferenceFromUrl(dels);
                        sref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(),"Imae delted",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"not done", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });*/


                holder.image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(eventhome.this,eventdetails.class);
                        i.putExtra("Name",model.getName());
                        startActivity(i);



                    }
                });


            }

            @NonNull
            @Override
            public evnetdisplayviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_displayevent,viewGroup,false);
                evnetdisplayviewholder holder =new evnetdisplayviewholder(view);
                return holder;
            }
        };
        rview.setAdapter(adapter);
        adapter.startListening();



        //adapter.notifyDataSetChanged();





    }

    private void deleteitem()
    {

        DatabaseReference dref;
        dref=FirebaseDatabase.getInstance().getReference().child("Event").child(ename);
        dref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(eventhome.this,"Event is deletd successfully",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
       // DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

            super.onBackPressed();

    }
    @Override

    protected void onStop()
    {
        super.onStop();

    }


}
