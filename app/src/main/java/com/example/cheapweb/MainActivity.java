package com.example.cheapweb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    EditText searchtxt;
    Button searchbtn;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchtxt = findViewById(R.id.searchtxt);
        searchbtn = findViewById(R.id.searchbtn);
        username=getIntent().getStringExtra("userEmail");




            searchbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String sear = searchtxt.getText().toString();
                    if (!sear.matches("")) {
                        String searchtext = searchtxt.getText().toString();
                        Intent intent = new Intent(MainActivity.this, ItemResult.class);
                        intent.putExtra("Searchtext", searchtext);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "please select an item name", Toast.LENGTH_SHORT).show();
                    }
                }
                });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.bottom_nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.navigation_logout:
                intent=new Intent(getApplicationContext(),Signin.class);
                Toast.makeText(MainActivity.this,"Logout...", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            case R.id.navigation_favorite:
                intent=new Intent(getApplicationContext(),Favorite.class);
                Toast.makeText(MainActivity.this,"favorite class", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            case R.id.navigation_home:
                intent=new Intent(getApplicationContext(),MainActivity.class);
                Toast.makeText(MainActivity.this,"home class", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
           case R.id.nawigation_add:
               MenuItem menuItem=findViewById(R.id.nawigation_add);
               /*if (username.equals(email)) {
                   Toast.makeText(MainActivity.this, "add..", Toast.LENGTH_SHORT).show();
                   menuItem.setVisible(true);
               }

                */
               break;
        }
        return false;
    }


}
