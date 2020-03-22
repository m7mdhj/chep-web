package com.example.cheapweb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
            TextView firstname,secandname,mail,pass;
            EditText name1,name2,email,password;
            Button signup;
            String sname,TAG;
            ProgressBar progressBar;
            FirebaseDatabase users = FirebaseDatabase.getInstance();
            DatabaseReference myRef=users.getReference("user");
            private FirebaseAuth mAuth;
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_signup);
                mAuth = FirebaseAuth.getInstance();
                progressBar=(ProgressBar) findViewById(R.id.progressBar2);
                firstname=(TextView) findViewById(R.id.textView3);
                secandname=(TextView) findViewById(R.id.textView4);
                mail=(TextView) findViewById(R.id.textView5);
                pass=(TextView) findViewById(R.id.textView6);
                name1=(EditText) findViewById(R.id.editText3);
                name2=(EditText) findViewById(R.id.editText4);
                email=(EditText) findViewById(R.id.editText5);
                password=(EditText) findViewById(R.id.editText6);
                signup=(Button) findViewById(R.id.button3);
                signup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String maill = email.getText().toString();
                        String pas = password.getText().toString();
                        if (TextUtils.isEmpty(maill)){
                            email.setError("Email is Required");
                            return;
                        }
                        if(TextUtils.isEmpty(pas)) {
                            password.setError("Password is Required");
                            return;
                        }
                        if(pas.length()<6){
                            password.setError("Password must be > 6 Characters");
                            return;
                        }


                        create((String)email.getText().toString(),(String)password.getText().toString());
                        progressBar.setVisibility(View.VISIBLE);
                    }
                });
            }
            private void create(String email, String password){
                mAuth.createUserWithEmailAndPassword((String)email,(String)password);
                Toast.makeText(Signup.this,"create account" ,Toast.LENGTH_SHORT).show();
                Intent m=new Intent(getApplicationContext(),Home.class);
                startActivity(m);

            }


}
