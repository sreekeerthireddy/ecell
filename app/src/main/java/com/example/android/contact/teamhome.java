package com.example.android.contact;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.contact.ViewHolder.displayviewholder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class teamhome extends AppCompatActivity {

    private DatabaseReference dref;
    private RecyclerView rview;
    RecyclerView.LayoutManager layoutManager;
   String link;
   private  String reg="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teamhome);
  dref= FirebaseDatabase.getInstance().getReference().child("Contact");
  rview=findViewById(R.id.recycleview);
  rview.setHasFixedSize(true);
  layoutManager=new LinearLayoutManager(this);
  rview.setLayoutManager(layoutManager);
  rview.setNestedScrollingEnabled(false);

    getSupportActionBar().setTitle("E Cell");
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<download> options= new FirebaseRecyclerOptions.Builder<download>()
                .setQuery(dref,download.class).build();

        FirebaseRecyclerAdapter<download,displayviewholder> adapter=new FirebaseRecyclerAdapter<download, displayviewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull displayviewholder holder, int position, @NonNull final download model) {
                holder.name.setText(model.getName());
                holder.des.setText(model.getDesignation());
                Picasso.get().load(model.getImage_url()).into(holder.image);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                       reg=model.getregister();
                       getpersondetails(reg);

                    }
                });

            }

            @NonNull
            @Override
            public displayviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.displayteamlayout,viewGroup,false);
            displayviewholder holder =new displayviewholder(view);
            return holder;
            }
        };

       rview.setAdapter(adapter);
       adapter.startListening();

    }

    private void getpersondetails(String reg)
    {

        DatabaseReference dref= FirebaseDatabase.getInstance().getReference().child("Contact");

        dref.child(reg).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    download devent=dataSnapshot.getValue(download.class);
                   link=devent.getll();

                    Intent browserIntent = new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(link));
                    startActivity(browserIntent);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
