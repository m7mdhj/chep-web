package com.example.cheapweb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.net.Authenticator;

public class Signup extends AppCompatActivity  {
    TextView firstname,secandname,mail,pass;
    EditText name1,name2,email,password;
    Button signup;
    int num=0;
    ProgressBar progressBar;
    CheckBox remember2;
    private FirebaseAuth mAuth;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRefUsers = database.getReference("users");

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
        remember2=findViewById(R.id.checkBox2);

        //when the user click on the first time, who don't have to sign in again
        remember2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    SharedPreferences preferences=getSharedPreferences("checkBox", MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();
                }
                else if (!buttonView.isChecked()){
                    SharedPreferences preferences=getSharedPreferences("checkBox", MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                }
            }
        });

        //when the user click on this button the function will check if the email that he write is already registered
        //it will be make a Users object and set the info of the user in it..
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query= myRefUsers.orderByChild("email").equalTo(email.getText().toString());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        num=(int) dataSnapshot.getChildrenCount();
                        if(num==0) {
                            Users users=new Users(name1.getText().toString(),name2.getText().toString(),email.getText().toString(), password.getText().toString());
                            String maill = email.getText().toString();
                            String pas = password.getText().toString();
                            myRefUsers.child(mAuth.getCurrentUser().getUid()).setValue(users);
                            if (TextUtils.isEmpty(maill)){
                                email.setError("Email is Required");
                                return;
                            }
                            if(TextUtils.isEmpty(pas)) {
                                password.setError("Password is Required");
                                return;
                            }
                            if(pas.length()<6){
                                password.setError("Password must be more than 6 Characters");
                                return;
                            }


                            createUser(users);
                            progressBar.setVisibility(View.VISIBLE);
                        }
                        else {
                            Toast.makeText(Signup.this, "the email is already registered", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }


    //this function create account to the user to success login in the future
    private void createUser(Users users){

        mAuth.createUserWithEmailAndPassword(users.getEmail(), users.getPassword());
        Toast.makeText(Signup.this,"create account" ,Toast.LENGTH_SHORT).show();
        Intent m=new Intent(getApplicationContext(),MainActivity.class);
        m.putExtra("userEmail", email.getText().toString());
        startActivity(m);
    }

}
