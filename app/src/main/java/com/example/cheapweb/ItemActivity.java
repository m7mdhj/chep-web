package com.example.cheapweb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ItemActivity extends AppCompatActivity {


    TextView NameOfItem, PriceOfItem, InfoOfItem, Link_1, Link_2, Link_3, PriceLink1, PriceLink2, PriceLink3;
    ImageView ImageOfItem;
    Button maddFavorite, mShareBtn;
    private static final int WRITE_EXTERNAL_STORAGE_CODE=1;
    FirebaseDatabase mfirebaseDatabase, mfirebaseDatabaseFavorite;
    DatabaseReference mRef, mRefFavoriteId, mRefItemFav;
    String username;
    private FirebaseAuth mAuth;
    Model img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Item");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        //recyclerView=findViewById(R.id.recycler_View);
        NameOfItem = findViewById(R.id.ItemNm);
        PriceOfItem = findViewById(R.id.ItemPric);
        InfoOfItem = findViewById(R.id.ItemInf);
        ImageOfItem=findViewById(R.id.Itemimg);
        Link_1=findViewById(R.id.Link1);
        Link_2=findViewById(R.id.Link2);
        Link_3=findViewById(R.id.Link3);
        PriceLink1=findViewById(R.id.price_link1);
        PriceLink2=findViewById(R.id.price_link2);
        PriceLink3=findViewById(R.id.price_link3);

        mfirebaseDatabase=FirebaseDatabase.getInstance();
        mRef=mfirebaseDatabase.getReference("items");
        mfirebaseDatabaseFavorite= FirebaseDatabase.getInstance();
        mRefFavoriteId=mfirebaseDatabaseFavorite.getReference("FavoritesId");
        mRefItemFav=mfirebaseDatabase.getReference("FavItem");
        final String id=getIntent().getStringExtra("itemid");
        username=getIntent().getStringExtra("userName");
       /* final String itemname = getIntent().getStringExtra("Item-Name");
        String itemprice = getIntent().getStringExtra("Item-Price");
        String img= getIntent().getStringExtra("Item-Image");



        NameOfItem.setText(itemname);
        PriceOfItem.setText(itemprice);
        Picasso.with(getBaseContext()).load(img).into(ImageOfItem);


        */
        Link_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url=Link_1.getText().toString();
                OpenWeb(url);
            }
        });

        Link_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url=Link_2.getText().toString();
                OpenWeb(url);
            }
        });

        Link_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url=Link_3.getText().toString();
                OpenWeb(url);
            }
        });

        maddFavorite=findViewById(R.id.addFavorite);
        mShareBtn=findViewById(R.id.sharebtn);
        maddFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRefItemFav.child(mAuth.getCurrentUser().getUid()).push().setValue(img);
                Toast.makeText(ItemActivity.this, "added to favorite..", Toast.LENGTH_SHORT).show();


            }
        });
        mShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareImage();
            }
        });

        Query query=mRef.orderByChild("idItem").equalTo(id);
        query.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    img = ds.getValue(Model.class);
                    NameOfItem.setText(img.getItemName());
                    PriceOfItem.setText(img.getItemPrice());
                    Picasso.with(getBaseContext()).load(img.getImagePath()).into(ImageOfItem);
                    InfoOfItem.setText(img.getItemInfo());
                    Link_1.setText(img.getLink1());
                    Link_2.setText(img.getLink2());
                    Link_3.setText(img.getLink3());
                    if (isEmpty(img.getPriceInLink1())){
                        PriceLink1.setText(img.getPriceInLink1()+"₪");
                    }
                    if (isEmpty(img.getPriceInLink1())){
                        PriceLink2.setText(img.getPriceInLink2()+"₪");
                    }

                    if(isEmpty(img.getPriceInLink3())) {
                        PriceLink3.setText(img.getPriceInLink3() + "₪");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        } );

    }


    public boolean isEmpty(String text){
        if (!text.equals("")){
            return true;
        }
        return false;
    }


    //open website of item..
    public void OpenWeb(String url){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("http://"+url));
        startActivity(i);
    }

    //share item func...
    private void shareImage() {
        try {
            String s=NameOfItem.getText().toString()+"\n"+ PriceOfItem.getText().toString();
            File file= new File(getExternalCacheDir(),"sample.png");
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_TEXT,s);
            intent.putExtra(Intent.EXTRA_SUBJECT, Uri.fromFile(file));
            intent.setType("image/png");
            startActivity(Intent.createChooser(intent,"share via"));
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case WRITE_EXTERNAL_STORAGE_CODE:{
                if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    //saveImage();
                }
                else {
                    Toast.makeText(this, "enable permission to save image", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    private String getImageType(String name){
        String [] type = name.split( "\\." );
        return type[1];
        }


}
