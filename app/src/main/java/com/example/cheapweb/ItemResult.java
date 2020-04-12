package com.example.cheapweb;

import androidx.annotation.NonNull;
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
import android.widget.ListView;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ItemResult extends AppCompatActivity {

    RecyclerView mrecyclerView;
    FirebaseDatabase mfirebaseDatabase;
    DatabaseReference mRef,mRefLastSeenUser, mRefSearch;
    private FirebaseAuth mAuth;
    String[] idItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_result);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Items Result");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        mrecyclerView=findViewById(R.id.recyclerView);
        mrecyclerView.setHasFixedSize(true);
        idItems=new String[5];
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mfirebaseDatabase=FirebaseDatabase.getInstance();
        mRefLastSeenUser=mfirebaseDatabase.getReference("UserLastSeen");
        mRefSearch=mfirebaseDatabase.getReference("ItemResult");
        mRef=mfirebaseDatabase.getReference("items");
        String txtItem=getIntent().getStringExtra("Searchtext");
        DatabaseReference Results= FirebaseDatabase.getInstance().getReference("ItemResult");
        Results.removeValue();
        firebaseSearch(txtItem);

    }

    //Search about the item name or items that contains this name
    private void firebaseSearch(final String SearchText){

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    if (snapshot.getValue(Model.class).getItemName().contains(SearchText)){
                        mRefSearch.child(mAuth.getCurrentUser().getUid()).child(snapshot.getValue(Model.class).getIdItem())
                                .setValue(snapshot.getValue(Model.class));
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Query firebaseSearchQuery=mRef.orderByChild("itemName").startAt(SearchText).endAt(SearchText+ "\uf8ff");
        Query query=mRefSearch.child(mAuth.getCurrentUser().getUid()).orderByChild("itemName");
        FirebaseRecyclerAdapter<Model, ViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Model, ViewHolder>(Model.class
                ,R.layout.item_show, ViewHolder.class, query) {
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
