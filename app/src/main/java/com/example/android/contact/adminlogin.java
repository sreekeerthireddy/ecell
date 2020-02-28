package com.example.android.contact;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.android.laura.model.users;
//import com.example.android.laura.prevalent.prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

//import io.paperdb.Paper;

public class adminlogin extends AppCompatActivity {
    private EditText pno,password;
    private Button logbtn;
    private CheckBox rememberme;
    private ProgressDialog loadbar;
    private TextView adminlink;
    private TextView nonadmin;
    String parentdbname="admin" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);

        logbtn= findViewById(R.id.adminlog);
        pno= findViewById(R.id.adminpno);
        password= findViewById(R.id.adminpswd);
        loadbar=new ProgressDialog(adminlogin.this);
       // rememberme=(CheckBox)findViewById(R.id.remember_chkb);
     //   adminlink=(TextView) findViewById(R.id.admin_link);
       // nonadmin=(TextView)findViewById(R.id.not_admin_link);
      //  Paper.init(this);


      /*  adminlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logbtn.setText("Admin login");
                adminlink.setVisibility(View.INVISIBLE);
                nonadmin.setVisibility(View.VISIBLE);
                parentdbname="admin";
            }
        }); */
      /*  nonadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logbtn.setText("login");
                adminlink.setVisibility(View.VISIBLE);
                nonadmin.setVisibility(View.INVISIBLE);
                parentdbname="users";

            }
        });*/
        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logininto();    }
        });
    }
    private void logininto()
    {
        String upno=pno.getText().toString();
        String upassword=password.getText().toString();


        if(TextUtils.isEmpty(upno))
        {
            Toast.makeText(this, "Please enter registered phone number", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(upassword))
        {
            Toast.makeText(this,"Please enter correct password",Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadbar.setTitle("Login account");
            loadbar.setMessage("Please wait, we are checking your credentials");
            loadbar.setCanceledOnTouchOutside(false);
            loadbar.show();
            Toast.makeText(adminlogin.this, "wait", Toast.LENGTH_SHORT).show();
            loginaccount(upno,upassword);
        }
    }
    private void loginaccount( final String upno, final String upassword)
    {
        /*if(rememberme.isChecked())
        {
            Paper.book().write(prevalent.upno,upno);
            Paper.book().write(prevalent.upass,upassword);

        }*/
        Toast.makeText(adminlogin.this, "in login account", Toast.LENGTH_SHORT).show();
         DatabaseReference rootref;
        rootref= FirebaseDatabase.getInstance().getReference();
        Toast.makeText(adminlogin.this, "got db reference", Toast.LENGTH_SHORT).show();
        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentdbname).child(upno).exists()) {

                    Toast.makeText(adminlogin.this, "db exists", Toast.LENGTH_SHORT).show();
                    user userdata = dataSnapshot.child(parentdbname).child(upno).getValue(user.class);

                    if (userdata.getPhone().equals(upno)) {

                        if (userdata.getPassword().equals(upassword)) {

                            if (parentdbname.equals("admin")) {
                                Toast.makeText(adminlogin.this, "login successful", Toast.LENGTH_SHORT).show();
                                loadbar.dismiss();

                                Intent i = new Intent(adminlogin.this, category.class);
                                i.putExtra("keyy","admin");
                                startActivity(i);
                            }
                            else
                            {
                                Toast.makeText(adminlogin.this, "No user found", Toast.LENGTH_SHORT).show();
                                loadbar.dismiss();
                            }

                        } else {
                            Toast.makeText(adminlogin.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            loadbar.dismiss();
                        }

                    } else {
                        loadbar.dismiss();
                        Toast.makeText(adminlogin.this, "Account doesn't. Invalid phone number ", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(adminlogin.this,"Account doesn't exist. Create new one",Toast.LENGTH_SHORT).show();
                    loadbar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(adminlogin.this, "Error Error", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
