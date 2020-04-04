package com.example.cheapweb;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ItemResult extends AppCompatActivity {

    RecyclerView mrecyclerView;
    FirebaseDatabase mfirebaseDatabase;
    DatabaseReference mRef;
    SearchView searchView;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_result);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Items Result");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mrecyclerView=findViewById(R.id.recyclerView);
        mrecyclerView.setHasFixedSize(true);

        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mfirebaseDatabase=FirebaseDatabase.getInstance();
        mRef=mfirebaseDatabase.getReference("items");
        String txtItem=getIntent().getStringExtra("Searchtext");
        firebaseSearch(txtItem);

    }

    private void firebaseSearch(String SearchText){
        Query firebaseSearchQuery=mRef.orderByChild("itemName").equalTo(SearchText);

        FirebaseRecyclerAdapter<Model, ViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Model, ViewHolder>(Model.class
                ,R.layout.item_show, ViewHolder.class, firebaseSearchQuery) {
            @Override
            protected void populateViewHolder(ViewHolder viewHolder, Model model, int i) {
                viewHolder.setDetails(getApplicationContext(), model.getItemName(), model.getItemPrice(), model.getImagePath());
            }
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                ViewHolder viewHolder=super.onCreateViewHolder(parent,viewType);
                viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String ItName= getItem(position).getItemName();
                        String ItPrice=getItem(position).getItemPrice();
                        String ItImage=getItem(position).getImagePath();
                        String idItem=getItem(position).getIdItem();
                        Intent intent=new Intent(ItemResult.this,ItemActivity.class);
                       /* intent.putExtra("Item-Name", ItName);
                        intent.putExtra("Item-Price", ItPrice);
                        intent.putExtra("Item-Image", ItImage);

                        */
                       intent.putExtra("userName", userName);
                       intent.putExtra("itemid", idItem);
                       startActivity(intent);


                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });
                return viewHolder;
            }



        };

        //set adapter to recycler view
        mrecyclerView.setAdapter(firebaseRecyclerAdapter);
    }


    //load data into recycler view
  /*  @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Model, ViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Model, ViewHolder>(Model.class
                ,R.layout.item_show, ViewHolder.class, mRef) {
            @Override
            protected void populateViewHolder(ViewHolder viewHolder, Model model, int i) {
                viewHolder.setDetails(getApplicationContext(), model.getItemName(), model.getItemPrice(), model.getImagePath());
            }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                ViewHolder viewHolder=super.onCreateViewHolder(parent,viewType);
                viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        String ItName= getItem(position).getItemName();
                        String ItPrice=getItem(position).getItemPrice();
                        String ItImage=getItem(position).getImagePath();
                        Intent intent=new Intent(ItemResult.this,ItemActivity.class);
                        intent.putExtra("title", ItName);
                        intent.putExtra("description", ItPrice);
                        intent.putExtra("image", ItImage);
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });
                return viewHolder;
            }
        };

        //set adapter to recycler view
        mrecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

   */
}
