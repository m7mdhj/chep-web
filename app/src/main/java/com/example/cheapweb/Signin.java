package com.example.cheapweb;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signin extends AppCompatActivity {
    TextView user, pass;
    EditText email, password;
    Button signin, signup, forgetpass;
    FirebaseDatabase Users = FirebaseDatabase.getInstance();
    DatabaseReference myRef=Users.getReference("user");
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
                // Initialize Firebase Auth
                mAuth = FirebaseAuth.getInstance();
                // user=(TextView) findViewById(R.id.textView);
                // pass=(TextView) findViewById(R.id.textView2);
                progressBar=(ProgressBar) findViewById(R.id.progressBar);
                email=(EditText) findViewById(R.id.editText);
                password=(EditText) findViewById(R.id.editText2);
                signin=(Button) findViewById(R.id.button);
                signup=(Button) findViewById(R.id.button2);
                forgetpass=(Button) findViewById(R.id.button9);
                signin.setOnClickListener(new View.OnClickListener() {
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
                        mAuth.signInWithEmailAndPassword(maill,pas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(Signin.this,"Logged in Successfully" ,Toast.LENGTH_SHORT).show();
                                    Intent m=new Intent(getApplicationContext(),Home.class);
                                    startActivity(m);
                                }
                                else{
                                    Toast.makeText(Signin.this,"Error"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        progressBar.setVisibility(View.VISIBLE);
                    }
                });
                signup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent s= new Intent(getApplicationContext(),Signup.class);
                        startActivity(s);
                    }
                });
                forgetpass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mail = email.getText().toString().trim();

                        if (TextUtils.isEmpty(mail)) {
                            Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        progressBar.setVisibility(View.VISIBLE);

                        mAuth.sendPasswordResetEmail(mail)

                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Signin.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(Signin.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                        }

                                        progressBar.setVisibility(View.GONE);
                                    }
                                });
                    }
                });
            }
 /* private void signIn(final String username, final String password){
 myRef.addListenerForSingleValueEvent(new ValueEventListener() {
 @Override
 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
 if(dataSnapshot.child(username).exists()){
 if(!username.isEmpty()){
 User login=dataSnapshot.child(username).getValue(User.class);
 if(login.getPassword().equals(pass)){
 Toast.makeText(Signin.this,"Success login", Toast.LENGTH_SHORT).show();
 }
 else{
 Toast.makeText(Signin.this,"password is wrong", Toast.LENGTH_SHORT).show();
 }
 }
 else{
 Toast.makeText(Signin.this,"Username is wrong", Toast.LENGTH_SHORT).show();
 }
 }
 }

 @Override
 public void onCancelled(@NonNull DatabaseError databaseError) {

 }
 });
 }

 */
        }




